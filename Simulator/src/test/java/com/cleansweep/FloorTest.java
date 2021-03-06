/**
 * 
 */
package com.cleansweep;

import com.cleansweep.enums.FloorType;
import com.cleansweep.environmentobjects.Floor;
import com.cleansweep.exceptions.CleanException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author monty_000
 *
 */
public class FloorTest {

	/**
	 * Test method for {@link com.cleansweep.environmentobjects.Floor#Floor(int, com.cleansweep.enums.FloorType)}.
	 */
	@Test
	public void testFloor() {
	Floor floor = new Floor(1, FloorType.Bare);
	assertNotNull(floor);
	
	try
	{
		Floor floor1 = new Floor(-1, FloorType.Bare);
	}
	catch(IllegalArgumentException i)
	{
		assertTrue(i.getMessage().equals("Dirt units cannot be negative"));
	}
	}

	/**
	 * Test method for {@link com.cleansweep.environmentobjects.Floor#getDirtUnits()}.
	 */
	@Test
	public void testGetDirtUnits() {
		Floor floor = new Floor(1, FloorType.Bare);
		assertTrue(floor.getDirtUnits() == 1);

		Floor floor2 = new Floor(10, FloorType.Bare);
		assertTrue(floor2.getDirtUnits() == 10);
		
		Floor floor3 = new Floor(100, FloorType.Bare);
		assertTrue(floor3.getDirtUnits() == 100);
	}

	/**
	 * Test method for {@link com.cleansweep.environmentobjects.Floor#getFloorType()}.
	 */
	@Test
	public void testGetFloorType() {
		Floor floor = new Floor(1, FloorType.Bare);
		assertTrue(floor.getFloorType() == FloorType.Bare);
		
		Floor floor2 = new Floor(1, FloorType.LowPile);
		assertTrue(floor2.getFloorType() == FloorType.LowPile);
		
		Floor floor3 = new Floor(1, FloorType.HighPile);
		assertTrue(floor3.getFloorType() == FloorType.HighPile);
		
		Floor floor4 = new Floor(1, FloorType.NotFloor);
		assertTrue(floor4.getFloorType() == FloorType.NotFloor);
	}

	/**
	 * Test method for {@link com.cleansweep.environmentobjects.Floor#isBlocking()}.
	 */
	@Test
	public void testIsBlocking() {
		Floor floor = new Floor(1, FloorType.HighPile);
		assertFalse(floor.isBlocking());
	}

	/**
	 * Test method for {@link com.cleansweep.environmentobjects.Floor#clean()}.
	 */
	@Test
	public void testClean() {

		Floor floor;

		try
		{
			floor = new Floor(0, FloorType.HighPile);
			floor.clean();
		}
		catch(CleanException c)
		{
			assertTrue(c.getMessage().equals("Cannot clean a floor that is already clean"));
		}

	}

}
