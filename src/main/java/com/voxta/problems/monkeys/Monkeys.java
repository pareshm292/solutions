package com.voxta.problems.monkeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Monkeys 
{
	private static final Random random = new Random();
	
	private static final double MEAN = 30.5;
	private static final double STD_DEVIATION = 2.5;
	private static final double HEIGHT_CUTOFF = 28;
	
	/*
	 * Generating random numbers with given standard deviation and mean and taking a normal distribution
	 * Since the requirement is to have numbers between 24 and 36, we will be rejecting ~2% numbers
	 * and hence it is not a perfect normal distribution
	 * 
	 * Converting days to multiples of 10seconds, and every 20s we remove a monkey fitting the HEIGHT_CUTOFF
	 * criteria
	 * 
	 * Variables rejected and accepted are to keep track of rejected/accepted random numbers for our trial.
	 * 
	 * We are generating a monkey at t=0s, however first removal is at t=20s
	 */
	public static int findMonkeys(int days) {
		
		List<Double> nums = new ArrayList<>();
		//double sum = 0;
		int rejected = 0;
		int accepted = 0;
		
		for (int i = 0; i <= days*24*60*6 ; i++) {
			
			double a = MEAN + random.nextGaussian()*STD_DEVIATION;
			
			if(a>36 || a<24) rejected++;

			else {
				accepted++;
				//logger.debug(a + " was added at " + (i*10) + " seconds");
				nums.add(a);
				if(i % 2 ==0 && i > 0) {
					for(int j = 0 ; j < nums.size() ; j++) {
						if(nums.get(j) > HEIGHT_CUTOFF) {
					//		double removed = nums.remove(j);
					//		logger.debug(removed + " was removed at " + (i*10) + " seconds");
							break;
						}
					}
				}
			}
		}
		System.out.println("Percentage of rejected values : " + (rejected / (double)(rejected + accepted)*100));
		return nums.size();
	}
	
    public static void main( String[] args )
    {
    	int numOfDays = Integer.parseInt(args[0]);
		
		System.out.println("Monkeys at end of " +numOfDays + " days " + findMonkeys(numOfDays));
    }
}
