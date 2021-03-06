/**
 * 
 */
package com.cleansweep;

import com.cleansweep.dataobjects.Point;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author monty_000
 *
 */
public class PointTest {

	/**
	 * Test method for {@link Point#Point(int, int)}.
	 */
	@Test
	public void testPoint() {
		Point point = new Point(1,5);
		assertNotNull(point);
	}

	/**
	 * Test method for {@link Point#getX()}.
	 */
	@Test
	public void testGetX() {
		Point point = new Point(1,5);
		assertTrue(point.getX() == 1);
	}

	/**
	 * Test method for {@link Point#setX(int)}.
	 */
	@Test
	public void testSetX() {
		Point point = new Point(1,5);
		point.setX(3);
		assertTrue(point.getX() == 3);
	}

	/**
	 * Test method for {@link Point#getY()}.
	 */
	@Test
	public void testGetY() {
		Point point = new Point(1,5);
		assertTrue(point.getY() == 5);
	}

	/**
	 * Test method for {@link Point#setY(int)}.
	 */
	@Test
	public void testSetY() {
		Point point = new Point(1,5);
		point.setY(10);
		assertTrue(point.getY() == 10);
	}

	/**
	 * Test method for {@link Point#toString()}.
	 */
	@Test
	public void testToString() {
		Point point = new Point(1,5);
		String p = 1 + "" + 5;
		assertTrue(point.toString().equals(p));
	}

}
