/**
 * 
 */
package com.cleansweep;

import com.cleansweep.barriers.Wall;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * @author monty_000
 *
 */
public class WallTest {

	/**
	 * Test method for {@link com.cleansweep.barriers.Wall#Wall()}.
	 */
	@Test
	public void testWall() {
		Wall wall = new Wall();
	}

	/**
	 * Test method for {@link com.cleansweep.barriers.Wall#isBlocking()}.
	 */
	@Test
	public void testIsBlocking() {
		Wall wall = new Wall();
		assertTrue(wall.isBlocking());
	}

}
