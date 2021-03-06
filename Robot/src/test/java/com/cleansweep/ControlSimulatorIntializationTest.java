package com.cleansweep;

import com.cleansweep.dataobjects.DirtCapacity;
import com.cleansweep.dataobjects.Point;
import com.cleansweep.dataobjects.PowerLevel;
import com.cleansweep.enums.Direction;
import com.cleansweep.enums.DirtCapacityStatus;
import com.cleansweep.exceptions.*;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.io.IOException;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by amoy on 11/11/2016.
 * Testing for Control Simulator
 */
public class ControlSimulatorIntializationTest {

    ControlSimulator controlSimulator;

    @Before
    public void SetUp() throws InvalidEnvironmentObjectException, IOException, InvalidBarrierException {
        SensorSimulator sensorSimulator = new  SensorSimulator(0,9, "floorplan.txt");
        controlSimulator = new ControlSimulator(sensorSimulator, 1);
    }

    @Test
    public void testStartingLocation(){
        assertTrue(controlSimulator.getCurrentLocation().getX() == 0);
        assertTrue(controlSimulator.getCurrentLocation().getX() == 0);
    }

    @Test
    public void testControlSimulator() throws InterruptedException, InvalidEnvironmentObjectException, IOException, OutOfPowerException, CapacityFullException, CleanException, BumpException {

        // Assert that Indicator is turned off
        assertTrue(!controlSimulator.getEmptyMeIndicator().getEmptyMeIndicator());

        // Assert that power level is max
        assertTrue(controlSimulator.getPowerLevel().getPowerLevel() == PowerLevel.MAX_POWER_LEVEL);

        // Assert that Dirt level is at 0
        assertTrue(controlSimulator.getDirtCapacity().isDirtTrayEmpty());
        assertTrue(controlSimulator.getDirtCapacity().getDirtCapacityStatus() == DirtCapacityStatus.Empty);
        assertTrue(controlSimulator.getDirtCapacity().getDirtCapacity() == 0);

        // Manually set dirt count at max and try to clean, should throw exception
        controlSimulator.moveSensorSimulator(Direction.North);
        controlSimulator.getEmptyMeIndicator().setEmptyMeIndicator(true);
        try {
            controlSimulator.clean();
            fail();
        } catch (CapacityFullException ex){
            // Success
        }
        // Move it back to beginning
        controlSimulator.moveSensorSimulator(Direction.South);

        JFrame jFrame = mock(JFrame.class);

        controlSimulator.run();

        Point startPoint = controlSimulator.getCurrentLocation();

        // Assert that traveling to own location is an empty list
        assertTrue(controlSimulator.bfsToDestination(startPoint, startPoint).isEmpty());



    }

}
