/**
 * 
 */
package com.cleansweep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cleansweep.dataobjects.PowerLevel;
import com.cleansweep.enums.FloorType;
import com.cleansweep.exceptions.InvalidEnvironmentObjectException;

/**
 * @author monty_000
 *
 */
public class PowerLevelTest {

	/**
	 * Test method for {@link com.cleansweep.dataobjects.PowerLevel#PowerLevel()}.
	 */
	@Test
	public void testPowerLevel() {
		PowerLevel powerLevel = new PowerLevel();
		assertNotNull(powerLevel);
		assertTrue(powerLevel.getPowerLevel() == 100);
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.PowerLevel#getPowerLevel()}.
	 */
	@Test
	public void testGetPowerLevel() {
		PowerLevel powerLevel = new PowerLevel();
		powerLevel.setPowerLevel(10);
		assertTrue(powerLevel.getPowerLevel() == 10);
		
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.PowerLevel#isPowerLow()}.
	 */
	@Test
	public void testIsPowerLow() {
		PowerLevel powerLevel = new PowerLevel();
		powerLevel.setPowerLevel(15);
		assertTrue(powerLevel.isPowerLow());
		powerLevel.setPowerLevel(26);
		assertFalse(powerLevel.isPowerLow());
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.PowerLevel#isSweeperFullyCharged()}.
	 */
	@Test
	public void testIsSweeperFullyCharged() {
		PowerLevel powerLevel = new PowerLevel();
		assertTrue(powerLevel.isSweeperFullyCharged());
		powerLevel.setPowerLevel(50);
		assertFalse(powerLevel.isSweeperFullyCharged());
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.PowerLevel#getPowerUnits(com.cleansweep.enums.FloorType)}.
	 */
	@Test
	public void testGetPowerUnits() {
		PowerLevel powerLevel = new PowerLevel();
		int powerUnit;
		try{
			powerUnit = powerLevel.getPowerUnits(FloorType.NotFloor);
			assertTrue(powerUnit == 0);
			powerUnit = powerLevel.getPowerUnits(FloorType.Bare);
			assertTrue(powerUnit == 1);
			powerUnit = powerLevel.getPowerUnits(FloorType.LowPile);
			assertTrue(powerUnit == 2);
			powerUnit = powerLevel.getPowerUnits(FloorType.HighPile);
			assertTrue(powerUnit == 3);
		}catch(InvalidEnvironmentObjectException ieo){
			
		}

		
		
	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.PowerLevel#updatePowerLevel(com.cleansweep.enums.FloorType, com.cleansweep.enums.FloorType)}.
	 */
	@Test
	public void testUpdatePowerLevel() {
		PowerLevel powerLevel = new PowerLevel();
		try{
			powerLevel.updatePowerLevel(FloorType.HighPile);
			assertTrue(powerLevel.getPowerLevel() == 97);
			powerLevel.updatePowerLevel(FloorType.HighPile);
			assertTrue(powerLevel.getPowerLevel() == 94);
			powerLevel.updatePowerLevel(FloorType.LowPile);
			assertTrue(powerLevel.getPowerLevel() == 92);
			powerLevel.updatePowerLevel(FloorType.LowPile);
			assertTrue(powerLevel.getPowerLevel() == 90);
			powerLevel.updatePowerLevel(FloorType.Bare);
			assertTrue(powerLevel.getPowerLevel() == 89);
			powerLevel.updatePowerLevel(FloorType.HighPile);
			assertTrue(powerLevel.getPowerLevel() == 87);
		}catch(InvalidEnvironmentObjectException ieo){
			
		}

	}

	/**
	 * Test method for {@link com.cleansweep.dataobjects.PowerLevel#charge()}.
	 */
	@Test
	public void testCharge() {
		PowerLevel powerLevel = new PowerLevel();
		powerLevel.setPowerLevel(50);
		powerLevel.charge();
		assertTrue(powerLevel.isSweeperFullyCharged());
	}

}
