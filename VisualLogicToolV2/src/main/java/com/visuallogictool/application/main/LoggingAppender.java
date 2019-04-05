package com.visuallogictool.application.main;

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
		this.size = 10;
		
		System.out.println("Logger appender started");
//		this.getContext().getSystem().actorSelection("/user/director").tell(new GetAllFlow(), this.getSender());;

		
	}
	
	
	@Override
	protected void append(ILoggingEvent eventObject) {
		String supervisor = eventObject.getMDCPropertyMap().get("supervisor");
		
		if(supervisor != null) {
			System.out.println("///////////////////////////////////");
			System.out.println();
			if(!this.cicularBuffers.containsKey(supervisor)) {
				EvictingQueue<String> evictingQueue = EvictingQueue.create(this.size);
				this.cicularBuffers.put(supervisor, evictingQueue);
			}
			this.cicularBuffers.get(supervisor).add(eventObject.toString());

			//System.out.println(supervisor);
			System.out.println(eventObject.toString());
			//System.out.println(eventObject.getMessage());
			//System.out.println(eventObject.getTimeStamp());
			
			System.out.println();
			System.out.println("///////////////////////////////////");
			
			
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
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <target>System.out</target>
        <encoder>
            <pattern>%X{akkaTimestamp} %-5level[%thread] %logger{0} [group : %X{group}, supervisor: %X{supervisor}, nodeId: %X{nodeId}] - %msg%n</pattern>
        </encoder>
    </appender>
    
    <appender-ref ref="CONSOLE"/>
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

