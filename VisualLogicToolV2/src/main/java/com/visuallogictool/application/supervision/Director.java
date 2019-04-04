package com.visuallogictool.application.supervision;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

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
	
	
	private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	
	public Director(RestServer server, int mode) {
		
		this.server = server;
		this.mode = mode;
		
		this.jsonParser = new JsonParser();
		this.filesUtils = new Files();
		
		this.listSupervisor = new HashSet<ActorRef>();
		this.supervisors = new HashMap<String, ActorRef>();
		this.supervisorsFlow = new HashMap<String, Flow>();
	}
	
	@Override
	public void preStart() throws Exception {
		// TODO Add logic flow route when this actor is created, then start adding new route
		super.preStart();
		log.info("Director created, charging files");
		chargeFiles();
		
	}
	
	
	
	private void chargeFiles() {
		Collection<File> listFiles = filesUtils.getAllJSONFile("src/main/resources/jsonFlow");
		for (File file : listFiles) {
			
			Flow flow = jsonParser.jsonFlowConverter(file);
			initializeFlow(flow);
		}
		log.info("File charged");
	}

	
	private void initializeFlow(Flow flow) {
		log.info("Initializing flow");		
		if(this.supervisors.containsKey(flow.getId())) {
			log.info("The flow with this id already exists");
			return;
		}
		ActorRef supervisor = this.getContext().actorOf(Supervisor.props(flow,null));
		this.listSupervisor.add(supervisor);
		this.supervisors.put(flow.getId(), supervisor);
		this.supervisorsFlow.put(flow.getId(), flow);
	}
	private void initializeFlow(Flow flow,ActorRef sender) {
		log.info("Initializing flow");		
		if(this.supervisors.containsKey(flow.getId())) {
			log.info("The flow with this id already exists");
			return;
		}
		ActorRef supervisor = this.getContext().actorOf(Supervisor.props(flow,sender));
		this.listSupervisor.add(supervisor);
		this.supervisors.put(flow.getId(), supervisor);
		this.supervisorsFlow.put(flow.getId(), flow);
	}

	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().match(CreateFlow.class,apply -> {
			if(this.supervisorsFlow.get(apply.getFlow().getId())!=null) {
				sendResponse("Flow with this id already exists");
			}else {
				initializeFlow(apply.getFlow(),this.getSender());
			}
			
		}).match(FlowCreated.class, apply -> {
			
			Flow flow = this.supervisorsFlow.get(apply.getId());
			jsonParser.addFlowJsonFile(flow,"src/main/resources/jsonFlow/"+flow.getId()+".json");
			
		
			sendResponse("Flow succesfully created");
			
		}).match(DeleteFlow.class, apply -> {
			deleteFlow(apply);
		}).match(GetAllFlow.class, apply ->{
			getAllFlow();
		}).match(GetFlowGraph.class, apply->{
			getFlowGraph(apply.getId());
		}).build();
		
		
		
	}

	private void getFlowGraph(String id) {
		
		this.sendResponse(this.supervisorsFlow.get(id));
		
	}

	private void getAllFlow() {

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
		String id = apply.getId();
		ActorRef supervisor = supervisors.get(id);
		
		String resp = "";
		
		if(supervisor == null) {
			resp = "no such flow";
			log.info("NO SUCH FLOW");
		}else {
			resp = "flow deleted";
			log.info("FLOW DELETED");
			supervisors.remove(id);
			listSupervisor.remove(supervisor);
			supervisorsFlow.remove(id);
			
			this.filesUtils.deleteFile("src/main/resources/jsonFlow/"+id+".json");
			
			this.context().stop(supervisor);
		}
		
		
		sendResponse(resp);
		
	}
	private void sendResponse(Object object) {
		HttpResponse response = HttpResponse.create()
				.withStatus(200)
				.withEntity(jsonParser.getJson(object)).addHeader(new RawHeader("Access-Control-Allow-Origin","*" ));
		
		
		
		this.getSender().tell(response, ActorRef.noSender());
		
	}
	

	
	

}
