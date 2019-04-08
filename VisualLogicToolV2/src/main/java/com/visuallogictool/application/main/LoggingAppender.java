package com.visuallogictool.application.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import com.google.common.collect.EvictingQueue;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class LoggingAppender extends AppenderBase<ILoggingEvent> {

	private static LoggingAppender loggingAppender;
	private HashMap<String, EvictingQueue<String>> cicularBuffers;
	private int size;
	public LoggingAppender() {
		loggingAppender = this;
		this.cicularBuffers = new HashMap<String, EvictingQueue<String>>();
		this.size =1000;
		
		System.out.println("Logger appender started");
//		this.getContext().getSystem().actorSelection("/user/director").tell(new GetAllFlow(), this.getSender());;

		
	}
	
	
	@Override
	protected void append(ILoggingEvent eventObject) {
		String supervisor = eventObject.getMDCPropertyMap().get("supervisor");
		
		if(supervisor != null) {
//			System.out.println("///////////////////////////////////");
//			System.out.println();
			if(!this.cicularBuffers.containsKey(supervisor)) {
				EvictingQueue<String> evictingQueue = EvictingQueue.create(this.size);
				this.cicularBuffers.put(supervisor, evictingQueue);
			}
			

			//System.out.println(supervisor);
//	System.out.println(eventObject.toString());
			//System.out.println(eventObject.getMessage());
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

	        // Get date and time information in milliseconds
	        long now = eventObject.getTimeStamp();

	        // Create a calendar object that will convert the date and time value
	        // in milliseconds to date. We use the setTimeInMillis() method of the
	        // Calendar object.
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(now);

//	        System.out.println(formatter.format(calendar.getTime()));
	        
	        this.cicularBuffers.get(supervisor).add(formatter.format(calendar.getTime()) + " " + eventObject.toString());
	        
//			System.out.println();
//			System.out.println("///////////////////////////////////");
			
			
		}
		
		
	}
	
	public EvictingQueue<String> getLogSupervisor(String supervisor){
		return this.cicularBuffers.get(supervisor);
	}
	
	public static LoggingAppender getLoggingAppender() {
		return loggingAppender;
	}
}
/*

*/

/*
<appender name="SOCKET" class="ch.qos.logback.classic.net.SocketAppender">
<remoteHost>${host}</remoteHost>
<port>${port}</port>
<reconnectionDelay>10000</reconnectionDelay>
<includeCallerData>${includeCallerData}</includeCallerData>
</appender>

        <appender-ref ref="SOCKET" />
*/
/*
Calendar calendar = Calendar.getInstance();
calendar.setTimeInMillis(eventObject.getTimeStamp());

int mYear = calendar.get(Calendar.YEAR);
int mMonth = calendar.get(Calendar.MONTH);
int mDay = calendar.get(Calendar.DAY_OF_MONTH);

System.out.println(mDay+"/"+mMonth+"/"+mYear);*/

