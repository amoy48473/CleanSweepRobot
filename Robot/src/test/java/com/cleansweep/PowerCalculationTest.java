package com.cleansweep;

import com.cleansweep.dataobjects.PowerLevel;
import com.cleansweep.enums.FloorType;
import com.cleansweep.exceptions.InvalidEnvironmentObjectException;
import com.cleansweep.exceptions.OutOfPowerException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by amoy on 11/6/2016.
 */
public class PowerCalculationTest {

    @Test
    public void testBareToBare() throws OutOfPowerException, InvalidEnvironmentObjectException {
        PowerLevel powerLevel = new PowerLevel();

        powerLevel.updatePowerLevel(FloorType.Bare);

        powerLevel.setPowerLevel(100);

        powerLevel.updatePowerLevel(FloorType.Bare);

        assertTrue(powerLevel.getPowerLevel()==99);

    }

    @Test
    public void testBareToLow() throws OutOfPowerException, InvalidEnvironmentObjectException {
        PowerLevel powerLevel = new PowerLevel();

        powerLevel.updatePowerLevel(FloorType.Bare);

        powerLevel.setPowerLevel(100);

        powerLevel.updatePowerLevel(FloorType.LowPile);

        assertTrue(powerLevel.getPowerLevel()==98.5);

    }

    @Test
    public void testBareToHigh() throws OutOfPowerException, InvalidEnvironmentObjectException {
        PowerLevel powerLevel = new PowerLevel();

        powerLevel.updatePowerLevel(FloorType.Bare);

        powerLevel.setPowerLevel(100);

        powerLevel.updatePowerLevel(FloorType.HighPile);

        assertTrue(powerLevel.getPowerLevel()==98);

    }

    @Test
    public void testLowToHigh() throws OutOfPowerException, InvalidEnvironmentObjectException {
        PowerLevel powerLevel = new PowerLevel();

        powerLevel.updatePowerLevel(FloorType.LowPile);

        powerLevel.setPowerLevel(100);

        powerLevel.updatePowerLevel(FloorType.HighPile);

        assertTrue(powerLevel.getPowerLevel()==97.5);

    }

    @Test
    public void testLowToLow() throws OutOfPowerException, InvalidEnvironmentObjectException {
        PowerLevel powerLevel = new PowerLevel();

        powerLevel.updatePowerLevel(FloorType.LowPile);

        powerLevel.setPowerLevel(100);

        powerLevel.updatePowerLevel(FloorType.LowPile);

        assertTrue(powerLevel.getPowerLevel()==98);

    }

    @Test
    public void testHighToHigh() throws OutOfPowerException, InvalidEnvironmentObjectException {
        PowerLevel powerLevel = new PowerLevel();

        powerLevel.updatePowerLevel(FloorType.HighPile);

        powerLevel.setPowerLevel(100);

        powerLevel.updatePowerLevel(FloorType.HighPile);

        assertTrue(powerLevel.getPowerLevel()==97);

    }

}
