package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.HashMap;

import com.visuallogictool.application.nodes.BaseNode;
import com.visuallogictool.application.nodes.information.Field;
import com.visuallogictool.application.nodes.information.NodeInformations;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;
import com.visuallogictool.application.nodes.information.concrete.TextboxField;



public class PrependNode extends BaseNode {
	
	 

	private String var;
	private String value;
	private String newVariable;
	private PrependNodeConfiguration prependNodeConfiguration;
	

	public PrependNode(String id, String logId, String flowId, PrependNodeConfiguration prependNodeConfiguration) {
		super(id, logId, flowId);
		
		this.shortName = "PN";
		this.setLogName(flowId, logId);
		
		this.var = prependNodeConfiguration.getVar();
		this.value = prependNodeConfiguration.getValue();
		this.newVariable = prependNodeConfiguration.getNewVariable();
		
		this.prependNodeConfiguration = prependNodeConfiguration;
		
		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		String variable = (String) context.get(this.var);

		log.info("Prepending node");
		log.debug("before prepending : {}", variable);		
		variable =  this.value + variable;
		log.debug("After prepending {}", variable);
		if(this.newVariable.equals("")) {
			context.put(this.var, variable);
			log.debug("erasing var");
		}else {
			context.put(this.newVariable, variable);
			log.debug("putting in new variable {}",this.newVariable);
		}
		
		
		this.sendingToAllActor(context);	
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("PrependNode", "Prepend at the end of the String", "Prepend at the end of the String").
									setFields(new Field("var", "String", "variable", "name of the variable that you want to modify")).
									setFields(new Field("value", "String", "value", "the value that will be added at the end of the string")).
									setFields(new Field("newVariable", "String", "newVariable", "the name of the new variable where the value will be stored"));
		
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", false, 1, null)).
									setFieldBase(new TextboxField(null, "value", "value", false, 2, null)).
									setFieldBase(new TextboxField(null, "newVariable", "newVariable", false, 3, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.PrependNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.PrependNode");
		
		informations = informations.setShortName("PN");
		
		informations = informations.setImageUrl("https://img.icons8.com/plasticine/48/000000/plus.png");
		
		return informations.getNodeInformations();
	}
	
	



}
