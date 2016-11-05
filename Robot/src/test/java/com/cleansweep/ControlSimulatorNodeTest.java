/**
 * 
 */
package com.cleansweep;

import com.cleansweep.dataobjects.ControlSimulatorNode;
import com.cleansweep.enums.Direction;
import com.cleansweep.enums.FloorType;
import com.cleansweep.environmentobjects.EnvironmentObject;
import com.cleansweep.environmentobjects.Floor;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author monty_000
 *
 */
public class ControlSimulatorNodeTest {

	/**
	 * Test method for {@link com.cleansweep.dataobjects.ControlSimulatorNode#ControlSimulatorNode()}.
	 */
	@Test
	public void testControlSimulatorNode() {
		ControlSimulatorNode controlsimulatornode = new ControlSimulatorNode();
		assertNotNull(controlsimulatornode);
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.ControlSimulatorNode#isBlocking(com.cleansweep.enums.Direction)}.
	 */
	@Test
	public void testIsBlocking() {
		ControlSimulatorNode controlsimulatornode = new ControlSimulatorNode();
		controlsimulatornode.setBlocking(Direction.North);
		assertTrue(controlsimulatornode.isBlocking(Direction.North));
		controlsimulatornode.setBlocking(Direction.East);
		assertTrue(controlsimulatornode.isBlocking(Direction.East));
		controlsimulatornode.setBlocking(Direction.South);
		assertTrue(controlsimulatornode.isBlocking(Direction.South));
		controlsimulatornode.setBlocking(Direction.West);
		assertTrue(controlsimulatornode.isBlocking(Direction.West));
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.ControlSimulatorNode#setBlocking(com.cleansweep.enums.Direction)}.
	 */
	@Test
	public void testSetBlocking() {
		ControlSimulatorNode controlsimulatornode = new ControlSimulatorNode();
		controlsimulatornode.setBlocking(Direction.North);
		assertTrue(controlsimulatornode.isBlocking(Direction.North));
		controlsimulatornode.setBlocking(Direction.East);
		assertTrue(controlsimulatornode.isBlocking(Direction.East));
		controlsimulatornode.setBlocking(Direction.South);
		assertTrue(controlsimulatornode.isBlocking(Direction.South));
		controlsimulatornode.setBlocking(Direction.West);
		assertTrue(controlsimulatornode.isBlocking(Direction.West));
		
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.ControlSimulatorNode#setOpen(com.cleansweep.enums.Direction)}.
	 */
	@Test
	public void testSetOpen() {
		ControlSimulatorNode controlsimulatornode = new ControlSimulatorNode();
		controlsimulatornode.setOpen(Direction.North);
		assertFalse(controlsimulatornode.isBlocking(Direction.North));
		controlsimulatornode.setOpen(Direction.East);
		assertFalse(controlsimulatornode.isBlocking(Direction.East));
		controlsimulatornode.setOpen(Direction.South);
		assertFalse(controlsimulatornode.isBlocking(Direction.South));
		controlsimulatornode.setOpen(Direction.West);
		assertFalse(controlsimulatornode.isBlocking(Direction.West));
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.ControlSimulatorNode#isDownBlocking()}.
	 */
	@Test
	public void testIsDownBlocking() {
		ControlSimulatorNode controlsimulatornode = new ControlSimulatorNode();
		controlsimulatornode.setBlocking(Direction.South);
		assertTrue(controlsimulatornode.isDownBlocking());
		
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.ControlSimulatorNode#getEnvironmentObject()}.
	 */
	@Test
	public void testGetEnvironmentObject() {
		EnvironmentObject environmentobject = new Floor(1, FloorType.HighPile);
		ControlSimulatorNode controlsimulatornode = new ControlSimulatorNode();
		controlsimulatornode.setEnvironmentObject(environmentobject);
		assertEquals(environmentobject, controlsimulatornode.getEnvironmentObject());
		
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.ControlSimulatorNode#setEnvironmentObject(com.cleansweep.environmentobjects.EnvironmentObject)}.
	 */
	@Test
	public void testSetEnvironmentObject() {
		EnvironmentObject environmentobject = new Floor(1, FloorType.HighPile);
		ControlSimulatorNode controlsimulatornode = new ControlSimulatorNode();
		controlsimulatornode.setEnvironmentObject(environmentobject);
		assertEquals(environmentobject, controlsimulatornode.getEnvironmentObject());
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.ControlSimulatorNode#isLeftBlocking()}.
	 */
	@Test
	public void testIsLeftBlocking() {
		ControlSimulatorNode controlsimulatornode = new ControlSimulatorNode();
		controlsimulatornode.setBlocking(Direction.West);
		assertTrue(controlsimulatornode.isLeftBlocking());
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.ControlSimulatorNode#isRightBlocking()}.
	 */
	@Test
	public void testIsRightBlocking() {
		ControlSimulatorNode controlsimulatornode = new ControlSimulatorNode();
		controlsimulatornode.setBlocking(Direction.East);
		assertTrue(controlsimulatornode.isRightBlocking());
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.ControlSimulatorNode#isTopBlocking()}.
	 */
	@Test
	public void testIsTopBlocking() {
		ControlSimulatorNode controlsimulatornode = new ControlSimulatorNode();
		controlsimulatornode.setBlocking(Direction.North);
		assertTrue(controlsimulatornode.isTopBlocking());
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.ControlSimulatorNode#setTopBlocking(boolean)}.
	 */
	@Test
	public void testSetTopBlocking() {
		ControlSimulatorNode controlsimulatornode = new ControlSimulatorNode();
		controlsimulatornode.setTopBlocking(true);
		assertTrue(controlsimulatornode.isTopBlocking());
		
		controlsimulatornode.setTopBlocking(false);
		assertFalse(controlsimulatornode.isTopBlocking());
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.ControlSimulatorNode#isVisited()}.
	 */
	@Test
	public void testIsVisited() {
		ControlSimulatorNode controlsimulatornode = new ControlSimulatorNode();
		controlsimulatornode.setVisited(true);
		assertTrue(controlsimulatornode.isVisited());
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.ControlSimulatorNode#setVisited(boolean)}.
	 */
	@Test
	public void testSetVisited() {
		ControlSimulatorNode controlsimulatornode = new ControlSimulatorNode();
		controlsimulatornode.setVisited(true);
		assertTrue(controlsimulatornode.isVisited());
		
		controlsimulatornode.setVisited(false);
		assertFalse(controlsimulatornode.isVisited());
	}

}
