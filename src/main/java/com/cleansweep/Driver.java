package com.cleansweep;

import com.cleansweep.exceptions.BumpException;
import com.cleansweep.exceptions.CleanException;
import com.cleansweep.exceptions.InvalidBarrierException;
import com.cleansweep.exceptions.InvalidEnvironmentObjectException;

import java.io.IOException;

public class Driver {

	public static void main(String[] args) throws IOException, InvalidBarrierException, InvalidEnvironmentObjectException, BumpException, CleanException, InterruptedException {
		SensorSimulator s = new SensorSimulator(0,9,"floorplan.txt");
		ControlSimulator c = new ControlSimulator(s);

		c.run();

	}

}
