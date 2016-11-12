package com.cleansweep;

import com.cleansweep.enums.Direction;
import com.cleansweep.exceptions.BumpException;
import com.cleansweep.exceptions.InvalidBarrierException;
import com.cleansweep.exceptions.InvalidEnvironmentObjectException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.fail;

/**
 * Created by amoy on 11/12/2016.
 * Test for Testing movements and bump exceptions
 */
public class MoveTest {

    SensorSimulator sensorSimulator;

    @Before
    public void setup() throws InvalidEnvironmentObjectException, IOException, InvalidBarrierException {
        sensorSimulator = new SensorSimulator(0, 9, "floorplan.txt");
    }

    @Test
    public void testBumpException()
    {
        try {
            sensorSimulator.move(Direction.South);
            fail();
        } catch (BumpException be){

        } catch (Exception ex){
            fail();
        }
    }



}
