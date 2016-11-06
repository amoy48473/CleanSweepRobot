package com.cleansweep.dataobjects;

import com.cleansweep.OutOfPowerException;
import com.cleansweep.enums.FloorType;
import com.cleansweep.exceptions.InvalidEnvironmentObjectException;

import java.util.LinkedList;
import java.util.List;

//import com.cleansweep.enums.Direction;

public class PowerLevel {
	
	private double powerLevel;
    private List<FloorType> floorTypes;
	public static final int MAX_POWER_LEVEL = 100;
	public static final int Low_Power_Level = 25;
	
	public PowerLevel(){
		powerLevel = MAX_POWER_LEVEL;
		floorTypes = new LinkedList<FloorType>();
	}
	
	public double getPowerLevel(){
		return powerLevel;
	}
	
	public void setPowerLevel(int power){
		powerLevel = power;
	}
	
	public boolean isPowerLow(){
		return powerLevel < Low_Power_Level;
	}
	
	public boolean isSweeperFullyCharged(){
		return powerLevel == MAX_POWER_LEVEL;
	}
	
	public double getPowerUnits(FloorType floor)throws InvalidEnvironmentObjectException{
		if(floor == FloorType.Bare){
			return 1;
		}else if(floor == FloorType.LowPile){
			return 2;
		}else if(floor == FloorType.HighPile){
			return 3;
		}else{
			// Since we're required to use energy on on traversals, stations are considered 1 movement
			return 1;
		}
				
	}
	
	public void updatePowerLevel(FloorType currentFloorType)throws InvalidEnvironmentObjectException, OutOfPowerException{

		

			if(!(floorTypes.size() == 0)){
				FloorType previousFloorType = floorTypes.get(floorTypes.size() - 1);
				if(previousFloorType != currentFloorType){
					double CurrentFloorPower = getPowerUnits(currentFloorType);
					double previousFloorPower = getPowerUnits(previousFloorType);

					if (powerLevel - (( CurrentFloorPower +  previousFloorPower) /2) < 0){
						throw new OutOfPowerException();
					}

					powerLevel = powerLevel - (( CurrentFloorPower + previousFloorPower)/2);
				}else{
					if ((powerLevel - getPowerUnits(currentFloorType)) < 0){
						throw new OutOfPowerException();
					}


					powerLevel = powerLevel - getPowerUnits(currentFloorType);
				}
			
	

		}else{
			if ((powerLevel - getPowerUnits(currentFloorType)) < 0){
				throw new OutOfPowerException();
			}
			powerLevel = powerLevel - getPowerUnits(currentFloorType);
		}
		floorTypes.add(currentFloorType);
		
	}
	
	private void clearPreviousFloorTypes(){
		floorTypes.clear();
	}
	
	public void charge(){
		powerLevel = MAX_POWER_LEVEL;
	}
	
	

}
