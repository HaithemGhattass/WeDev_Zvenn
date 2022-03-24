#import "com_codename1_calendar_impl_CalendarNativeInterfaceImpl.h"

@implementation com_codename1_calendar_impl_CalendarNativeInterfaceImpl

/*
 * Traverse through all event sources available and enumarates accissible calendars
 * from all of them. After that, returns the name of the calendar at the given offset
 *
 * @param   offset - Offset into the list of available calendars
 * @return  Name of the calendar at the given offset. Nil in the case of failure.
 * @author  Kapila de Lanerolle
 * @date    1 Feb 2014
 */
-(NSString*)getCalendarName:(int)offset{

    //Make sure we have user permission to continue
    if (NO == [self hasPermissions]) {
        return nil;
    }

    if (offset >= 0) {
        EKEventStore *eventStore = [[EKEventStore alloc] init];
        NSMutableSet *calendars = [self getAvailableCalendars:eventStore];
        
        //Make sure the offset is valid before searching for the name
        if (calendars.count > offset){
            //Let's sort them by title here. This is because the order of calendars returned is not consistant.
            NSSortDescriptor *sortDescriptor = [NSSortDescriptor sortDescriptorWithKey:@"calendarIdentifier" ascending:YES];
            NSArray *sortedCalendars = [calendars sortedArrayUsingDescriptors:[NSArray arrayWithObject:sortDescriptor]];
            //Let's find the name of the calendar with the offset provided
            int i = 0;
            for (EKCalendar *calendar in sortedCalendars) {
                if (i < offset) {
                    i++;
                    continue;
                }
                return calendar.title;
            }
        }
    }
    return nil;
}

/*
 * Traverse through all event sources available and counts accissible calendars
 * from all of them
 *
 * return Total number of accissible calendars from all available event sources
 * @author  Kapila de Lanerolle
 * @date    1 Feb 2014
 */
-(int)getCalendarCount{
    //Make sure we have user permission to continue
    if (NO == [self hasPermissions]) {
        return 0;
    }

    EKEventStore *eventStore = [[EKEventStore alloc] init];
    NSMutableSet *calendars = [self getAvailableCalendars:eventStore];
    return calendars.count;
}

/*
 * Checks the calendar permission status. If the permissions has not been requested yet,
 * prompt the user for permissions. Note that after the user makes the permission choice,
 * this prompt will no longer be displayed. User is still able to change the permissions
 * on the Settings panel of the device.
 *
 * @return   If the application has permissions to modify Calendar Events
 * @author   Kapila de Lanerolle
 * @date     1 Feb 2014
 */
-(BOOL)hasPermissions{
    
    __block BOOL result = NO;
    
    EKEventStore *eventStore = [[EKEventStore alloc] init];
    
    //Check permissions
    EKAuthorizationStatus status = [EKEventStore authorizationStatusForEntityType:EKEntityTypeEvent];
    
    switch (status) {
        case EKAuthorizationStatusAuthorized:
            result = YES;
            break;
        case EKAuthorizationStatusDenied:
        case EKAuthorizationStatusRestricted:
            //There is no point prompt for permissions here since iOS will prevent the prompt being displayed.
            break;
        case EKAuthorizationStatusNotDetermined:
            //Permission have not yet been determined. Prompt the user for permissions
            [eventStore requestAccessToEntityType:EKEntityTypeEvent completion:^(BOOL granted, NSError *error) {
                if (granted) {
                    result = YES;
                }
            }];
            break;
            
        default:
            break;
    }
    
    return result;
}

/*
 * Seaches through available calendars to find the one with the given name. This function
 * just returns the calendarIdentifier of the first calendar that has the name provided.
 *
 * @param   calendarName - Name of the calendar
 * @param   createIfNotExists - Whether we should attempt to create a calendar by this name, if not found
 * @return  CalendarIdentifier of the calendar with the name provided. Nil in the case of failure.
 * @author  Kapila de Lanerolle
 * @date    1 Feb 2014
 */
