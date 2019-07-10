package com.voxta.problems.banksimulation;

import java.util.ArrayList;
import java.util.List;

public class Bank {

	private List<BankCounter> counters;
	//private int countersOpen = 3;


	public Bank() {
		initCounters();
	}



	public List<BankCounter> getCounters() {
		return counters;
	}



	public void setCounters(List<BankCounter> counters) {
		this.counters = counters;
	}



	private void initCounters() {

		counters = new ArrayList<>();

		for(int i = 1 ; i <= 10 ; i++) {
			counters.add(new BankCounter(i, new ArrayList<Customer>(), false));
		}
		/*
		 * for(int i = 4 ; i <= 10 ; i++) { counters.add(new BankCounter(i, new
		 * ArrayList<Customer>(), false)); }
		 */

		System.out.println("Bank Counters initialized");
	}

	public void serviceCustomers(int numOfCustomers, int timeStamp) {

		for(int i = 0 ; i < numOfCustomers ; i++) {
			Customer customer = new Customer(timeStamp);
			serviceCustomer(customer,timeStamp);
		}

	}

	public void updateCustomerTimes(Customer c , int timeStamp) {
		c.setServicedAtTime(timeStamp);
		c.setDepartureTime(timeStamp+5);
	}

	public void updateQueues(int timestamp) {

		for(BankCounter counter : counters) {

			// remove customers who have been serviced..
			if(!counter.getQueue().isEmpty() && counter.getQueue().get(0).getDepartureTime() == timestamp) {
				Customer c = counter.getQueue().remove(0);
				counter.incrementCustomersServed();
				System.out.println("Customer with id " + c.getId() + " leaves at " + timestamp);
			}

			// take first customer from queue and start servicing them.
			if(counter.isOpen() && !counter.getQueue().isEmpty()) {
				Customer cust = counter.getQueue().get(0);
				cust.setServicedAtTime(timestamp);
				cust.setDepartureTime(timestamp + 5);
				System.out.println("Customer with id " + cust.getId() + " started being serviced at " + cust.getServicedAtTime() + " from counter " + counter.getId());
			}

			// close counter if no customers to service
			if(counter.getQueue().isEmpty() && counter.isOpen()) {
				System.out.println("Closing counter : " + counter.getId() + " at timestamp " + timestamp) ;
				counter.setOpen(false);
			}
		}

	}
	/*
	 * Method to add customer to relevant queue.
	 */
	private void serviceCustomer(Customer customer,int timeStamp) {

		boolean addedToQueue = false;
		for(BankCounter counter : counters) {
			if(counter.isOpen() && !counter.isQueueFull()) {
				System.out.println("customer with id " + customer.getId() + " in queue at " + counter.getId());
				counter.getQueue().add(customer);
				counter.updateMaxQueueSize();
				if(counter.getQueue().size() == 1) {
					updateCustomerTimes(customer, timeStamp);
				}
				addedToQueue = true;
				break;
			}
		}

		if(!addedToQueue) {
			for(BankCounter counter : counters) {
				if(!counter.isOpen()) {
					counter.setOpen(true);
					counter.getQueue().add(customer);
					addedToQueue = true;
					if(counter.getQueue().size() == 1) {
						updateCustomerTimes(customer, timeStamp);
					}
					counter.updateMaxQueueSize();
					System.out.println("Opening counter with id " + counter.getId() + " and adding customer with id " + customer.getId() + " to it");
					break;
				}
			}
		}

	}



}
