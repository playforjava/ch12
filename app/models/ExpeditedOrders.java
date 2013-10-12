package models;


import play.*;
import play.mvc.*;
import play.mvc.Results.*;
import play.mvc.Results.Chunks;
import java.util.*;

import play.mvc.*;
import play.libs.*;
import play.libs.F.*;


import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import akka.actor.*;
import akka.dispatch.*;
import static akka.pattern.Patterns.ask;

import scala.concurrent.duration.*;
import akka.actor.*;
import akka.dispatch.*;


import static java.util.concurrent.TimeUnit.*;

public class ExpeditedOrders extends UntypedActor {

	static List<Comet> comets = 
                     new ArrayList<Comet>(); 

	
	static ActorRef defaultActor = Akka.system().actorOf(new Props(ExpeditedOrders.class));

	static {
		Akka.system().scheduler().schedule(
			Duration.create(4, SECONDS),
			Duration.create(5, SECONDS),
			defaultActor,
			new Order(),
			Akka.system().dispatcher(),
			null
		);
	}

	public static void registerChunkOut(Comet out) {
		ExpeditedOrders.comets.add(out);
	}

	public void onReceive(Object message) throws Exception {
		Order order = (Order)message;
		for(Comet comet: comets) {
     		 comet.sendMessage(order.toString()); 
    	}

	}
}