-(NSString*)openCalendar:(NSString*)calendarName param1:(BOOL)createIfNotExists{
    
    //Make sure we have user permission to continue
    if (NO == [self hasPermissions]) {
        return nil;
    }
    
    //calendarName can't be null or empty
    if (0 == [calendarName length]) {
        return nil;
    }
    
    //Search for the calendar with the given name
    EKEventStore *eventStore = [[EKEventStore alloc] init];
    NSMutableSet *calendars = [self getAvailableCalendars:eventStore];
    for (EKCalendar *calendar in calendars) {
        if ([calendarName isEqualToString:calendar.title]) {
            return calendar.calendarIdentifier;
        }
    }
    //We haven't found a match.
    
    //TODO: The interface offers the options of creating a calendar by the given name in this case.
    //Need to explore the possibility of this. We may need more info than the calender name (like which event sorce we should use).
    //Just return nil for now.
    
    return nil;
}

/*
 * Add/Edit an event in named calendar
 *
 * @param calendarID     - As returned from openCalendar. Pass null for default calendar
 * @param eventID        - Event Identifier. Pass null for new Events
 * @param title          - Title of the Calendar Event
 * @param startTimeStamp - Event starting time stamp (unix time) in milliseconds
 * @param endTimeStamp   - Event ending time stamp (unix time) in milliseconds
 * @param allDayEvent    - The event is an all day event
 * @param taskOnly       - Task Only. No time associated with this.
 * @param notes          - Any notes for the event
 * @param location       - Location of the event
 * @param reminders      - alarm offsets (in seconds) in CSV format. Pass null for no alarms
 * @return Unique event identifier for the event that's created. Null in the case of failure
 * @author  Kapila de Lanerolle
 * @date    2 Feb 2014
 */
-(NSString*)saveEvent:(NSString*)calendarID param1:(NSString*)eventID param2:(NSString*)title param3:(long long)startTimeStamp param4:(long long)endTimeStamp param5:(BOOL)allDayEvent param6:(BOOL)taskOnly param7:(NSString*)notes param8:(NSString*)location param9:(NSString*)reminders{
    
    //Make sure we have user permission to continue
    if (NO == [self hasPermissions]) {
        return nil;
    }
    
    EKEventStore *eventStore = [[EKEventStore alloc] init];
    
    //Find the calendar with the given ID. If calendar ID not provided, use the device default calendar
    EKCalendar * calendar = [eventStore defaultCalendarForNewEvents];
    if(0 < [calendarID length]){
        NSMutableSet *calendars = [self getAvailableCalendars:eventStore];
        BOOL calendarFound = NO;
        for (EKCalendar *cal in calendars) {
            if ([calendarID isEqualToString:cal.calendarIdentifier]) {
                calendar = cal;
                calendarFound = YES;
                break;
            }
        }
        //If calendarID is provided but we couldn't locate the calendar, return here
        if (!calendarFound) {
            return nil;
        }
    }
    
    //Find the event with the given ID. If id not given, create a new event
    EKEvent *event = nil;
    if (0 < [eventID length]) {
        event = [eventStore eventWithIdentifier:eventID];
        //If we couldn't locate the event with the ID provided, return here
        if (nil == event) {
            return nil;
        }
    }else{
        event = [EKEvent eventWithEventStore:eventStore];
        event.calendar = calendar;
    }
    
    //Set event properties
    event.title = title;
    event.notes = notes;
    event.allDay = allDayEvent;
    if (!allDayEvent) {
        NSDate *startDate = [NSDate dateWithTimeIntervalSince1970:(NSTimeInterval)startTimeStamp/1000];
        NSDate *endDate = [NSDate dateWithTimeIntervalSince1970:(NSTimeInterval)endTimeStamp/1000];
        event.startDate = startDate;
        event.endDate = endDate;
    }
    event.location = location;
    
    //Add alarms to the event
    //First, remove all existing alarms
    NSArray *alarms = event.alarms;
    if (nil != alarms) {
        for (EKAlarm *alarm in alarms) {
            [event removeAlarm:alarm];
        }
    }
    
    //Alarms are submitted as a stirng in CSV format. The alarm offsets must be
    //negative values. If not, they will be converted to a negative value before applying
    NSArray *reminderArray = [reminders componentsSeparatedByString:@","];
    for (NSString *str in reminderArray) {
        int minutes = [str intValue];
        if (minutes > 0) {
            minutes *= -1;
        }
        //Add this only if we don't have this reminder already
        BOOL alarmExists = NO;
        NSArray *alarms = event.alarms;
        if (nil != alarms) {
            for (EKAlarm *alarm in alarms) {
                if (alarm.relativeOffset == minutes) {
                    alarmExists = YES;
                    break;
                }
            }
        }
        if (!alarmExists) {
            EKAlarm * alarm = [EKAlarm alarmWithRelativeOffset:minutes];
            [event addAlarm:alarm];
            //We can only have two alarms
            if (event.alarms.count >= 2) {
                break;
            }
        }
        
    }
    
    //Finally, Save the event
    NSError *saveError = nil;
    BOOL result = [eventStore saveEvent:event span:EKSpanThisEvent commit:YES error:&saveError];
    
    if (result) {
        eventID = event.eventIdentifier;
        NSLog(@"Event created successfully");
    }else{
        NSLog(@"Event creation failed.");
    }
    
    return eventID;
}

