package com.cleansweep;

import com.cleansweep.environmentobjects.Stairs;
import org.junit.Test;

import static org.junit.Assert.*;

public class StairsTest {

	@Test
	public void testStairs() {
		Stairs stairs = new Stairs();
		assertNotNull(stairs);
	}

	@Test
	public void testGetDirtUnits() {
		Stairs stairs = new Stairs();
		assertTrue(stairs.getDirtUnits() == 0);
	}

	@Test
	public void testIsBlocking() {
		Stairs stairs = new Stairs();
		assertTrue(stairs.isBlocking());
	}
	
}
