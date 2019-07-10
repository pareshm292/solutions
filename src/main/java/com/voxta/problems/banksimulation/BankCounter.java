package com.voxta.problems.banksimulation;

import java.util.List;

public class BankCounter {

	private int id;
	private List<Customer> queue;
	private boolean isOpen;
	private int customersServed = 0;
	private int maxQueueSize = 0;
	
	public void updateMaxQueueSize() {
		if(queue.size() > maxQueueSize) {
			maxQueueSize = queue.size();
		}
	}
	
	public int getMaxQueueSize() {
		return maxQueueSize;
	}
	
	public void incrementCustomersServed() {
		customersServed++;
	}
	
	public int getCustomersServed() {
		return customersServed;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Customer> getQueue() {
		return queue;
	}
	public void setQueue(List<Customer> queue) {
		this.queue = queue;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	public BankCounter(int id, List<Customer> queue, boolean isOpen) {
		super();
		this.id = id;
		this.queue = queue;
		this.isOpen = isOpen;
	}
	
	public boolean isQueueFull() {
		
		return queue.size() >= 5;
	
	}
}
