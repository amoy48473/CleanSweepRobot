import com.cleansweep.ControlSimulator;
import com.cleansweep.SensorSimulator;
import com.cleansweep.exceptions.BumpException;
import com.cleansweep.exceptions.CleanException;
import com.cleansweep.exceptions.InvalidBarrierException;
import com.cleansweep.exceptions.InvalidEnvironmentObjectException;
import com.cleansweep.ui.SimulatorFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Driver {

	public static void main(String[] args) throws IOException, InvalidBarrierException, InvalidEnvironmentObjectException, BumpException, CleanException, InterruptedException {
		SensorSimulator s = new SensorSimulator(0,9, "floorplan.txt");
		ControlSimulator c = new ControlSimulator(s);

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