/*
 * Removes the event with the ID provided from the calendar provided
 * calendarID parameter is ignored since eventID is unique across all calendars on iOS
 *
 * @param calendarID     - As returned from openCalendar. Pass null for default calendar.
 * @param eventID        - Event Identifier. Pass null for new Events
 * @return  Wheather the event was successfully removed
 * @author  Kapila de Lanerolle
 * @date    2 Feb 2014
 */
-(BOOL)removeEvent:(NSString*)calendarID param1:(NSString*)eventID{
   
    //Make sure we have user permission to continue
    if (NO == [self hasPermissions]) {
        return NO;
    }


    //Make sure we have an eventID
    if (0 == [eventID length]) {
        return NO;
    }
    
    EKEventStore *eventStore = [[EKEventStore alloc] init];
    
    NSError *removeError = nil;
    BOOL success = NO;
    
    //Find the event with unique id
    EKEvent *event = [eventStore eventWithIdentifier:eventID];
    
    //If event could be located, then delete it
    if (event != nil) {
        success = [eventStore removeEvent:event span:EKSpanThisEvent commit:YES error:&removeError];
    }
    
    return success;

}

/*
 * Returns details of the event with the given ID in an XML string
 * calendarID parameter is ignored since eventID is unique across all calendars on iOS
 *
 * @param calendarID     - As returned from openCalendar. Pass null for default calendar.
 * @param eventID        - Event Identifier. Pass null for new Events
 * @return  XML String of event details
 * @author  Kapila de Lanerolle
 * @date    2 Feb 2014
 */
-(NSString*)getEventByID:(NSString*)calendarID param1:(NSString*)eventID{
    
    //We are returning an XML document (String) in all cases here.
    //Let's start building it write away
    NSMutableString *xmlString = [[NSMutableString alloc] init];
    [xmlString appendString:[self getResponseHeaderXML:@"getEventByID"]];
    
    //Verify parameters. We don't care about the calendarID because it is ignored anyway.
    //We need to make sure we have an event id
    if (0 == [eventID length]) {
        [xmlString appendString:[self getErrorXML:@"No Event ID supplied."]];
        [xmlString appendString:[self getResponseFooterXML:NO]];
        return xmlString;
    }
    
    //If we don't have permissions for this operation, return an error
    if ([self hasPermissions] == NO) {
        [xmlString appendString:[self getErrorXML:@"Permission denied."]];
        [xmlString appendString:[self getResponseFooterXML:NO]];
        return xmlString;
    }
    
    //Find the event with event ID provided
    EKEventStore *eventStore = [[EKEventStore alloc] init];
    EKEvent *event = [eventStore eventWithIdentifier:eventID];
    
    //If we couldn't locate the event, say so
    if (nil == event) {
        [xmlString appendString:[self getErrorXML:@"Event not found."]];
        [xmlString appendString:[self getResponseFooterXML:NO]];
        return xmlString;
    }
    
    //We have located the event. Encode it in XML
    [xmlString appendString:[self eventToXML:event]];

    //Append the footer and return the XML
    [xmlString appendString:[self getResponseFooterXML:YES]];
    return xmlString;
}


/*
 * Searches events in the calendar with in the period specified
 *
 * @param calendarID        - As returned from openCalendar. Pass null for default calendar.
 * @param startTimeStamp    - Event search starting time stamp (unix time) in milliseconds
 * @param startTimeStamp    - Event search ending time stamp (unix time) in milliseconds
 * @return  XML String of event search details
 * @author  Kapila de Lanerolle
 * @date    2 Feb 2014
 */
