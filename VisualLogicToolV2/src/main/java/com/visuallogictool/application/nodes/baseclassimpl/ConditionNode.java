package com.visuallogictool.application.nodes.baseclassimpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.function.BiFunction;

import com.visuallogictool.application.errors.DateError;
import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.BaseNode;
import com.visuallogictool.application.nodes.baseclassimpl.conditions.ConditionHour;
import com.visuallogictool.application.nodes.baseclassimpl.conditions.ConditionJson;

import akka.actor.ActorRef;

public class ConditionNode<T> extends BaseNode{

	private ConditionNodeConfiguration configuration;
	private ArrayList<Condition> conditions;
	
	private HashMap<String, HashMap<String, BiFunction<Object,String,Boolean>>> types; 
	
	private HashMap<String, BiFunction<Object,String,Boolean>> hourFunction;
	private HashMap<String, BiFunction<Object, String, Boolean>> json;
	
	
	public ConditionNode(int id, ConditionNodeConfiguration configuration) {
		super(id);
		this.configuration = configuration;
		conditions = configuration.getConditions();
		initializeMap();
	}

	
	private void initializeMap() {

		types = new HashMap<String, HashMap<String,BiFunction<Object,String,Boolean>>>();
		
		hourFunction = (new ConditionHour()).getHourFunction();		
		json = (new ConditionJson()).getJson();
		
		types.put("hour", hourFunction);
		types.put("json", json);
		


		/*
		 * 
		 */
		
	}
	
	private boolean resp;
	@Override
	public void processMessage(HashMap<String, Object> context) {
		resp = false;
		this.conditions.forEach( condition -> {
			String type = condition.getTypeVal2();
			String function = condition.getCondition();
			
			if(type.equals("else")) {
				if(!resp) {
					log.info("ELSE SENDING TO : " + condition.getOutPut());
					this.getOutput().get(condition.getOutPut()).tell(new MessageNode(context), ActorRef.noSender());
				}
				resp = false;
				
			} else {
				resp = this.types.get(type).get(function).apply(context.get(condition.getVal1()), condition.getVal2());
				if(resp) {
					log.info("SENDING TO : " + condition.getOutPut());
					this.getOutput().get(condition.getOutPut()).tell(new MessageNode(context), ActorRef.noSender());
				}
			}
			
			
		});
	}

	@Override
	public void getGUI() {
		// TODO Auto-generated method stub
		
	}

}
