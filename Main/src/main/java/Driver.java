import com.cleansweep.ControlSimulator;
import com.cleansweep.exceptions.*;
import com.cleansweep.SensorSimulator;
import com.cleansweep.ui.SimulatorFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Driver {

	public static void main(String[] args) throws IOException, InvalidBarrierException, InvalidEnvironmentObjectException, BumpException, CleanException, InterruptedException, OutOfPowerException, CapacityFullException {
		SensorSimulator s = new SensorSimulator(0,9, "floorplan.txt");
		ControlSimulator c = new ControlSimulator(s, 100);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(new SimulatorFrame(c), BorderLayout.CENTER);
		frame.setSize(400, 400);
		frame.setMinimumSize(frame.getSize());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		c.run(frame);

	}

}
