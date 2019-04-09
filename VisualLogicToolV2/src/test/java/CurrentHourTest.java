import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.flow.NodeCreated;
import com.visuallogictool.application.messages.message.HttpRequestReceived;
import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.messages.message.RegisterRestRouter;
import com.visuallogictool.application.nodes.baseclassimpl.ApiRest;
import com.visuallogictool.application.nodes.baseclassimpl.ApiRestConfiguration;
import com.visuallogictool.application.nodes.baseclassimpl.AppendNode;
import com.visuallogictool.application.nodes.baseclassimpl.AppendNodeConfiguration;
import com.visuallogictool.application.nodes.baseclassimpl.ContainsNode;
import com.visuallogictool.application.nodes.baseclassimpl.ContainsNodeConfiguration;
import com.visuallogictool.application.nodes.baseclassimpl.CurrentHour;
import com.visuallogictool.application.nodes.baseclassimpl.CurrentHourConfiguration;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.http.javadsl.model.HttpRequest;
import akka.testkit.javadsl.TestKit;
import static org.junit.Assert.*;
public class CurrentHourTest {

	private ActorRef currentHour;

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
		    	  CurrentHourConfiguration currentHourConfiguration = new CurrentHourConfiguration("hour");
		    	  TestKit parent = new TestKit(system);
		    	  ActorRef child = parent.childActorOf(Props.create(CurrentHour.class,"1","AN","coucou",currentHourConfiguration));

		    	  //Check if the child has been created
		    	  parent.expectMsgClass(Duration.ofSeconds(1), NodeCreated.class);
		    	
		    	  
		    	  TestKit nextActor1 = new TestKit(system);
		    	  
		    	  
		    	  ArrayList<ArrayList<ActorRef>> listNextActor = new ArrayList<ArrayList<ActorRef>>();
		    	  
		    	  ArrayList<ActorRef> firstInput = new ArrayList<ActorRef>();
		    	  firstInput.add(nextActor1.getRef());
		    	  listNextActor.add(firstInput);
		    	  

		    	  
		    	  NextActors nextActorMessage = new NextActors(listNextActor);
		    	  
		    	   
		    	  child.tell(nextActorMessage, ActorRef.noSender());
		    	  
		    	  parent.expectMsgClass(Duration.ofSeconds(1), NextActorReceived.class);
		    	  
		    	  
		    	  
		    	  HashMap<String, Object> context = new HashMap<String, Object>();
		    	  context.put("message", "coucou");
		    	  context.put("loopDetectionBaseNode",new HashMap<String, Integer>());
		    	  context.put("loopDetectionMultipleOutPut",new HashMap<String, Integer>());
			    	

		    	  MessageNode message = new MessageNode(context);
		    	  
		    	  
		    	  child.tell(message, ActorRef.noSender());
		    	  //currenthour is in var
		    	  MessageNode response = nextActor1.expectMsgClass(Duration.ofSeconds(1), MessageNode.class);

		    	  String var = (String) response.getContext().get("hour");
		    	  
		    	  String regex = "[0-9]{2}:[0-9]{2}:[0-9]{2}";
		    	  
		    	  assertTrue(var.matches(regex));
		    	  
		      }
		    };
	  }
	
	
	
}
