import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.visuallogictool.application.nodes.baseclassimpl.ApiRest;
import com.visuallogictool.application.nodes.baseclassimpl.ApiRestConfiguration;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class ApiRestTest {

	private ActorRef apiRest;
	private ActorSystem system;
	
	@BeforeEach
    public void init() {
		system = ActorSystem.create("system");
		String id = "1";
		ApiRestConfiguration apiRestConfiguration = new ApiRestConfiguration("api");
		//apiRest = system.actorOf(ApiRest.props(id, apiRestConfiguration ));
    }
	@Test
	public void Test() {
		
		
	}
	
}
