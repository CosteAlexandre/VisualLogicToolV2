import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.flow.NodeCreated;
import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.baseclassimpl.Condition;
import com.visuallogictool.application.nodes.baseclassimpl.ConditionNode;
import com.visuallogictool.application.nodes.baseclassimpl.ConditionNodeConfiguration;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
public class ConditionNodeTest {

	private ActorRef conditionNode;

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
/*
					"val1" : "weather",
					"val2" : "rain",
					"typeVal2" : "json",
					"condition" : "contains",
					"outPut" : "0"										
				},
				{
					"val1" : "weather",
					"val2" : "else",
					"typeVal2" : "else",
					"condition" : "else",
					"outPut" : "1"										
				}		
		    	   * 
		    	   */
		    	  
		    	  ArrayList<Condition> listCondition = new ArrayList<Condition>();
		    	  
		    	  listCondition.add(new Condition("hour", "12:00:00", "hour", "hour<", 0));
		    	  listCondition.add(new Condition("hour", "12:00:00", "hour", "hour>=", 1));
		    	  listCondition.add(new Condition("weather", "rain", "json", "jsoncontains", 2));
		    	  listCondition.add(new Condition("temp", "10", "json", "json<", 3));
		    	  listCondition.add(new Condition("weather", "else", "else", "else", 4));
		    	  
		    	  
		    	  ConditionNodeConfiguration conditionNodeConfiguration = new ConditionNodeConfiguration(listCondition );
		    	  TestKit parent = new TestKit(system);
		    	  ActorRef child = parent.childActorOf(Props.create(ConditionNode.class,"1","AN","coucou",conditionNodeConfiguration));

		    	  //Check if the child has been created
		    	  parent.expectMsgClass(Duration.ofSeconds(1), NodeCreated.class);
		    	
		    	  
		    	  TestKit nextActor1 = new TestKit(system);
		    	  TestKit nextActor2 = new TestKit(system);
		    	  TestKit nextActor3 = new TestKit(system);
		    	  TestKit nextActor4 = new TestKit(system);
		    	  TestKit nextActor5 = new TestKit(system);
		    	  
		    	  
		    	  ArrayList<ArrayList<ActorRef>> listNextActor = new ArrayList<ArrayList<ActorRef>>();
		    	  
		    	  ArrayList<ActorRef> firstInput = new ArrayList<ActorRef>();
		    	  firstInput.add(nextActor1.getRef());
		    	  listNextActor.add(firstInput);
		    	  
		    	  ArrayList<ActorRef> secondInput = new ArrayList<ActorRef>();
		    	  secondInput.add(nextActor2.getRef());
		    	  listNextActor.add(secondInput);
		    	  
		    	  ArrayList<ActorRef> thirdInput = new ArrayList<ActorRef>();
		    	  thirdInput.add(nextActor3.getRef());
		    	  listNextActor.add(thirdInput);
		    	  
		    	  ArrayList<ActorRef> fourthInput = new ArrayList<ActorRef>();
		    	  fourthInput.add(nextActor4.getRef());
		    	  listNextActor.add(fourthInput);
		    	  
		    	  ArrayList<ActorRef> fifthInput = new ArrayList<ActorRef>();
		    	  fifthInput.add(nextActor5.getRef());
		    	  listNextActor.add(fifthInput);
		    	  
		    	  
		    	  NextActors nextActorMessage = new NextActors(listNextActor);
		    	  
		    	   
		    	  child.tell(nextActorMessage, ActorRef.noSender());
		    	  
		    	  parent.expectMsgClass(Duration.ofSeconds(1), NextActorReceived.class);
		    	  
		    	  
		    	  
		    	  HashMap<String, Object> context = new HashMap<String, Object>();
		    	  context.put("message", "coucou");
		    	  context.put("loopDetectionBaseNode",new HashMap<String, Integer>());
		    	  context.put("loopDetectionMultipleOutPut",new HashMap<String, Integer>());
			    	
		    	  //put var var to coucou and will append hola on it
/*		    	  listCondition.add(new Condition("hour", "12:00:00", "hour", "hour>=", 0));
		    	  listCondition.add(new Condition("hour", "12:00:00", "hour", "hour<", 1));
		    	  listCondition.add(new Condition("weather", "rain", "json", "jsoncontains", 2));
		    	  listCondition.add(new Condition("temp", "10", "json", "json<", 3));
		    	  listCondition.add(new Condition("weather", "else", "else", "else", 4));
*/
		    	  context.put("hour", "09:00:00");
		    	  context.put("weather", "rain");
		    	  context.put("temp", "5");
		    	  
		    	  MessageNode message = new MessageNode(context);
		    	  
		    	  
		    	  child.tell(message, ActorRef.noSender());
		    	  //
		    	  nextActor1.expectNoMessage();
		    	  nextActor2.expectMsgClass(Duration.ofSeconds(1), MessageNode.class);
		    	  nextActor3.expectMsgClass(Duration.ofSeconds(1), MessageNode.class);
		    	  nextActor4.expectMsgClass(Duration.ofSeconds(1), MessageNode.class);
		    	  
		    	  nextActor5.expectNoMessage();

		    	  HashMap<String, Object> context2 = new HashMap<String, Object>();
		    	  context2.put("message", "coucou");
		    	  context2.put("loopDetectionBaseNode",new HashMap<String, Integer>());
		    	  context2.put("loopDetectionMultipleOutPut",new HashMap<String, Integer>());
		    	  
		    	  context2.put("hour", "16:00:00");
		    	  context2.put("weather", "not");
		    	  context2.put("temp", "11");
		    	  
		    	  message = new MessageNode(context2);
		    	  
		    	  child.tell(message, ActorRef.noSender());
		    	  //
		    	  nextActor1.expectNoMessage();
		    	  nextActor2.expectMsgClass(Duration.ofSeconds(1), MessageNode.class);
		    	  nextActor3.expectNoMessage();
		    	  nextActor4.expectNoMessage();
		    	  
		    	  nextActor5.expectMsgClass(Duration.ofSeconds(1), MessageNode.class);
		    	  
		    	  
		    	  
		      }
		    };
	  }
	
	
	
}
