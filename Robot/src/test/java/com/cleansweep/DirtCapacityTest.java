/**
 * 
 */
package com.cleansweep;

import com.cleansweep.dataobjects.DirtCapacity;
import com.cleansweep.enums.DirtCapacityStatus;
import com.cleansweep.ui.FrameListener;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * @author monty_000
 *
 */
public class DirtCapacityTest {

	private FrameListener frameListener;

	@Before
	public void SetUp(){
		this.frameListener = mock(FrameListener.class);
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.DirtCapacity#DirtCapacity()}.
	 */
	@Test
	public void testDirtCapacity() {
		DirtCapacity dirt = new DirtCapacity();
		assertNotNull(dirt);
		assertTrue(dirt.getDirtCapacity() == 0);
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.Empty);
		
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.DirtCapacity#updateDirtCapacity()}.
	 */
	@Test
	public void testUpdateDirtCapacity() {
		DirtCapacity dirt = new DirtCapacity();
		dirt.updateDirtCapacity();
		assertTrue(dirt.getDirtCapacity() == 1);
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.NotFull);
		dirt.updateDirtCapacity();
		assertTrue(dirt.getDirtCapacity() == 2);
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.NotFull);
		dirt.setDirtCapacity(49);
		dirt.updateDirtCapacity();
		assertTrue(dirt.getDirtCapacity() == 50);
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.Full);
		
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.DirtCapacity#getDirtCapacity()}.
	 */
	@Test
	public void testGetDirtCapacity() {
		DirtCapacity dirt = new DirtCapacity();
		dirt.updateDirtCapacity();
		assertTrue(dirt.getDirtCapacity() == 1);
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.DirtCapacity#isMaxDirtCapacity()}.
	 */
	@Test
	public void testIsMaxDirtCapacity() {
		DirtCapacity dirt = new DirtCapacity();
		assertFalse(dirt.isMaxDirtCapacity());
		dirt.setDirtCapacity(20);
		assertFalse(dirt.isMaxDirtCapacity());
		dirt.setDirtCapacity(50);
		assertTrue(dirt.isMaxDirtCapacity());
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.Full);
		
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.DirtCapacity#getMaxDirtCapacity()}.
	 */
	@Test
	public void testGetMaxDirtCapacity() {
		DirtCapacity dirt = new DirtCapacity();
		assertTrue(dirt.getMaxDirtCapacity()==50);
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.DirtCapacity#emptyDirtTray(FrameListener)}.
	 */
	@Test
	public void testEmptyDirtTray() throws InterruptedException {
		DirtCapacity dirt = new DirtCapacity();
		dirt.emptyDirtTray(frameListener);
		assertTrue(dirt.getDirtCapacity() == 0);
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.Empty);
		dirt.setDirtCapacity(20);
		dirt.emptyDirtTray(frameListener);
		assertTrue(dirt.getDirtCapacity() == 0);
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.Empty);
		dirt.setDirtCapacity(50);
		dirt.emptyDirtTray(frameListener);
		assertTrue(dirt.getDirtCapacity() == 0);
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.Empty);
		
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.DirtCapacity#isDirtTrayEmpty()}.
	 */
	@Test
	public void testIsDirtTrayEmpty() {
		DirtCapacity dirt = new DirtCapacity();
		assertTrue(dirt.isDirtTrayEmpty());
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.Empty);
		dirt.setDirtCapacity(10);
		assertFalse(dirt.isDirtTrayEmpty());
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.NotFull);
		dirt.setDirtCapacity(50);
		assertFalse(dirt.isDirtTrayEmpty());
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.Full);
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.DirtCapacity#setDirtCapacityStatus(com.cleansweep.enums.DirtCapacityStatus)}.
	 */
	@Test
	public void testSetDirtCapacityStatus() {
		DirtCapacity dirt = new DirtCapacity();
		dirt.setDirtCapacityStatus(DirtCapacityStatus.Full);
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.Full);
		dirt.setDirtCapacityStatus(DirtCapacityStatus.NotFull);
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.NotFull);
		dirt.setDirtCapacityStatus(DirtCapacityStatus.Empty);
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.Empty);
	}
	
	@Test
	public void testSetDirtCapacity() {
		DirtCapacity dirt = new DirtCapacity();
		dirt.setDirtCapacity(20);
		assertTrue(dirt.getDirtCapacity() == 20);
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.NotFull);
		dirt.setDirtCapacity(0);
		assertTrue(dirt.getDirtCapacity() == 0);
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.Empty);
		dirt.setDirtCapacity(50);
		assertTrue(dirt.getDirtCapacity() == 50);
		assertTrue(dirt.getDirtCapacityStatus() == DirtCapacityStatus.Full);
	}

}
