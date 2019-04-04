package com.visuallogictool.application.supervision;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.visuallogictool.application.jsonclass.Flow;
import com.visuallogictool.application.messages.flow.CreateFlow;
import com.visuallogictool.application.messages.flow.DeleteFlow;
import com.visuallogictool.application.messages.flow.FlowCreated;
import com.visuallogictool.application.messages.flow.GetAllFlow;
import com.visuallogictool.application.messages.flow.GetFlowGraph;
import com.visuallogictool.application.server.RestServer;
import com.visuallogictool.application.utils.Files;
import com.visuallogictool.application.utils.JsonParser;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.DiagnosticLoggingAdapter;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.model.HttpResponse;
import akka.http.scaladsl.model.headers.RawHeader;

public class Director extends AbstractActor{

	
	
	private RestServer server;
	private int mode;
	private Files filesUtils;
	private JsonParser jsonParser;
	
	private HashSet<ActorRef> listSupervisor;
	private HashMap<String, ActorRef> supervisors;
	private HashMap<String, Flow> supervisorsFlow;
	
	
	protected DiagnosticLoggingAdapter log = Logging.getLogger(this);
	
	
	public Director(RestServer server, int mode) {
		
		this.server = server;
		this.mode = mode;
		
		this.jsonParser = new JsonParser();
		this.filesUtils = new Files();
		
		this.listSupervisor = new HashSet<ActorRef>();
		this.supervisors = new HashMap<String, ActorRef>();
		this.supervisorsFlow = new HashMap<String, Flow>();
		
		Map<String, Object> mdc;
        mdc = new HashMap<String, Object>();
        mdc.put("group", "Director");
        
        log.setMDC(mdc);
        
        
	}
	
	@Override
	public void preStart() throws Exception {
		// TODO Add logic flow route when this actor is created, then start adding new route
		super.preStart();
		log.info("Director started");
		chargeFiles();
		
	}
	
	
	
	private void chargeFiles() {
		log.info("Charging files");
		Collection<File> listFiles = filesUtils.getAllJSONFile("src/main/resources/jsonFlow");
		log.debug("Number of file :{}",listFiles.size());
		int i =0;
		for (File file : listFiles) {
			log.debug("File number {} with path {}",i,file.getAbsolutePath());
			Flow flow = jsonParser.jsonFlowConverter(file);
			initializeFlow(flow);
			i++;
		}
		log.info("Files charged");
	}

	
	private void initializeFlow(Flow flow) {
		log.info("Initializing flow {}", flow.getId());		
		if(this.supervisors.containsKey(flow.getId())) {
			log.info("The flow with this id already exists");
			return;
		}
		ActorRef supervisor = this.getContext().actorOf(Supervisor.props(flow,null),flow.getId());
		this.listSupervisor.add(supervisor);
		this.supervisors.put(flow.getId(), supervisor);
		this.supervisorsFlow.put(flow.getId(), flow);
		log.info("Flow {} initialized",flow.getId());
	}
	private void initializeFlow(Flow flow,ActorRef sender) {
		log.info("Initializing flow");		
		if(this.supervisors.containsKey(flow.getId())) {
			log.info("The flow with this id already exists");
			return;
		}
		log.debug("FLow with id {} sending to actor {}",flow.getId(),sender);
		ActorRef supervisor = this.getContext().actorOf(Supervisor.props(flow,sender));
		this.listSupervisor.add(supervisor);
		this.supervisors.put(flow.getId(), supervisor);
		this.supervisorsFlow.put(flow.getId(), flow);
		log.info("Flow {} initialized",flow.getId());
	}

	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().match(CreateFlow.class,apply -> {
			log.debug("Receive create flow id : {}",apply.getFlow().getId());
			if(this.supervisorsFlow.get(apply.getFlow().getId())!=null) {
				log.info("The flow with the id {} already exists", apply.getFlow().getId());
				sendResponse("Flow with this id already exists");
			}else {
				
				initializeFlow(apply.getFlow(),this.getSender());
			}
			
		}).match(FlowCreated.class, apply -> {
			
			Flow flow = this.supervisorsFlow.get(apply.getId());
			jsonParser.addFlowJsonFile(flow,"src/main/resources/jsonFlow/"+flow.getId()+".json");
			
			log.info("Flow {} succesfully created",flow.getId());
			sendResponse("Flow succesfully created");
			
		}).match(DeleteFlow.class, apply -> {
			log.info("received in deleteFlow");
			deleteFlow(apply);
		}).match(GetAllFlow.class, apply ->{
			log.info("received in GetAllFlow");
			getAllFlow();
		}).match(GetFlowGraph.class, apply->{
			log.info("received in GetFlowGraph");
			getFlowGraph(apply.getId());
		}).build();
		
		
		
	}

	private void getFlowGraph(String id) {
		log.info("Getting flow {}", id);
		this.sendResponse(this.supervisorsFlow.get(id));
		
	}

	private void getAllFlow() {
		log.info("Getting all flow {}");
		ArrayList<String> flowId = new ArrayList<String>();
		
		this.supervisorsFlow.forEach((id,flow) -> {
			if(flow.getGraph()!=null) {
				flowId.add(id);
			}
			
		});
		this.sendResponse(flowId);
	}

	private void deleteFlow(DeleteFlow apply) {
		/*
		 * 	private HashSet<ActorRef> listSupervisor;
	private HashMap<String, ActorRef> supervisors;
	private HashMap<String, Flow> supervisorsFlow;
		 * 
		 * */
		log.info("Deleting flow {}", apply.getId());
		String id = apply.getId();
		ActorRef supervisor = supervisors.get(id);
		
		String resp = "";
		
		if(supervisor == null) {
			resp = "no such flow";
			log.info("Flow {} doesn't exist",apply.getId());
		}else {
			resp = "flow deleted";
			
			supervisors.remove(id);
			listSupervisor.remove(supervisor);
			supervisorsFlow.remove(id);
			
			this.filesUtils.deleteFile("src/main/resources/jsonFlow/"+id+".json");
			
			this.context().stop(supervisor);
			log.info("Flow {} deleted",apply.getId());
		}
		
		
		sendResponse(resp);
		
	}
	private void sendResponse(Object object) {
		log.info("Sending back response");
		log.debug("Send response with object {}",object);
		HttpResponse response = HttpResponse.create()
				.withStatus(200)
				.withEntity(jsonParser.getJson(object)).addHeader(new RawHeader("Access-Control-Allow-Origin","*" ));
		
		
		
		this.getSender().tell(response, ActorRef.noSender());
		
	}
	

	
	

}
