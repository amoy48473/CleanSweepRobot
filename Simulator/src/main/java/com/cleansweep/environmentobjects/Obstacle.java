package com.cleansweep.environmentobjects;

public class Obstacle implements EnvironmentObject{

	public int getDirtUnits() {
		return 0;
	}

	public boolean isBlocking() {
		return true;
	}

	public boolean isChargingStation() {
		return false;
	}

	public boolean isFloor() {
		return false;
	}

}
