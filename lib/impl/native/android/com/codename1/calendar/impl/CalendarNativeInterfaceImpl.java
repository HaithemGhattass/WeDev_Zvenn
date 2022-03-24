package com.codename1.calendar.impl;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.Manifest;
import com.codename1.impl.android.AndroidNativeUtil;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class CalendarNativeInterfaceImpl {
   
   /**
    * @return currently assumes being used on >= ICS
    */
   public boolean hasPermissions() {
      if ( !AndroidNativeUtil.checkForPermission(Manifest.permission.READ_CALENDAR, "Allow reading device calendar")) {
          return false;
      } else
          if (!AndroidNativeUtil.checkForPermission(Manifest.permission.WRITE_CALENDAR, "Allow writing device calendar"))
              return false;       
      return true;
   }

   /**
    * Query device and return number of available calendars.
    *
    * @return Number of available calendars
    */
   public int getCalendarCount() {
      return AndroidCalendarImpl.getCalendarCount(com.codename1.impl.android.AndroidNativeUtil.getActivity());
   }

   /**
    * @param offset - offset into available calendar list
    * @return - Name of the calendar at the offset. Null if there is no calendar at this offset.
    */
   public String getCalendarName(int offset) {
      return AndroidCalendarImpl.getCalendarName(com.codename1.impl.android.AndroidNativeUtil.getActivity(), offset);
   }

   /**
    * Opens the named calendar creating it if necessary.
    *
    * @param calendarName      - Name of calendar to be opened/created
    * @param createIfNotExists - Indicates if a calendar must be created if there is no calendar found by the name provided
    * @return Unique ID to be used in other methods referencing this calendar. Null in case of failure or calendar does not exist
    */
   public String openCalendar(String calendarName, boolean createIfNotExists) {
      return AndroidCalendarImpl.openCalendar(com.codename1.impl.android.AndroidNativeUtil.getActivity(), calendarName, createIfNotExists);
   }

   /**
    * Add/Edit an event in named calendar
    *
    * @param calendarID     - As returned from openCalendar
    * @param eventID        - Event Identifier. Pass null for new Events
    * @param title          - Title of the Calendar Event
    * @param startTimeStamp - Event starting time stamp 
    * @param endTimeStamp   - Event ending time stamp 
    * @param allDayEvent    - The event is an all day event
    * @param taskOnly       - Task Only. No time associated with this.
    * @param notes          - Any notes for the event
    * @param location       - Location of the event
    * @param reminders      - alarm offsets (in seconds) in CSV format. Pass null for no alarms
    * @return Unique event identifier for the event that's created. Null in the case of failure
    */
   public String saveEvent(String calendarID, String eventID, String title, long startTimeStamp, long endTimeStamp, boolean allDayEvent, boolean taskOnly, String notes, String location, String reminders) {
      return AndroidCalendarImpl.saveEvent(com.codename1.impl.android.AndroidNativeUtil.getActivity(), calendarID, eventID, title, startTimeStamp, endTimeStamp, allDayEvent, taskOnly, notes, location, reminders);      
   }

   /**
    * Removes event with previously returned eventID
    *
    * @param calendarID	- As returned from openCalendar
    * @param eventID 	- As returned from saveEvent
    * @return If removal successful
    */
   public boolean removeEvent(String calendarID, String eventID) {
      return AndroidCalendarImpl.removeEvent(com.codename1.impl.android.AndroidNativeUtil.getActivity(), calendarID, eventID);      
   }

   /**
    * Query calendar and return details as XML string
    *
    * @param calendarID	- As returned from openCalendar
    * @param eventID    - As returned from saveEvent
    * @return XML String of event details. Null if not found.
    */
   public String getEventByID(String calendarID, String eventID) {
      return AndroidCalendarImpl.getEventByID(com.codename1.impl.android.AndroidNativeUtil.getActivity(), calendarID, eventID);      
   }

   /**
    * Returns all events in the calendar between startTimeStamp and endTimeStamp
    *
    * @param calendarID     - As returned from openCalendar
    * @param startTimeStamp - Event starting time stamp (unix time)
    * @param endTimeStamp   - Event ending time stamp (unix time)
    * @return XML string of all events found
    */
   public String getEvents(String calendarID, long startTimeStamp, long endTimeStamp) {
      return AndroidCalendarImpl.getEvents(com.codename1.impl.android.AndroidNativeUtil.getActivity(), calendarID, startTimeStamp, endTimeStamp);      
   }

   /**
    * The following are for registering/deregistering for receiving callbacks when calendar events are modified
    */
   public void registerForEventNotifications() {
      
   }

   public void deregisterForEventNotifications() {
      
   }
   
   public boolean isSupported() {
      return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
   }

}
