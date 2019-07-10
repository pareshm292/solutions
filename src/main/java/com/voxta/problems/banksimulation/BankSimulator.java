package com.voxta.problems.banksimulation;

import java.util.Random;

public class BankSimulator {

	public static void main(String[] args) {


		Bank bank = new Bank();
		Random random = new Random();
		int totalCustomers = 0;
		for(int i = 0 ; i < 480 ; i++) {
			
			// generate random number of customers at 30 min intervals
			if(i % 30 == 0 && isNotBreakTime(i)) {
				int num = random.nextInt(17) + 3;
				totalCustomers += num;
				System.out.println(num + " customers enter at timestamp " + i);
				bank.serviceCustomers(num, i);
			}
			
			// since our minimum time period of "updation" of things is 5, we refresh our queues 
			// at 5 min intervals
			if(i % 5 == 0) {
				bank.updateQueues(i);
			}
			
		}
		
		System.out.println("Total customers " + totalCustomers);
		bank.getCounters().forEach(counter ->{
			System.out.println("Counter " + counter.getId() + " serviced " + counter.getCustomersServed() + " people."
					+ "It had a max queue length of " + counter.getMaxQueueSize());
		});

	}
	
	// for sake of simplicity since we are counting in minutes 1.30 - 2.30 is 270 mins to 330 mins
	private static boolean isNotBreakTime(int i) {
		return i < 270 || i >= 330;
	}

	

}
