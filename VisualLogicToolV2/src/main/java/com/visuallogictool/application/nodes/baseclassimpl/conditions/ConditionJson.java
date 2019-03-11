package com.visuallogictool.application.nodes.baseclassimpl.conditions;

import java.util.HashMap;
import java.util.function.BiFunction;

public class ConditionJson {

	
	
	private HashMap<String, BiFunction<Object, String, Boolean>> json;

	public ConditionJson() {
		json = new HashMap<String, BiFunction<Object,String,Boolean>>();
		
		json.put("contains", containsJson);
		json.put("<", inferiorThanJson);
		
		
		
	}
	
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
	
	
	public HashMap<String, BiFunction<Object, String, Boolean>> getJson() {
		return json;
	}
	
}
