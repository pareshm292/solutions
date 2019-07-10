package com.voxta.problems;

import com.voxta.problems.monkeys.Monkeys;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class MonkeysTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MonkeysTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( MonkeysTest.class );
    }

    
    public void testMonkeyCounts()
    {
        assertTrue("Test failed " , Monkeys.findMonkeys(1) > 8400);
        assertTrue("Test failed " , Monkeys.findMonkeys(2) > 16900);
    }
}
