package com.cleansweep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cleansweep.environmentobjects.Stairs;

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
