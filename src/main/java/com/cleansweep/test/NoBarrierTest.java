/**
 * 
 */
package com.cleansweep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cleansweep.barriers.NoBarrier;

/**
 * @author monty_000
 *
 */
public class NoBarrierTest {

	/**
	 * Test method for {@link com.cleansweep.barriers.NoBarrier#isBlocking()}.
	 */
	@Test
	public void testIsBlocking() {
		NoBarrier nobarrier = new NoBarrier();
		assertFalse(nobarrier.isBlocking());
	}

}
