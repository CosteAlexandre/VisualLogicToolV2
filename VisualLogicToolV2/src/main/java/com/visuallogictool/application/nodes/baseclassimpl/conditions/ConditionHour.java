package com.visuallogictool.application.nodes.baseclassimpl.conditions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.function.BiFunction;

public class ConditionHour {

	
	private HashMap<String, BiFunction<Object, String, Boolean>> hourFunction;

	public ConditionHour() {
		hourFunction = new HashMap<String, BiFunction<Object,String,Boolean>>();
		
		hourFunction.put("hour>=", hourAboveOrEquals);
		hourFunction.put("hour<", hourBelow);
	
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
	
	public HashMap<String, BiFunction<Object, String, Boolean>> getHourFunction() {
		return hourFunction;
	}
	
	
}
