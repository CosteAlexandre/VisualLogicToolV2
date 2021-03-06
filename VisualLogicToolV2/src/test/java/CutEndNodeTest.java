import static org.junit.Assert.assertEquals;

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
import com.visuallogictool.application.nodes.baseclassimpl.CutEndNode;
import com.visuallogictool.application.nodes.baseclassimpl.CutEndNodeConfiguration;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
public class CutEndNodeTest {

	private ActorRef cutEndNode;

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
		    	  CutEndNodeConfiguration cutEndNodeConfiguration = new CutEndNodeConfiguration("var",2,"");
		    	  TestKit parent = new TestKit(system);
		    	  ActorRef child = parent.childActorOf(Props.create(CutEndNode.class,"1","AN","coucou",cutEndNodeConfiguration));

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
			    	
		    	  //put var var to coucou and will append hola on it
		    	  context.put("var", "coucou");
		    	  MessageNode message = new MessageNode(context);
		    	  
		    	  
		    	  child.tell(message, ActorRef.noSender());
		    	  
		    	  MessageNode response = nextActor.expectMsgClass(Duration.ofSeconds(1), MessageNode.class);

		    	  String var = (String) response.getContext().get("var");
		    	  
		    	  assertEquals(var,"couc");
		    	  
		       
		    	  cutEndNodeConfiguration = new CutEndNodeConfiguration("var",2,"bla");
		    	  ActorRef child2 = parent.childActorOf(Props.create(CutEndNode.class,"1","AN","coucou",cutEndNodeConfiguration));

		    	  
		    	  parent.expectMsgClass(Duration.ofSeconds(1), NodeCreated.class);
		    	  
		    	  child2.tell(nextActorMessage, ActorRef.noSender());
		    	  
		    	  parent.expectMsgClass(Duration.ofSeconds(1), NextActorReceived.class);
		    	  
		    	  context.put("var", "coucou");
		    	  message = new MessageNode(context);
		    	  
		    	  
		    	  child2.tell(message, ActorRef.noSender());
		    	  
		    	  response = nextActor.expectMsgClass(Duration.ofSeconds(1), MessageNode.class);

		    	  var = (String) response.getContext().get("bla");
		    	  
		    	  assertEquals(var,"couc");
		    	  
		      }
		    };
	  }
	
	
	
}
