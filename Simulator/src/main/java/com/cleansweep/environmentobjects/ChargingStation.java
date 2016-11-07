package com.cleansweep.environmentobjects;

public class ChargingStation implements EnvironmentObject{
	public ChargingStation(){
		
	}
	
	public int getDirtUnits() {
		return 0;
	}

	public boolean isBlocking() {
		return false;
	}

	public boolean isChargingStation(){
		return true;
	}

	public boolean isFloor() {
		return false;
	}

}
