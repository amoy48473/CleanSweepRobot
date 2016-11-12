package com.cleansweep;

import com.cleansweep.exceptions.InvalidBarrierException;
import com.cleansweep.exceptions.InvalidEnvironmentObjectException;
import org.junit.Test;

import static junit.framework.TestCase.fail;

/**
 * Created by amoy on 11/12/2016.
 */
public class SimulatorInstantiateTest {

    @Test
    public void InvalidEnvironmentObjectException(){
        try {
            new SensorSimulator(0, 0,  "floorplaninvalidEnvObj.txt");
            fail();
        } catch (InvalidEnvironmentObjectException ex){

        } catch (Exception ex){
            fail();
        }
    }

    @Test
    public void InvalidBarrierException(){
        try {
            new SensorSimulator(0, 0,  "floorplaninvalidBarrier.txt");
            fail();
        } catch (InvalidBarrierException ex){

        } catch (Exception ex){
            fail();
        }
    }


}
