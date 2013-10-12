package models;

import java.util.*;



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


import java.util.Random;
import java.math.BigInteger;

import static java.util.concurrent.TimeUnit.*;

public class Order {

	public Order() {}

	public String toString() {
		return "Order " + nextId() + " date " + new Date() + "\r\n";
	}

	private static String nextId() {
		Random random = new Random();
		return new BigInteger(30, random).toString(9);
	}

}