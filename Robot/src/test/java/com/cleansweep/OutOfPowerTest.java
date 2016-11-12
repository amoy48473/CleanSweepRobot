package com.cleansweep;

import com.cleansweep.dataobjects.PowerLevel;
import com.cleansweep.enums.FloorType;
import com.cleansweep.exceptions.InvalidEnvironmentObjectException;
import com.cleansweep.exceptions.OutOfPowerException;
import org.junit.Test;

/**
 * Created by amoy on 11/6/2016.
 * Test of Out of Power
 */
public class OutOfPowerTest {

    @Test(expected = OutOfPowerException.class)
    public void testBareFloorOutOfPower() throws OutOfPowerException, InvalidEnvironmentObjectException {
        PowerLevel powerLevel = new PowerLevel();
        powerLevel.setPowerLevel(0);

        powerLevel.updatePowerLevel(FloorType.Bare);
    }

    @Test(expected = OutOfPowerException.class)
    public void testLowPileOutOfPower() throws OutOfPowerException, InvalidEnvironmentObjectException {
        PowerLevel powerLevel = new PowerLevel();
        powerLevel.setPowerLevel(1);

        powerLevel.updatePowerLevel(FloorType.LowPile);
    }

    @Test(expected = OutOfPowerException.class)
    public void testHighPileOutOfPower() throws OutOfPowerException, InvalidEnvironmentObjectException {
        PowerLevel powerLevel = new PowerLevel();
        powerLevel.setPowerLevel(2);

        powerLevel.updatePowerLevel(FloorType.HighPile);
    }

    @Test(expected = OutOfPowerException.class)
    public void testBareLowOutOfPower() throws OutOfPowerException, InvalidEnvironmentObjectException {
        PowerLevel powerLevel = new PowerLevel();

        powerLevel.updatePowerLevel(FloorType.LowPile);

        powerLevel.setPowerLevel(1);


        powerLevel.updatePowerLevel(FloorType.Bare);
    }

    @Test(expected = OutOfPowerException.class)
    public void testBareHighOutOfPower() throws OutOfPowerException, InvalidEnvironmentObjectException {
        PowerLevel powerLevel = new PowerLevel();

        powerLevel.updatePowerLevel(FloorType.HighPile);

        powerLevel.setPowerLevel(1);


        powerLevel.updatePowerLevel(FloorType.Bare);
    }


    @Test(expected = OutOfPowerException.class)
    public void testLowHighOutOfPower() throws OutOfPowerException, InvalidEnvironmentObjectException {
        PowerLevel powerLevel = new PowerLevel();

        powerLevel.updatePowerLevel(FloorType.HighPile);

        powerLevel.setPowerLevel(2);


        powerLevel.updatePowerLevel(FloorType.LowPile);
    }





}
