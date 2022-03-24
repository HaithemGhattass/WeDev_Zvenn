package com.codename1.calendar.impl;


/**
 *  Interface to calendar functions for Android & iOS
 *  
 *  @author Kapila de Lanerolle
 *  @author Andreas Heydler
 */
public interface CalendarNativeInterface extends com.codename1.system.NativeInterface {

	/**
	 *  @return If we have permissions for calendar modifications
	 */
	public boolean hasPermissions();

	/**
	 *  Query device and return number of available calendars.
	 *  
	 *  @return Number of available calendars
	 *  
	 */
	public int getCalendarCount();

	/**
	 *  @param offset - offset into available calendar list 
	 *  @return - Name of the calendar at the offset. Null if there is no calendar at this offset.
	 */
	public String getCalendarName(int offset);

	/**
	 *  Opens the named calendar creating it if necessary.
	 * 
	 *  @param calendarName      - Name of calendar to be opened/created
	 *  @param createIfNotExists - Indicates if a calendar must be created if there is no calendar found by the name provided
	 *  @return Unique ID to be used in other methods referencing this calendar. Null in case of failure or calendar does not exist
	 */
	public String openCalendar(String calendarName, boolean createIfNotExists);

	/**
	 *  Add/Edit an event in named calendar
	 * 
	 *  @param calendarID     - As returned from openCalendar
	 *  @param eventID        - Event Identifier. Pass null for new Events
	 *  @param title          - Title of the Calendar Event
	 *  @param startTimeStamp - Event starting time stamp (unix time)
	 *  @param endTimeStamp   - Event ending time stamp (unix time)
	 *  @param allDayEvent    - The event is an all day event
	 *  @param taskOnly       - Task Only. No time associated with this.
	 *  @param notes          - Any notes for the event
	 *  @param location       - Location of the event
	 *  @param reminders      - alarm offsets (in seconds) in CSV format. Pass null for no alarms
	 *  @return Unique event identifier for the event that's created. Null in the case of failure
	 */
	public String saveEvent(String calendarID, String eventID, String title, long startTimeStamp, long endTimeStamp, boolean allDayEvent, boolean taskOnly, String notes, String location, String reminders);

	/**
	 *  Removes event with previously returned eventID
	 * 
	 *  @param calendarID	- As returned from openCalendar
	 *  @param eventID 	- As returned from saveEvent
	 *  @return If removal successful
	 */
	public boolean removeEvent(String calendarID, String eventID);

	/**
	 *  Query calendar and return details as XML string
	 * 
	 *  @param calendarID	- As returned from openCalendar
	 *  @param eventID    - As returned from saveEvent
	 *  @return XML String of event details. Null if not found.
	 */
	public String getEventByID(String calendarID, String eventID);

	/**
	 *  Returns all events in the calendar between startTimeStamp and endTimeStamp
	 * 
	 *  @param calendarID     - As returned from openCalendar
	 *  @param startTimeStamp - Event starting time stamp (unix time)
	 *  @param endTimeStamp   - Event ending time stamp (unix time)
	 *  @return XML string of all events found
	 */
	public String getEvents(String calendarID, long startTimeStamp, long endTimeStamp);

	/**
	 *  The following are for registering/deregistering for receiving callbacks when calendar events are modified
	 */
	public void registerForEventNotifications();

	public void deregisterForEventNotifications();
}
