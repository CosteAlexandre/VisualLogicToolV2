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
import com.visuallogictool.application.nodes.baseclassimpl.EndWithNode;
import com.visuallogictool.application.nodes.baseclassimpl.EndWithNodeConfiguration;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
public class EndWithNodeTest {

	private ActorRef containsNode;

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
		    	  EndWithNodeConfiguration endWithNodeConfiguration = new EndWithNodeConfiguration("var","la");
		    	  TestKit parent = new TestKit(system);
		    	  ActorRef child = parent.childActorOf(Props.create(EndWithNode.class,"1","AN","coucou",endWithNodeConfiguration));

		    	  //Check if the child has been created
		    	  parent.expectMsgClass(Duration.ofSeconds(1), NodeCreated.class);
		    	
		    	  
		    	  TestKit nextActor1 = new TestKit(system);
		    	  TestKit nextActor2 = new TestKit(system);
		    	  
		    	  
		    	  ArrayList<ArrayList<ActorRef>> listNextActor = new ArrayList<ArrayList<ActorRef>>();
		    	  
		    	  ArrayList<ActorRef> firstInput = new ArrayList<ActorRef>();
		    	  firstInput.add(nextActor1.getRef());
		    	  listNextActor.add(firstInput);
		    	  
		    	  ArrayList<ActorRef> secondInput = new ArrayList<ActorRef>();
		    	  secondInput.add(nextActor2.getRef());
		    	  listNextActor.add(secondInput);
		    	  
		    	  NextActors nextActorMessage = new NextActors(listNextActor);
		    	  
		    	   
		    	  child.tell(nextActorMessage, ActorRef.noSender());
		    	  
		    	  parent.expectMsgClass(Duration.ofSeconds(1), NextActorReceived.class);
		    	  
		    	  
		    	  
		    	  HashMap<String, Object> context = new HashMap<String, Object>();
		    	  context.put("message", "coucou");
		    	  context.put("loopDetectionBaseNode",new HashMap<String, Integer>());
		    	  context.put("loopDetectionMultipleOutPut",new HashMap<String, Integer>());
			    	
		    	  //put var var to coucou and will append hola on it
		    	  context.put("var", "coucou");
		    	  MessageNode message = new MessageNode(context);
		    	  
		    	  
		    	  child.tell(message, ActorRef.noSender());
		    	  //coucou does not contains hola
		    	  nextActor2.expectMsgClass(Duration.ofSeconds(1), MessageNode.class);
		    	  nextActor1.expectNoMessage();
		    	  context.put("var", "hola");
		    	  message = new MessageNode(context);
		    	  
		    	  
		    	  child.tell(message, ActorRef.noSender());
		    	  
		    	  nextActor1.expectMsgClass(Duration.ofSeconds(1), MessageNode.class);
		    	  nextActor2.expectNoMessage();
		      }
		    };
	  }
	
	
	
}
