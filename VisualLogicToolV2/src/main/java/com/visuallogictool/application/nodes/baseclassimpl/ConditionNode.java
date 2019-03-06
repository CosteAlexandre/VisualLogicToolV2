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

import akka.actor.ActorRef;

public class ConditionNode<T> extends BaseNode{

	private ConditionNodeConfiguration configuration;
	private ArrayList<Condition> conditionList;
	
	private HashMap<String, HashMap<String, BiFunction<String,String,Boolean>>> types; 
	
	private HashMap<String, BiFunction<String,String,Boolean>> hourFunction;
	
	
	public ConditionNode(int id, ConditionNodeConfiguration configuration) {
		super(id);
		this.configuration = configuration;
		conditionList = configuration.getConditionList();
		initializeMap();
	}

	
	private void initializeMap() {

		types = new HashMap<String, HashMap<String,BiFunction<String,String,Boolean>>>();
		
		hourFunction = new HashMap<String, BiFunction<String,String,Boolean>>();		
		
		types.put("hour", hourFunction);
		
		hourFunction.put(">=", hourAboveOrEquals);
		hourFunction.put("<", hourBelow);
		
	}


	private BiFunction<String, String, Boolean> hourAboveOrEquals = new BiFunction<String, String, Boolean>() {
		@Override
		public Boolean apply(String t, String u) {
			DateFormat format = new SimpleDateFormat ("hh:mm:ss");
			try {
				Date date1 = format.parse(t);
				Date date2 = format.parse(u);
				return !date1.before(date2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	};
	private BiFunction<String, String, Boolean> hourBelow = new BiFunction<String, String, Boolean>() {
		@Override
		public Boolean apply(String t, String u) {
			DateFormat format = new SimpleDateFormat ("hh:mm:ss");
			try {
				Date date1 = format.parse(t);
				Date date2 = format.parse(u);
				return date1.before(date2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	};
	
	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		this.conditionList.forEach( condition -> {
			String type = condition.getTypeVal2();
			String function = condition.getCondition();
			boolean resp = this.types.get(type).get(function).apply((String) context.get(condition.getVal1()), condition.getVal2());
			
			if(resp) {
				log.info("SENDING TO : " + condition.getOutPut());
				this.getOutput().get(condition.getOutPut()-1).tell(new MessageNode(context), ActorRef.noSender());
			}
			
		});
	}

	@Override
	public void getGUI() {
		// TODO Auto-generated method stub
		
	}

}
