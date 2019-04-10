import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletionStage;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.flow.NodeCreated;
import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.baseclassimpl.HttpRequest;
import com.visuallogictool.application.nodes.baseclassimpl.HttpRequestConfiguration;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.testkit.javadsl.TestKit;

import static akka.http.javadsl.server.Directives.path;
import static akka.http.javadsl.server.Directives.post;
import static akka.http.javadsl.server.Directives.*;

public class HttpRequestTest {

	private ActorRef httpRequest;

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
		    	  
				  	ActorSystem sys = ActorSystem.create("routes");
				    ActorMaterializer materializer = ActorMaterializer.create(sys);

				    final Http http = Http.get(sys);

				    //In order to access all directives we need an instance where the routes are define.

				    final Flow<akka.http.javadsl.model.HttpRequest, HttpResponse, NotUsed> routeFlow = createRoute().flow(sys, materializer);
				    final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
				        ConnectHttp.toHost("localhost", 9156), materializer);
				    //http://localhost:9156/hello
				    System.out.println("Server online at http://localhost:9156/\nPress RETURN to stop...");

				    binding
				        .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
				        .thenAccept(unbound -> sys.terminate()); // and shutdown when done
		    	  
		    	  
		    	  
		    	  
		    	  
		    	  
		        //String id, String logId, String flowId, AppendNodeConfiguration appendNodeConfiguration
		    	  HttpRequestConfiguration httpRequestConfiguration = new HttpRequestConfiguration("var","http://localhost:9156/hello");
		    	  TestKit parent = new TestKit(system);
		    	  ActorRef child = parent.childActorOf(Props.create(HttpRequest.class,"1","AN","coucou",httpRequestConfiguration));

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

		    	  MessageNode message = new MessageNode(context);
		    	  
		    	  
		    	  child.tell(message, ActorRef.noSender());
		    	  
		    	  MessageNode response = nextActor.expectMsgClass(Duration.ofSeconds(3), MessageNode.class);

		    	  String var = (String) response.getContext().get("var");
		    	  System.out.println("var : " + var);
		    	  assertEquals(var,"coucou");
		    	  
		    	  
		      }
		    };
	  }
		
	  private Route createRoute() {
			
			return concat(
			        path("hello", () ->
		            post(() ->
		                complete("{ \"hola\" : \"coucou\" }"))));
		  };
	}
	
	
	

