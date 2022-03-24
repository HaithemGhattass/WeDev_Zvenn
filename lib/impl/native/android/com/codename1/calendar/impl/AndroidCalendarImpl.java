/*
 * @author  Andreas
 * @version $Revision$
 *
 * Date: 4/02/14
 * Time: 8:28 AM
 * 
 * $Id$
 */
package com.codename1.calendar.impl;

import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.TimeZone;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.text.format.Time;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class AndroidCalendarImpl {

   private static final String ACCOUNT_NAME = "local";
   
   private static final Uri CALENDAR_URI = CalendarContract.Calendars.CONTENT_URI;
   private static final Uri EVENT_URI    = CalendarContract.Events.   CONTENT_URI;
   private static final Uri REMINDER_URI = CalendarContract.Reminders.CONTENT_URI;

   //
   // helpers
   //
   
   private static Uri buildCalendarURI(String accountName) {
      return CALENDAR_URI
        .buildUpon()
        .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
        .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, accountName)
        .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL)
        .build();
   }

   private static ContentValues buildNewCalendarCVs(String accountName, String calendarName) {
      ContentValues cv = new ContentValues();
      cv.put(CalendarContract.Calendars.ACCOUNT_NAME,          accountName);
      cv.put(CalendarContract.Calendars.ACCOUNT_TYPE,          CalendarContract.ACCOUNT_TYPE_LOCAL);
      cv.put(CalendarContract.Calendars.NAME,                  calendarName);
      cv.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, calendarName);
      cv.put(CalendarContract.Calendars.CALENDAR_COLOR,        0xEA8561);
      cv.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_READ);
      cv.put(CalendarContract.Calendars.OWNER_ACCOUNT,         accountName);
      cv.put(CalendarContract.Calendars.VISIBLE,               1);
      cv.put(CalendarContract.Calendars.SYNC_EVENTS,           1);
      
      return cv;
   }

   private static Uri buildEventURI(String accountName) {
      return EVENT_URI
        .buildUpon()
        .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
        .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, accountName)
        .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL)
        .build();
   }

   //
   // talk to OS
   //   

   public static int getCalendarCount(Context ctx) {
      ContentResolver cr = ctx.getContentResolver();
      Cursor      cursor = cr.query(buildCalendarURI(ACCOUNT_NAME), new String[] { CalendarContract.Calendars._ID }, null, null, null);

      try {
         return cursor.getCount();         
      }
      finally {
         cursor.close();
      }
   }

   public static String getCalendarName(Context ctx, int offset) {
      ContentResolver cr = ctx.getContentResolver();
      Cursor      cursor = cr.query(buildCalendarURI(ACCOUNT_NAME), new String[] { CalendarContract.Calendars.NAME }, null, null, CalendarContract.Calendars._ID);

      try {
         return cursor.move(offset + 1) ? cursor.getString(0) : null;
      }
      finally {
         cursor.close();
      }
   }

   public static String openCalendar(Context ctx, String calendarName, boolean createIfNotExists) {
      ContentResolver cr = ctx.getContentResolver();
      Uri         calURI = buildCalendarURI(ACCOUNT_NAME);
      Cursor      cursor = cr.query(calURI, 
                                    new String[] { CalendarContract.Calendars._ID }, 
                                    "(" + CalendarContract.Calendars.ACCOUNT_NAME + " = ? AND " + CalendarContract.Calendars.NAME + " = ?)", 
                                    new String[] { ACCOUNT_NAME, calendarName }, 
                                    null);
      
      try {
         if (!cursor.moveToFirst()) 
            return createIfNotExists ? cr.insert(calURI, buildNewCalendarCVs(ACCOUNT_NAME, calendarName)).getLastPathSegment() : null; 
         else 
            return cursor.getString(0);  
      }
      finally {
         cursor.close();
      }      
   }

   public static String saveEvent(Context ctx, String calendarID, String eventID, String title, long startTimeStamp, long endTimeStamp, boolean allDayEvent, boolean taskOnly, String notes, String location, String reminders) {
      ContentResolver cr = ctx.getContentResolver();
      ContentValues   cv = new ContentValues();
      cv.put(CalendarContract.Events.CALENDAR_ID,    calendarID);
      cv.put(CalendarContract.Events.TITLE,          title);
      cv.put(CalendarContract.Events.EVENT_LOCATION, location);
      cv.put(CalendarContract.Events.DESCRIPTION,    notes);
      
      if (allDayEvent) {
         Calendar cal = Calendar.getInstance();
         cal.setTimeInMillis(startTimeStamp);
         cal.set(Calendar.HOUR_OF_DAY, 0);
         cal.set(Calendar.MINUTE,      0);
         cal.set(Calendar.SECOND,      0);
         cal.set(Calendar.MILLISECOND, 0);
         
         cv.put(CalendarContract.Events.EVENT_TIMEZONE, Time.TIMEZONE_UTC);
         cv.put(CalendarContract.Events.DTSTART,        cal.getTimeInMillis());         
      }
      else {
         cv.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
         cv.put(CalendarContract.Events.DTSTART,        startTimeStamp);
         cv.put(CalendarContract.Events.DTEND,          endTimeStamp);
      }
      
      if (eventID == null || eventID.trim().length() == 0)  // create new event
         eventID = cr.insert(buildEventURI(ACCOUNT_NAME), cv).getLastPathSegment();   
      else 
         cr.update(buildEventURI(ACCOUNT_NAME), cv, "(" + CalendarContract.Events._ID + " = ?)", new String[] { eventID });
      
      if (reminders != null && reminders.trim().length() > 0) 
         addReminders(ctx, eventID, reminders);
      
      return eventID;
   }

   private static void addReminders(Context ctx, String eventID, String reminders) {
      ContentResolver cr = ctx.getContentResolver();
      ContentValues   cv = new ContentValues();
      cv.put(CalendarContract.Reminders.EVENT_ID, eventID);
      cv.put(CalendarContract.Reminders.METHOD,   CalendarContract.Reminders.METHOD_ALERT);
      StringTokenizer tokens = new StringTokenizer(reminders, ",");
      
      while (tokens.hasMoreTokens()) {
         String reminder = tokens.nextToken();
         
         if (TextUtils.isDigitsOnly(reminder)) {            
            cv.put(CalendarContract.Reminders.MINUTES, Integer.valueOf(reminder) / 60);

            cr.insert(REMINDER_URI, cv);
         }         
      }      
   }
   
   public static boolean removeEvent(Context ctx, String calendarID, String eventID) {
      ContentResolver cr = ctx.getContentResolver();
        
      return cr.delete(EVENT_URI, "(" + CalendarContract.Events._ID + " = ?)", new String [] { eventID }) > 0;      
   }

   public static String getEventByID(Context ctx, String calendarID, String eventID) {
      ContentResolver cr = ctx.getContentResolver();
      Cursor      cursor = cr.query(buildEventURI(ACCOUNT_NAME),
                                    new String[] {
                                      CalendarContract.Events._ID,
                                      CalendarContract.Events.TITLE,
                                      CalendarContract.Events.DESCRIPTION,
                                      CalendarContract.Events.EVENT_LOCATION,
                                      CalendarContract.Events.DTSTART,
                                      CalendarContract.Events.DTEND,
                                      CalendarContract.Events.ALL_DAY
                                    },
                                    "(" + CalendarContract.Events.CALENDAR_ID + " = ? AND " + CalendarContract.Events._ID + " = ?)",
                                    new String[] { calendarID, eventID },
                                    null);

      try {
         return "<?xml version=\"1.0\"?>\n" +
                "<calendarNativeAPIResponse>\n" +
                "  <response>\n" +
                "    <requestType>getEventByID</requestType>\n" +
                     encodeEvents(ctx, cursor, "    ") +
                "    <requestCompletedSuccessfully>true</requestCompletedSuccessfully>\n" +
                "  </response>\n" +
                "</calendarNativeAPIResponse>";
      }
      finally {
         cursor.close();
      }
   }

   public static String getEvents(Context ctx, String calendarID, long startTimeStamp, long endTimeStamp) {
      ContentResolver cr = ctx.getContentResolver();
      Cursor      cursor = cr.query(buildEventURI(ACCOUNT_NAME),
                                    new String[] {
                                      CalendarContract.Events._ID,
                                      CalendarContract.Events.TITLE,
                                      CalendarContract.Events.DESCRIPTION,
                                      CalendarContract.Events.EVENT_LOCATION,
                                      CalendarContract.Events.DTSTART,
                                      CalendarContract.Events.DTEND,
                                      CalendarContract.Events.ALL_DAY
                                    },
                                    "(" + CalendarContract.Events.CALENDAR_ID + " = ? AND " + CalendarContract.Events.DTSTART + " >= ? AND " + CalendarContract.Events.DTEND + " <= ?)",
                                    new String[] { calendarID, String.valueOf(startTimeStamp), String.valueOf(endTimeStamp) },
                                    null);

      try {
         return "<?xml version=\"1.0\"?>\n" +
                "<calendarNativeAPIResponse>\n" +
                "  <response>\n" +
                "    <requestType>getEvents</requestType>\n" +
                "    <eventList>\n" +
                "      <searchStartTimeStamp>" + startTimeStamp + "</searchStartTimeStamp>\n" +
                "      <searchEndTimeStamp>"   + endTimeStamp   + "</searchEndTimeStamp>\n"   +
                       encodeEvents(ctx, cursor, "      ") +
                "    </eventList>\n" +
                "    <requestCompletedSuccessfully>true</requestCompletedSuccessfully>\n" +
                "  </response>\n" +
                "</calendarNativeAPIResponse>";
      }
      finally {
         cursor.close();
      }      
   }

   private static String encodeEvents(Context ctx, Cursor cursor, String padding) {
      StringBuilder sb = new StringBuilder();
            
      while (cursor.moveToNext()) 
         sb.append(padding).append("<event>\n")
           .append(padding).append("  <id>").            append(cursor.getString(0)).append("</id>\n")
           .append(padding).append("  <title>").         append(cursor.getString(1)).append("</title>\n")
           .append(padding).append("  <description>").   append(cursor.getString(2)).append("</description>\n")
           .append(padding).append("  <location>").      append(cursor.getString(3)).append("</location>\n")
           .append(padding).append("  <startTimeStamp>").append(cursor.getLong  (4)).append("</startTimeStamp>\n")
           .append(padding).append("  <endTimeStamp>").  append(cursor.getString(5)).append("</endTimeStamp>\n")
           .append(padding).append("  <allDayEvent>").   append(cursor.getInt   (6) > 0 ? "true" : "false").append("</allDayEvent>\n")
           .append(padding).append("  <reminders>\n")     
           .append(padding).append(     encodeReminders(ctx, cursor.getString(0), padding))
           .append(padding).append("  </reminders>\n")
           .append(padding).append("</event>\n");
      
      return sb.toString();
   }
   
   private static String encodeReminders(Context ctx, String eventID, String padding) {
      ContentResolver cr = ctx.getContentResolver();
      Cursor      cursor = cr.query(REMINDER_URI,
                                    new String[] { CalendarContract.Reminders.MINUTES },
                                    "(" + CalendarContract.Reminders.EVENT_ID + " = ?)",
                                    new String[] { eventID },
                                    null);
      StringBuilder sb = new StringBuilder();
      
      try {
         while (cursor.moveToNext()) 
            sb.append(padding).append("  <reminderOffset>").append(cursor.getInt(0) * 60).append("</reminderOffset>\n");
         
         return sb.toString();
      }
      finally {
         cursor.close();
      }
   }
}
