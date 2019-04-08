package com.visuallogictool.application.server.route;

import com.google.common.collect.EvictingQueue;
import com.visuallogictool.application.main.LoggingAppender;
import com.visuallogictool.application.messages.flow.GetFlowGraph;
import com.visuallogictool.application.messages.message.HttpRequestReceived;

import akka.actor.Props;

public class GetFlowLogRoute extends Route{

	private LoggingAppender loggingAppender;
	
	public static Props props() {
	    return Props.create(GetFlowLogRoute.class, () -> new GetFlowLogRoute());
	}

	
	public GetFlowLogRoute() {
		super();
		
		
	}
	
	@Override
	public void preStart() throws Exception {
		super.preStart();
		loggingAppender = LoggingAppender.getLoggingAppender();
		
/*		
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        ch.qos.logback.classic.LoggerContext loggerContext = root.getLoggerContext();

        root.addAppender(new LoggingAppender());
*/		
	}
	
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().match(HttpRequestReceived.class, apply -> {
			String body = this.getBody(apply);
			
			EvictingQueue<String> circularBuffer = loggingAppender.getLogSupervisor(body);
			
			if(circularBuffer == null) {
				this.sendResponse("No such supervisor");
			} else {
				//circularBuffer.
				this.sendResponse(circularBuffer);
			}
			
		}).build();
		
	}
	
}
