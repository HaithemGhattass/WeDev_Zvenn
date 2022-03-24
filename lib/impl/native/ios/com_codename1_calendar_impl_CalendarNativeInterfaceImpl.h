#import <Foundation/Foundation.h>
#import <EventKit/EventKit.h>

@interface com_codename1_calendar_impl_CalendarNativeInterfaceImpl : NSObject {
}

-(NSString*)getCalendarName:(int)param;
-(int)getCalendarCount;
-(BOOL)hasPermissions;
-(NSString*)openCalendar:(NSString*)param param1:(BOOL)param1;
-(NSString*)saveEvent:(NSString*)param param1:(NSString*)param1 param2:(NSString*)param2 param3:(long long)param3 param4:(long long)param4 param5:(BOOL)param5 param6:(BOOL)param6 param7:(NSString*)param7 param8:(NSString*)param8 param9:(NSString*)param9;
-(BOOL)removeEvent:(NSString*)param param1:(NSString*)param1;
-(NSString*)getEventByID:(NSString*)param param1:(NSString*)param1;
-(NSString*)getEvents:(NSString*)param param1:(long long)param1 param2:(long long)param2;
-(void)registerForEventNotifications;
-(void)deregisterForEventNotifications;
-(BOOL)isSupported;
@end
