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
import com.visuallogictool.application.nodes.baseclassimpl.ApiRestOutput;
import com.visuallogictool.application.nodes.baseclassimpl.ApiRestOutputConfiguration;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.RequestEntity;
import akka.http.javadsl.model.HttpEntity.Strict;
import akka.testkit.javadsl.TestKit;
public class ApiRestOutputTest {

	private ActorRef apiRestOutput;

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
		    	  ApiRestOutputConfiguration apiRestOutputConfiguration = new ApiRestOutputConfiguration("coucou");
		    	  TestKit parent = new TestKit(system);
		    	  ActorRef child = parent.childActorOf(Props.create(ApiRestOutput.class,"1","AN","coucou",apiRestOutputConfiguration));

		    	  //Check if the child has been created
		    	  parent.expectMsgClass(Duration.ofSeconds(1), NodeCreated.class);
		    	
		    	  
		    	  TestKit nextActor = new TestKit(system);
		    	  
		    	  
		    	  ArrayList<ArrayList<ActorRef>> listNextActor = new ArrayList<ArrayList<ActorRef>>();
		    	  
		    	  
		    	  
		    	  NextActors nextActorMessage = new NextActors(listNextActor);
		    	  
		    	   
		    	  child.tell(nextActorMessage, ActorRef.noSender());
		    	  
		    	  parent.expectMsgClass(Duration.ofSeconds(1), NextActorReceived.class);
		    	  
		    	  
		    	  
		    	  HashMap<String, Object> context = new HashMap<String, Object>();
		    	  context.put("message", "coucou");
		    	  context.put("loopDetectionBaseNode",new HashMap<String, Integer>());
		    	  context.put("loopDetectionMultipleOutPut",new HashMap<String, Integer>());
			    	
		    	  //put var var to coucou and will append hola on it
		    	  TestKit httpRespActor = new TestKit(system);
		    	  context.put("InputSender", httpRespActor.getRef());
		    	  MessageNode message = new MessageNode(context);
		    	  
		    	  
		    	  child.tell(message, ActorRef.noSender());
		    	  
		    	  HttpResponse response = httpRespActor.expectMsgClass(Duration.ofSeconds(1), HttpResponse.class);
		    	  Strict entity = (Strict) response.entity();
		    	  String body = entity.getData().decodeString("UTF-8");
		    	  assertEquals(body,"\"coucou\"");
		    	 
		    	  
		    	  
		      }
		    };
	  }
	
	
	
}
