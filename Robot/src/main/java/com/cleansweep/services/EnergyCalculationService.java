package com.cleansweep.services;

import com.cleansweep.dataobjects.ControlSimulatorNode;

/**
 * Created by amoy on 11/6/2016.
 * Interface for Eneergy Calcuation Service
 */
public interface EnergyCalculationService {

    /**
     * Calculates the amount of energy required for a move
     * @param fromNode
     * @param toNode
     * @return
     */
    double calculateEnergyRequiredToTraverseFloor(ControlSimulatorNode fromNode, ControlSimulatorNode toNode);

}
