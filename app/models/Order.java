package models;

import java.util.*;


import java.math.BigInteger;

public class Order {

	public Order() {}

	public String toString() {
		return "Order " + nextId() + " date " + new Date();
	}

	private static String nextId() {
		Random random = new Random();
		return new BigInteger(30, random).toString(9);
	}

}