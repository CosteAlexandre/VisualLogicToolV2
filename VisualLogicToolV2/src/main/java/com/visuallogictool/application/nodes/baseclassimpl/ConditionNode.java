package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;

import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.baseclass.MultipleOutput;
import com.visuallogictool.application.nodes.baseclassimpl.conditions.ConditionHour;
import com.visuallogictool.application.nodes.baseclassimpl.conditions.ConditionJson;
import com.visuallogictool.application.nodes.information.Field;
import com.visuallogictool.application.nodes.information.NodeInformations;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;
import com.visuallogictool.application.nodes.information.concrete.DropdownField;
import com.visuallogictool.application.nodes.information.concrete.Option;
import com.visuallogictool.application.nodes.information.concrete.TextboxField;

import akka.actor.ActorRef;

public class ConditionNode<T> extends MultipleOutput{

	private ConditionNodeConfiguration configuration;
	private ArrayList<Condition> conditions;
	
	private HashMap<String, HashMap<String, BiFunction<Object,String,Boolean>>> types; 
	
	private HashMap<String, BiFunction<Object,String,Boolean>> hourFunction;
	private HashMap<String, BiFunction<Object, String, Boolean>> json;
	
	
	public ConditionNode(String id, String logId , ConditionNodeConfiguration configuration) {
		super(id, logId);
		
		this.shortName = "CN";
		this.logName = this.shortName + "-" + logId;
		
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
					this.getOutput().get(condition.getOutPut()).forEach(output -> { 
						output.tell(new MessageNode(context), ActorRef.noSender());
					});
				}
				resp = false;
				
			} else {
				resp = this.types.get(type).get(function).apply(context.get(condition.getVal1()), condition.getVal2());
				if(resp) {
					log.info("SENDING TO : " + condition.getOutPut());
					this.getOutput().get(condition.getOutPut()).forEach(output -> {
						output.tell(new MessageNode(context), ActorRef.noSender());
					});
					
				}
			}
			
		});
	}

	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("ConditionNode", "Creates new conditions", "Creates multiple conditions and depending on the output of those conditions redirect it").
				setFields(new Field("val1", "String", "first value", "the parameter of the context that will be used for the first parameter")).
				setFields(new Field("val2", "String", "second value", "the value that will be used for the second parameter")).
				setFields(new Field("typeval2", "type", "type of value 2", "decide the type of the second value")).
				setFields(new Field("condition", "String", "conditions", "the condition that the user wanna use")).
//				setFields(new Field("output", "int", "output result", "Where the output will go")).
				
									setFields(new Field("hourAboveOrEquals", "hour", "check above or equals hour", "check if the hour is above or equal to the given parameter")).
									setFields(new Field("hourBelow", "hour", "check below hour", "check if the hour is below the given parameter")).
									setFields(new Field("containsJson", "json", "contains", "check if the value of the json contains the given parameter")).
									setFields(new Field("inferiorThanJson", "json", "inferior than integer", "check if the hour is the value of the json (converted to an integer) is inferior than the given parameter"));
		
		ArrayList<Option> optionsType = new ArrayList<Option>();
		optionsType.add(new Option("hour","hour"));
		optionsType.add(new Option("json","json"));
		optionsType.add(new Option("else","else"));
		
		ArrayList<Option> optionsFunction = new ArrayList<Option>();
		optionsFunction.add(new Option("hour>=","hour>="));
		optionsFunction.add(new Option("hour<","hour<"));
		optionsFunction.add(new Option("json<","json<"));
		optionsFunction.add(new Option("jsoncontains","jsoncontains"));
		optionsFunction.add(new Option("else","else"));

		informations = informations.setFieldBase(new TextboxField(null, "val1", "valeur 1", false, 1, null),
												 new TextboxField(null, "val2", "valeur 2", false, 2, null),
												 new DropdownField(null, "typeVal2", "type value 2", false, 3, optionsType),
												 new DropdownField(null, "condition", "condition", false, 4, optionsFunction));
//												 new TextboxField(null, "output", "output", true, 5, null));

		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.ConditionNodeConfiguration"
				,"com.visuallogictool.application.nodes.baseclassimpl.ConditionNode");
		
		informations = informations.setShortName("CN");
		
		informations = informations.setImageUrl("https://img.icons8.com/metro/26/000000/split.png");
		
		return informations.getNodeInformations();
	}
}