-(NSString*)getEvents:(NSString*)calendarID param1:(long long)startTimeStamp param2:(long long)endTimeStamp{
    //We are returning an XML document (String) in all cases here.
    //Let's start building it write away
    NSMutableString *xmlString = [[NSMutableString alloc] init];
    [xmlString appendString:[self getResponseHeaderXML:@"getEvents"]];
    
    //If we don't have permissions for this operation, return an error
    if ([self hasPermissions] == NO) {
        [xmlString appendString:[self getErrorXML:@"Permission denied."]];
        [xmlString appendString:[self getResponseFooterXML:NO]];
        return xmlString;
    }
    
    EKEventStore *eventStore = [[EKEventStore alloc] init];
    
    //Find the calendar with the given ID. If calendar ID not provided, use the device default calendar
    EKCalendar * calendar = [eventStore defaultCalendarForNewEvents];
    if(0 < [calendarID length]){
        NSMutableSet *calendars = [self getAvailableCalendars:eventStore];
        BOOL calendarFound = NO;
        for (EKCalendar *cal in calendars) {
            if ([calendarID isEqualToString:cal.calendarIdentifier]) {
                calendar = cal;
                calendarFound = YES;
                break;
            }
        }
        //If calendarID is provided but we couldn't locate the calendar, return here
        if (!calendarFound) {
            [xmlString appendString:[self getErrorXML:@"Calendar not found."]];
            [xmlString appendString:[self getResponseFooterXML:NO]];
            return xmlString;
        }
    }

    
    NSDate *startDate = [NSDate dateWithTimeIntervalSince1970:(NSTimeInterval)startTimeStamp/1000];
    NSDate *endDate = [NSDate dateWithTimeIntervalSince1970:(NSTimeInterval)endTimeStamp/1000];
    
    //Create the predicate;
    NSArray *calendarArray = [NSArray arrayWithObjects:calendar, nil];
    NSPredicate *searchPredicate = [eventStore predicateForEventsWithStartDate:startDate endDate:endDate calendars:calendarArray];
    
    if (searchPredicate == nil) {
        [xmlString appendString:[self getErrorXML:@"Failed to create a search predicate with supplied parameters"]];
        [xmlString appendString:[self getResponseFooterXML:NO]];
        return xmlString;
    }
    
    NSArray *events = [eventStore eventsMatchingPredicate:searchPredicate];
    
    [xmlString appendString:@"<eventList>"];
    [xmlString appendString:[NSString stringWithFormat:@"<searchStartTimeStamp>%lld</searchStartTimeStamp>",startTimeStamp]];
    [xmlString appendString:[NSString stringWithFormat:@"<searchEndTimeStamp>%lld</searchEndTimeStamp>", endTimeStamp]];
    
    if (events != nil) {
        for (EKEvent *event in events) {
            [xmlString appendString:[self eventToXML:event]];
        }
    }
    
    
    [xmlString appendString:@"</eventList>"];
    [xmlString appendString:[self getResponseFooterXML:YES]];
    
    return xmlString;
}

-(void)registerForEventNotifications{
}

-(void)deregisterForEventNotifications{
}

-(BOOL)isSupported{
    return YES;
}


/*******************************************************************************************************
Helper methods for the class. These are not declared in the header. 
Shouldn't be used directly by the class user.
******************************************************************************************************/

/*
 * Traverse through all event sources available and enumarates all accissible calendars
 * from all of them
 *
 * return Set of EKCalendar objects. Note that the order or calendars returned is not consistant.
 * @author  Kapila de Lanerolle
 * @date    1 Feb 2014
 */

-(NSMutableSet*) getAvailableCalendars: (EKEventStore *) eventStore
{
    //EKEventStore *eventStore = [[EKEventStore alloc] init];
    NSMutableSet * calendars = [[NSMutableSet alloc] init];
    
    for (EKSource *source in eventStore.sources) {
        //Let's check the event source type. We are only interested in the calendars
        //that contain events that are editable by us
        EKSourceType sourceType = source.sourceType;
        if (sourceType != EKSourceTypeCalDAV &&
            sourceType != EKSourceTypeExchange &&
            sourceType != EKSourceTypeLocal) {
            continue;
        }
        
        //We have a source that we are interested in.
        //Let's findout about the available calendars
        NSSet *cals = [source calendarsForEntityType:EKEntityTypeEvent];
        for (EKCalendar *calendar in cals) {
            if(calendar.allowsContentModifications)
            {
                [calendars addObject:calendar];
            }
        }
        
    }
    
    return  calendars;
}

