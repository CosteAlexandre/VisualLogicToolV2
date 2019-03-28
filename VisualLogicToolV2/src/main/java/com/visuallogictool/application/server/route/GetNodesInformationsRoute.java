package com.visuallogictool.application.server.route;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;

import com.visuallogictool.application.messages.message.HttpRequestReceived;
import com.visuallogictool.application.nodes.BaseNode;
import com.visuallogictool.application.nodes.information.NodeInformations;

import akka.actor.Props;

public class GetNodesInformationsRoute extends Route{

	public static Props props() {
	    return Props.create(GetNodesInformationsRoute.class, () -> new GetNodesInformationsRoute());
	}

	public GetNodesInformationsRoute() {
		super();
		
	}
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().match(HttpRequestReceived.class, apply -> {
			Reflections reflections = new Reflections("com.visuallogictool.application.nodes");    
			Set<Class<? extends BaseNode>> classes = reflections.getSubTypesOf(BaseNode.class);
				
			ArrayList<NodeInformations> nodes = new ArrayList<NodeInformations>();
			
			classes.forEach(classe -> {			
				
				if(reflections.getSubTypesOf(classe).size()==0) {
					try {
						nodes.add((NodeInformations) classe.getMethod("getGUI", null).invoke(null, null));
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			
			 /*Reflections reflections = new Reflections("java.util");
			    Set<Class<? extends List>> classes = reflections.getSubTypesOf(java.util.List.class);
			    for (Class<? extends List> aClass : classes) {
			        System.out.println(aClass.getName());
			        if(aClass == ArrayList.class) {
			            List list = aClass.newInstance();
			            list.add("test");
			            System.out.println(list.getClass().getName() + ": " + list.size());
			        }
			    }*/
			/*ArrayList<NodeInformations> truc = new ArrayList<NodeInformations>();
			
			truc.add(ApiRest.getGUI());
			*/
			this.sendResponse(nodes);
			
			
			
		}).build();
		
	}
	
}
