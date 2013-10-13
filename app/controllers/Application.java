package controllers;


import play.*;
import play.mvc.*;
import play.mvc.Results.*;
import play.mvc.Results.Chunks;

import views.html.*;
import play.libs.*;
import models.*;
import java.util.*;


import play.libs.F.*;


public class Application extends Controller {


	public static Result index() {
		return ok(index.render("live stream"));
	}

	public static WebSocket<String> liveUpdate() {
		return new WebSocket<String>() {  

    		// Called when the WebSocket Handshake is done.
			public void onReady(final WebSocket.In<String> in, 
				final WebSocket.Out<String> out) {  

      			// For each event received on the socket,
				in.onMessage(new Callback<String>() {  
					public void invoke(String event) {
						ExpeditedOrders.notifyOthers(out, event 
       + " is being processed");
					} 
				});

			    // When the socket is closed.
				in.onClose(new Callback0() { 
					public void invoke() {
						ExpeditedOrders.unregister(out);
					}
				});

				ExpeditedOrders.register(out); 
      			
			}
		};
	}
}