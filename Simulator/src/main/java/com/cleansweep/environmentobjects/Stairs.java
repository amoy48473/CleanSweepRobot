package com.cleansweep.environmentobjects;

public class Stairs implements EnvironmentObject{
	public Stairs(){
		
	}
	
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