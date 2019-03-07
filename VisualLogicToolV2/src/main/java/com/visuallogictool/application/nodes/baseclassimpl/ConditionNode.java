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
	
	private HashMap<String, HashMap<String, BiFunction<Object,String,Boolean>>> types; 
	
	private HashMap<String, BiFunction<Object,String,Boolean>> hourFunction;
	private HashMap<String, BiFunction<Object, String, Boolean>> json;
	
	
	public ConditionNode(int id, ConditionNodeConfiguration configuration) {
		super(id);
		this.configuration = configuration;
		conditionList = configuration.getConditionList();
		initializeMap();
	}

	
	private void initializeMap() {

		types = new HashMap<String, HashMap<String,BiFunction<Object,String,Boolean>>>();
		
		hourFunction = new HashMap<String, BiFunction<Object,String,Boolean>>();		
		json = new HashMap<String, BiFunction<Object,String,Boolean>>();
		
		types.put("hour", hourFunction);
		types.put("json", json);
		
		hourFunction.put(">=", hourAboveOrEquals);
		hourFunction.put("<", hourBelow);
		json.put("contains", containsJson);
		json.put("<", inferiorThanJson);
		
		
	}


	private BiFunction<Object, String, Boolean> hourAboveOrEquals = new BiFunction<Object, String, Boolean>() {
		@Override
		public Boolean apply(Object t, String u) {
			DateFormat format = new SimpleDateFormat ("hh:mm:ss");
			try {
				Date date1 = format.parse((String)t);
				Date date2 = format.parse(u);
				return !date1.before(date2);
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
		}
	};
	private BiFunction<Object, String, Boolean> hourBelow = new BiFunction<Object, String, Boolean>() {
		@Override
		public Boolean apply(Object t, String u) {
			DateFormat format = new SimpleDateFormat ("hh:mm:ss");
			try {
				Date date1 = format.parse((String)t);
				Date date2 = format.parse(u);
				return date1.before(date2);
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
		}
	};
	
	private BiFunction<Object, String, Boolean> containsJson = new BiFunction<Object, String, Boolean>() {
		@Override
		public Boolean apply(Object t, String u) {
			
				return t.toString().contains(u);
			
		}
	};
	private BiFunction<Object, String, Boolean> inferiorThanJson = new BiFunction<Object, String, Boolean>() {
		@Override
		public Boolean apply(Object t, String u) {
				double a = Double.parseDouble(t.toString());
				double b = Double.parseDouble(u);
				
				return a < b ;
			
		}
	};
	
	
	private boolean resp;
	@Override
	public void processMessage(HashMap<String, Object> context) {
		resp = false;
		this.conditionList.forEach( condition -> {
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
