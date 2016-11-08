package com.cleansweep.enums;

public enum FloorType {
	Bare(1), LowPile(2), HighPile(3), NotFloor(1);

	private double energyRequired;

	FloorType(double energyRequired){
		this.energyRequired = energyRequired;
	}

	public double getEnergyRequired() {
		return energyRequired;
	}


}
