import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.flow.NodeCreated;
import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.baseclassimpl.SetJsonNode;
import com.visuallogictool.application.nodes.baseclassimpl.SetJsonNodeConfiguration;
import com.visuallogictool.application.utils.JsonParser;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
public class SetJsonNodeTest {

	private ActorRef setJsonNode;

	@Test
	public void Test() {
		
		
	}
	static ActorSystem system;
	  @BeforeClass
	  public static void setup() {
	    system = ActorSystem.create();
	  }

	  @AfterClass
	  public static void teardown() {
	    TestKit.shutdownActorSystem(system);
	    system = null;
	  }
	  //https://doc.akka.io/docs/akka/current/testing.html
	  @Test
	  public void testIt() {
	    /*
	     * Wrap the whole test procedure within a testkit constructor
	     * if you want to receive actor replies or use Within(), etc.
	     */
		  new TestKit(system) {
		      {
		        //String id, String logId, String flowId, AppendNodeConfiguration appendNodeConfiguration
		    	  SetJsonNodeConfiguration setJsonNodeConfiguration = new SetJsonNodeConfiguration("var","actors[0].name","");
		    	  TestKit parent = new TestKit(system);
		    	  ActorRef child = parent.childActorOf(Props.create(SetJsonNode.class,"1","AN","coucou",setJsonNodeConfiguration));

		    	  //Check if the child has been created
		    	  parent.expectMsgClass(Duration.ofSeconds(1), NodeCreated.class);
		    	
		    	  
		    	  TestKit nextActor = new TestKit(system);
		    	  
		    	  
		    	  ArrayList<ArrayList<ActorRef>> listNextActor = new ArrayList<ArrayList<ActorRef>>();
		    	  
		    	  ArrayList<ActorRef> firstInput = new ArrayList<ActorRef>();
		    	  firstInput.add(nextActor.getRef());
		    	  listNextActor.add(firstInput);
		    	  
		    	  NextActors nextActorMessage = new NextActors(listNextActor);
		    	  
		    	   
		    	  child.tell(nextActorMessage, ActorRef.noSender());
		    	  
		    	  parent.expectMsgClass(Duration.ofSeconds(1), NextActorReceived.class);
		    	  
		    	  
		    	  
		    	  HashMap<String, Object> context = new HashMap<String, Object>();
		    	  context.put("message", "coucou");
		    	  context.put("loopDetectionBaseNode",new HashMap<String, Integer>());
		    	  context.put("loopDetectionMultipleOutPut",new HashMap<String, Integer>());
			    	
		    	  //put var var a json message
		    	  JsonParser parser = new JsonParser(); 
		    	  String json = "{"
		    			+"	\"actors\": [{ "
		    			+"		\"name\": \"John\" "
		    			+"	}, {"
		    			+"		\"name\": \"Bernard\" "
		    			+"	}"
		    			+"]"
		    		+"}";
			      context.put("var", parser.getJsonNode(json));
		    	  
		    	  
		    	  MessageNode message = new MessageNode(context);
		    	  
		    	  
		    	  child.tell(message, ActorRef.noSender());
		    	  
		    	  MessageNode response = nextActor.expectMsgClass(Duration.ofSeconds(1), MessageNode.class);

		    	  JsonNode var = (JsonNode) response.getContext().get("var");
		    	  String variable = var.asText();
		    	  assertEquals(variable,"John");
		    	  
		    	  setJsonNodeConfiguration = new SetJsonNodeConfiguration("var","actors[1].name","hola");
		    	  ActorRef child2 = parent.childActorOf(Props.create(SetJsonNode.class,"1","AN","coucou",setJsonNodeConfiguration));

		    	  //Check if the child has been created
		    	  parent.expectMsgClass(Duration.ofSeconds(1), NodeCreated.class);
		    	
		    	  child2.tell(nextActorMessage, ActorRef.noSender());
		    	  
		    	  parent.expectMsgClass(Duration.ofSeconds(1), NextActorReceived.class);
		    	  
		    	  json = "{"
			    			+"	\"actors\": [{ "
			    			+"		\"name\": \"John\" "
			    			+"	}, {"
			    			+"		\"name\": \"Bernard\" "
			    			+"	}"
			    			+"]"
			    		+"}";
				      context.put("var", parser.getJsonNode(json));
			    	  
			    	  
			   message = new MessageNode(context);
			    	  
			    	  
	    	  child2.tell(message, ActorRef.noSender());
	    	  
	    	  response = nextActor.expectMsgClass(Duration.ofSeconds(1), MessageNode.class);

	    	  var = (JsonNode) response.getContext().get("hola");
	    	  variable = var.asText();
	    	  assertEquals(variable,"Bernard");
		    	  
		      }
		    };
	  }
	
	
	
}
