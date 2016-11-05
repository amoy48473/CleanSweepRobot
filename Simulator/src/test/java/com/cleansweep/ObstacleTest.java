/**
 * 
 */
package com.cleansweep;

import com.cleansweep.environmentobjects.Obstacle;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * @author monty_000
 *
 */
public class ObstacleTest {

	/**
	 * Test method for {@link Obstacle#getDirtUnits()}.
	 */
	@Test
	public void testGetDirtUnits() {
		Obstacle obstacle = new Obstacle();
		assertTrue(obstacle.getDirtUnits() == 0);
	}

	/**
	 * Test method for {@link Obstacle#isBlocking()}.
	 */
	@Test
	public void testIsBlocking() {
		Obstacle obstacle = new Obstacle();
		assertTrue(obstacle.isBlocking());
	}

}
