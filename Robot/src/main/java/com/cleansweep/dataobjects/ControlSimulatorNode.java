package com.cleansweep.dataobjects;

import com.cleansweep.enums.Direction;
import com.cleansweep.enums.FloorType;

/**
 * Created by allenmoy on 10/11/16.
 * This a node known by the Control Simulator (Robot)
 */
public class ControlSimulatorNode {

    private boolean isChargingStation;

    private boolean visited;

    private boolean cleaned;

    private FloorType floorType;

    private boolean leftBlocking, topBlocking, rightBlocking, downBlocking;

    public ControlSimulatorNode(){

    }

    public boolean isBlocking(Direction direction){
        switch (direction){
            case North:
                return topBlocking;
            case East:
                return rightBlocking;
            case South:
                return downBlocking;
            case West:
                return leftBlocking;
            default:
                return false;
        }
    }

    public void setBlocking(Direction direction){
        switch (direction){
            case North:
                topBlocking = true;
                break;
            case East:
                rightBlocking = true;
                break;
            case South:
                downBlocking = true;
                break;
            case West:
                leftBlocking = true;
                break;
            default: return;
        }
    };

    public void setOpen(Direction direction){
        switch (direction){
            case North:
                topBlocking = false;
                break;
            case East:
                rightBlocking = false;
                break;
            case South:
                downBlocking = false;
                break;
            case West:
                leftBlocking = false;
                break;
            default: return;
        }
    };


    public boolean isDownBlocking() {
        return downBlocking;
    }




    public boolean isLeftBlocking() {
        return leftBlocking;
    }


    public boolean isRightBlocking() {
        return rightBlocking;
    }


    public boolean isTopBlocking() {
        return topBlocking;
    }

    public void setTopBlocking(boolean topBlocking) {
        this.topBlocking = topBlocking;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isCleaned() {
        return cleaned;
    }

    public void setCleaned(boolean cleaned) {
        this.cleaned = cleaned;
    }


    public FloorType getFloorType() {
        return floorType;
    }

    public void setFloorType(FloorType floorType) {
        this.floorType = floorType;
    }

    public boolean isChargingStation() {
        return isChargingStation;
    }

    public void setChargingStation(boolean chargingStation) {
        isChargingStation = chargingStation;
    }
}
