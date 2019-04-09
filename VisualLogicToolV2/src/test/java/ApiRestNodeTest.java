import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.flow.NodeCreated;
import com.visuallogictool.application.messages.message.HttpRequestReceived;
import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.baseclassimpl.ApiRest;
import com.visuallogictool.application.nodes.baseclassimpl.ApiRestConfiguration;
import com.visuallogictool.application.server.RestRouter;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.model.HttpRequest;
import akka.testkit.javadsl.TestKit;
public class ApiRestNodeTest {

	private ActorRef apiRestNode;

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
		    	  ApiRestConfiguration apiRestConfiguration = new ApiRestConfiguration("coucou");
		    	  TestKit parent = new TestKit(system);
		    	  ActorRef child = parent.childActorOf(Props.create(ApiRest.class,"1","AN","coucou",apiRestConfiguration));
//		    	  TestKit router = system.actorOf(Props.create(RestRouter.class,system), "restRouter");
		    	  
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
		    	  
		    	  
		    	  
		    	  
		    	  
		    	  HttpRequest httpRequest = HttpRequest.create("api");
		    	  child.tell(new HttpRequestReceived(httpRequest), ActorRef.noSender());
		    	  
		    	  MessageNode response = nextActor.expectMsgClass(Duration.ofSeconds(1), MessageNode.class);

		    	  

		    	  
		    	  assertTrue(response.getContext().containsKey("message"));
		    	  assertTrue(response.getContext().containsKey("loopDetectionBaseNode"));
		    	  assertTrue(response.getContext().containsKey("loopDetectionMultipleOutPut"));
		    	  
		    	  
		    	  
		    	  
		      }
		    };
	  }
	
	
	
}
