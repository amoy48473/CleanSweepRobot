package com.cleansweep.services;

import com.cleansweep.enums.FloorType;

/**
 * Created by amoy on 11/6/2016.
 */
public interface EnergyCalculationService {

    /**
     * Calculates the amount of energy required for a move
     * @param fromFloor
     * @param toFloor
     * @return
     */
    double calculateEnergyRequiredToTraverseFloor(FloorType fromFloor, FloorType toFloor);

}
