/**
 * 
 */
package com.cleansweep;

import com.cleansweep.barriers.Door;
import com.cleansweep.barriers.NoBarrier;
import com.cleansweep.barriers.Wall;
import com.cleansweep.dataobjects.Node;
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
public class NodeTest {

	/**
	 * Test method for {@link Node#Node(EnvironmentObject, com.cleansweep.barriers.Barrier, com.cleansweep.barriers.Barrier, com.cleansweep.barriers.Barrier, com.cleansweep.barriers.Barrier)}.
	 */
	@Test
	public void testNode() {
		EnvironmentObject eo = new Floor(1, FloorType.LowPile);
		Wall wall = new Wall();
		NoBarrier nobarrier = new NoBarrier();
		Door door = new Door(true);
		Node node = new Node(eo, wall, nobarrier, door, wall);
		assertNotNull(node);
	}

	/**
	 * Test method for {@link Node#getEnvironmentObject()}.
	 */
	@Test
	public void testGetEnvironmentObject() {
		EnvironmentObject eo = new Floor(1, FloorType.LowPile);
		Wall wall = new Wall();
		NoBarrier nobarrier = new NoBarrier();
		Door door = new Door(true);
		Node node = new Node(eo, wall, nobarrier, door, wall);
		assertEquals(eo, node.getEnvironmentObject());
	}

	/**
	 * Test method for {@link Node#getBarrier(Direction)}.
	 */
	@Test
	public void testGetBarrier() {
		EnvironmentObject eo = new Floor(1, FloorType.LowPile);
		Wall wall = new Wall();
		NoBarrier nobarrier = new NoBarrier();
		Door door = new Door(true);
		Door door2 = new Door(false);
		Node node = new Node(eo, wall, nobarrier, door, door2);
		assertEquals(wall, node.getBarrier(Direction.North));
		assertEquals(nobarrier, node.getBarrier(Direction.East));
		assertEquals(door, node.getBarrier(Direction.South));
		assertEquals(door2, node.getBarrier(Direction.West));
	}

}