/*
 * Event detailed are encoded in XML and returns as an NSString
 *
 * @param   event - Event that need to be encoded in XML
 * @return  XML String for the event passed in.
 * @author  Kapila de Lanerolle
 * @date    2 Feb 2014
 */
-(NSString *)eventToXML:(EKEvent *)event
{
    NSMutableString *xmlString = [[NSMutableString alloc] init];
    [xmlString appendString:@"<event>"];
    [xmlString appendString:[NSString stringWithFormat:@"<id>%@</id>", event.eventIdentifier]];
    [xmlString appendString:[NSString stringWithFormat:@"<title>%@</title>", event.title]];
    [xmlString appendString:[NSString stringWithFormat:@"<description>%@</description>", (nil != event.notes) ? event.notes : @""]];
    [xmlString appendString:[NSString stringWithFormat:@"<location>%@</location>", event.location]];
    [xmlString appendString:[NSString stringWithFormat:@"<startTimeStamp>%lld</startTimeStamp>", (long long)[event.startDate timeIntervalSince1970]*1000]];
    [xmlString appendString:[NSString stringWithFormat:@"<endTimeStamp>%lld</endTimeStamp>", (long long)[event.endDate timeIntervalSince1970]*1000]];
    [xmlString appendString:[NSString stringWithFormat:@"<createdTimeStamp>%lld</createdTimeStamp>", (long long)[event.creationDate timeIntervalSince1970]*1000]];
    [xmlString appendString:[NSString stringWithFormat:@"<lastModifiedTimeStamp>%lld</lastModifiedTimeStamp>", (long long)[event.lastModifiedDate timeIntervalSince1970]*1000]];
    [xmlString appendString:[NSString stringWithFormat:@"<allDayEvent>%@</allDayEvent>", event.allDay ? @"true" : @"false"]];
    
    NSArray *alarms = event.alarms;
    if (nil != alarms) {
        [xmlString appendString:@"<reminders>"];
        //Go through all alarms
        
        for (EKAlarm *alarm in alarms) {
            [xmlString appendString:[NSString stringWithFormat:@"<reminderOffset>%d</reminderOffset>", (int)alarm.relativeOffset]];
        }
        [xmlString appendString:@"</reminders>"];
    }
    
    [xmlString appendString:@"</event>"];
    return xmlString;
}

/*
 * Generates XML document header for the response for a given request
 *
 * @param   requestName - Name of the request for which we prepare a response
 * @return  XML Formatted response header
 * @author  Kapila de Lanerolle
 * @date    2 Feb 2014
 */

-(NSString *)getResponseHeaderXML:(NSString *)requestName{
    NSMutableString *xmlString = [[NSMutableString alloc] init];
    [xmlString appendString:@"<?xml version=\"1.0\"?>"];
    [xmlString appendString:@"<calendarNativeAPIResponse>"];
    [xmlString appendString:@"<response>"];
    [xmlString appendString:[NSString stringWithFormat:@"<requestType>%@</requestType>", requestName]];
    return xmlString;
}

/*
 * Generates XML document footer for the response
 *
 * @param   completedSuccessfully - Indicates if the request was fullfilled successfully
 * @return  XML Formatted response footer
 * @author  Kapila de Lanerolle
 * @date    2 Feb 2014
 */
-(NSString *)getResponseFooterXML:(BOOL)completedSuccessfully{
    NSMutableString *xmlString = [[NSMutableString alloc] init];
    [xmlString appendString:[NSString stringWithFormat:@"<requestCompletedSuccessfully>%@</requestCompletedSuccessfully>", completedSuccessfully ? @"true" : @"false"]];
    
    [xmlString appendString:@"</response>"];
    [xmlString appendString:@"</calendarNativeAPIResponse>"];
    return xmlString;
}

/*
 * Encodes the provided error text in XML format that can be included in the response
 *
 * @param   errorText - Actual Error text that must be encoded in XML
 * @return  XML Formatted error message
 * @author  Kapila de Lanerolle
 * @date    2 Feb 2014
 */
-(NSString *)getErrorXML:(NSString *)errorText{
    return [NSString stringWithFormat:@"<errors><error>%@</error></errors>", errorText];
}



@end
