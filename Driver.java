import java.io.IOException;

public class Driver {

	public static void main(String[] args) throws IOException, InvalidBarrierException, InvalidEnvironmentObjectException, BumpException {
		SensorSimulator s = new SensorSimulator(0,9,"floorplan.txt");
	}

}
