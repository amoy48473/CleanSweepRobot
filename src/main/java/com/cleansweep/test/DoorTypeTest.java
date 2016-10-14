/**
 * 
 */
package com.cleansweep.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cleansweep.barriers.Door;

/**
 * @author monty_000
 *
 */
public class DoorTypeTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//boolean isOpen = true;
		//Door door = new Door(true);
	}

	/**
	 * Test method for {@link com.cleansweep.barriers.Door#Door(boolean)}.
	 */
	@Test
	public void testDoor() {
		boolean isOpen = true;
		Door door = new Door(isOpen);
		boolean isBlocked = door.isBlocking();
		assertFalse(isBlocked);
		
		boolean isOpen2 = false;
		Door door2 = new Door(isOpen2);
		boolean isBlocked2 = door2.isBlocking();
		assertTrue(isBlocked2);
	}

	/**
	 * Test method for {@link com.cleansweep.barriers.Door#Open()}.
	 */
	@Test
	public void testOpen() {
		boolean isOpen = false;
		Door door = new Door(isOpen);
		boolean isBlocked = door.isBlocking();
		assertTrue(isBlocked);
		door.Open();
		assertFalse(door.isBlocking());
	}

	/**
	 * Test method for {@link com.cleansweep.barriers.Door#Close()}.
	 */
	@Test
	public void testClose() {
		boolean isOpen = true;
		Door door = new Door(isOpen);
		boolean isBlocked = door.isBlocking();
		assertFalse(isBlocked);
		door.Close();
		assertTrue(door.isBlocking());
	}

	/**
	 * Test method for {@link com.cleansweep.barriers.Door#isBlocking()}.
	 */
	@Test
	public void testIsBlocking() {
		boolean isOpen = true;
		Door door = new Door(isOpen);
		assertFalse(door.isBlocking());
		door.Close();
		assertTrue(door.isBlocking());
	}

}
