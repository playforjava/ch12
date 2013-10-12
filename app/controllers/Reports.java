package controllers;


import models.Report;

import java.util.List;
import java.util.concurrent.Callable;

import play.*;
import play.libs.F.Promise;
import play.libs.F.Function;
import play.mvc.*;
import views.html.*;


public class Reports extends Controller {

	public static Result index() {
	
		Promise<Report> promiseOfKPIReport = 
		play.libs.Akka.future(               
			new Callable<Report>() {
				public Report call() {
					return intensiveKPIReport();       
				}
			}
		);
		Promise<Report> promiseOfETAReport = 
		play.libs.Akka.future(              
			new Callable<Report>() {
				public Report call() {
					return intensiveETAReport();
				}
			}
		);
		Promise<List<Report>> promises = 
			Promise.waitAll(promiseOfKPIReport, promiseOfETAReport); 
		return async(                                             
			promises.map(                                           
				new Function<List<Report>, Result>() {
					public Result apply(List<Report> reports) {
						return ok(report.render(reports));
					} 
				}
			)
		);
	}

	public static Report intensiveKPIReport() {
		Report r = new Report("KPI report");
		r.execute();
		return r;
	}

	public static Report intensiveETAReport() {
		Report r = new Report("ETA report");
		r.execute();
		return r;
	}

}