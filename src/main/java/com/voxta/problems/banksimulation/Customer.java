package com.voxta.problems.banksimulation;

public class Customer implements Comparable<Customer> {

	public static int nextId = 1000;
	
	private int id;
	private int arrivalTime;
	private int servicedAtTime;
	private int departureTime;
	
	public int getServicedAtTime() {
		return servicedAtTime;
	}

	public void setServicedAtTime(int servicedAtTime) {
		this.servicedAtTime = servicedAtTime;
	}

	
	
	public int getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(int departureTime) {
		this.departureTime = departureTime;
	}

	public Customer(int arrivalTimeStamp) {
		this.arrivalTime = arrivalTimeStamp;
		this.id = nextId++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	@Override
	public String toString() {
		return String.format("Customer [id=%s, arrivalTime=%s]", id, arrivalTime);
	}

	@Override
	public int compareTo(Customer o) {
		return o.arrivalTime - this.arrivalTime;
	}
	
	
	
	
	
	
}
