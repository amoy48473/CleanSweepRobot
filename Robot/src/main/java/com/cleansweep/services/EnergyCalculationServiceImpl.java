package com.cleansweep.services;

import com.cleansweep.dataobjects.ControlSimulatorNode;
import com.cleansweep.enums.FloorType;

/**
 * Created by amoy on 11/6/2016.
 * Service class to be used for energy calculation
 */
public class EnergyCalculationServiceImpl implements EnergyCalculationService{



    public double calculateEnergyRequiredToTraverseFloor(ControlSimulatorNode fromNode, ControlSimulatorNode toNode) {
        FloorType fromFloorType = fromNode.getFloorType();
        FloorType toFloorType = toNode.getFloorType();

        if(fromFloorType != toFloorType){
            double CurrentFloorPower = toFloorType.getEnergyRequired();
            double previousFloorPower = fromFloorType.getEnergyRequired();


            return (( CurrentFloorPower + previousFloorPower)/2);
        }else{



            return toFloorType.getEnergyRequired();
        }


    }
}
