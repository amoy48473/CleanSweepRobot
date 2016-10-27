package com.cleansweep.dataobjects;

import java.util.LinkedList;
import java.util.List;

//import com.cleansweep.enums.Direction;
import com.cleansweep.enums.FloorType;
import com.cleansweep.exceptions.InvalidEnvironmentObjectException;

public class PowerLevel {
	
	private int powerLevel;
    private List<FloorType> floorTypes;
	public static final int MAX_POWER_LEVEL = 100;
	public static final int Low_Power_Level = 25;
	
	public PowerLevel(){
		powerLevel = MAX_POWER_LEVEL;
		floorTypes = new LinkedList<FloorType>();
	}
	
	public int getPowerLevel(){
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
	
	public int getPowerUnits(FloorType floor)throws InvalidEnvironmentObjectException{
		if(floor == FloorType.Bare){
			return 1;
		}else if(floor == FloorType.LowPile){
			return 2;
		}else if(floor == FloorType.HighPile){
			return 3;
		}else{
            throw new InvalidEnvironmentObjectException("environment object '" + floor + "' does not match any defined environment objects");
		}
				
	}
	
	public void updatePowerLevel(FloorType currentFloorType)throws InvalidEnvironmentObjectException{

		

			if(!(floorTypes.size() == 0)){
				FloorType previousFloorType = floorTypes.get(floorTypes.size() - 1);
				if(previousFloorType != currentFloorType){
					int CurrentFloorPower = getPowerUnits(currentFloorType);
					int previousFloorPower = getPowerUnits(previousFloorType);
					powerLevel = powerLevel - ((CurrentFloorPower + previousFloorPower)/2);	
				}else{
					powerLevel = powerLevel - getPowerUnits(currentFloorType);
				}
			
	

		}else{
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
