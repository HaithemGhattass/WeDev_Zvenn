package com.codename1.calendar;


/**
 *  This class will represent the user visible portable API
 * 
 *  @author Shai Almog
 *  @author Kapila de Lanerolle
 *  @author Andreas Heydler
 */
public final class DeviceCalendar {

	/**
	 *  DeviceCalendar can be use to manipulate device calendars.
	 *  @return	Instance of a DeviceCalendar implementation
	 */
	public static synchronized DeviceCalendar getInstance() {
	}

	/**
	 *  See if calendar interface available
	 *  
	 *  @return true if calendar interface available & have permission to access
	 */
	public boolean hasPermissions() {
	}

	/**
	 *  @return array of Strings of calendar names or null if no permissions
	 */
	public java.util.Collection getCalendars() {
	}

	/**
	 *  Opens the named calendar creating it if necessary.
	 * 
	 *  @param calendarName      - Name of calendar to be opened/created. Pass null for default calendar on the device
	 *  @param createIfNotExists - Indicates if a calendar must be created if there is no calendar found by the name provided
	 *                           
	 *  @return Unique ID to be used in other methods referencing this calendar. Null in case of failure or calendar does not exist
	 */
	public String openCalendar(String calendarName, boolean createIfNotExists) {
	}

	/**
	 *  Add/Edit an event in named calendar.
	 * 
	 *  @param calendarID     - As returned from openCalendar
	 *  @param eventID        - Event Identifier. Pass null for new Events
	 *  @param title          - Title of the Calendar Event
	 *  @param startTimeStamp - Event starting time stamp
	 *  @param endTimeStamp   - Event ending time stamp
	 *  @param allDayEvent    - The event is an all day event
	 *  @param notes          - Any notes for the event
	 *  @param location       - Location of the event
	 *  @param reminders      - alarm offsets (in seconds). Pass null for no alarms
	 *                        
	 *  @return Unique event identifier for the event that's created. Null in the case of failure or no permissions
	 */
	public String saveEvent(String calendarID, String eventID, String title, java.util.Date startTimeStamp, java.util.Date endTimeStamp, boolean allDayEvent, String notes, String location, java.util.Collection reminders) {
	}

	/**
	 *  Removes event with previously returned eventID
	 * 
	 *  @param calendarID	- As returned from openCalendar
	 *  @param eventID 	- As returned from saveEvent
	 *                    
	 *  @return If removal successful
	 */
	public boolean removeEvent(String calendarID, String eventID) {
	}

	/**
	 *  Query calendar and return details as an EventInfo
	 * 
	 *  @param calendarID	- As returned from openCalendar
	 *  @param eventID    - As returned from saveEvent
	 *                    
	 *  @return an EventInfo or null on failure, not found or no permissions.
	 */
	public EventInfo getEventByID(String calendarID, String eventID) {
	}

	/**
	 *  Returns all events in the calendar between startTimeStamp and endTimeStamp
	 * 
	 *  @param calendarID     - As returned from openCalendar
	 *  @param startTimeStamp - Event search starting time stamp                       
	 *  @param endTimeStamp   - Event search ending time stamp
	 *                        
	 *  @return collection of EventInfo's. Returns null in case of failure or no permissions
	 */
	public java.util.Collection getEvents(String calendarID, java.util.Date startTimeStamp, java.util.Date endTimeStamp) {
	}
}
