package controllers;


import play.*;
import play.mvc.*;
import play.mvc.Results.*;
import play.mvc.Results.Chunks;

import views.html.*;
import play.libs.*;
import models.*;
import java.util.*;



public class Application extends Controller {


	public static Result index() {
		return ok(index.render("live stream"));
	}

	public static Result liveUpdate() {	
  		// Prepare a chunked text stream
		Comet comet = new Comet("parent.cometMessage") { 
			public void onConnected() {  
				ExpeditedOrders.registerChunkOut(this);
			}
		};
		return ok(comet);
		
	}
}