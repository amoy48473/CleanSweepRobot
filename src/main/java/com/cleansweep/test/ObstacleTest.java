/**
 * 
 */
package com.cleansweep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cleansweep.environmentobjects.Obstacle;

/**
 * @author monty_000
 *
 */
public class ObstacleTest {

	/**
	 * Test method for {@link com.cleansweep.environmentobjects.Obstacle#getDirtUnits()}.
	 */
	@Test
	public void testGetDirtUnits() {
		Obstacle obstacle = new Obstacle();
		assertTrue(obstacle.getDirtUnits() == 0);
	}

	/**
	 * Test method for {@link com.cleansweep.environmentobjects.Obstacle#isBlocking()}.
	 */
	@Test
	public void testIsBlocking() {
		Obstacle obstacle = new Obstacle();
		assertTrue(obstacle.isBlocking());
	}

}
