/**
 * 
 */
package com.cleansweep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cleansweep.barriers.Wall;

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
