/**
 * 
 */
package com.cleansweep;

import com.cleansweep.barriers.NoBarrier;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

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
