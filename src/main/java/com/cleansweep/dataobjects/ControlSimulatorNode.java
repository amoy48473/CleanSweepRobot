package com.cleansweep.dataobjects;

import com.cleansweep.enums.Direction;
import com.cleansweep.environmentobjects.EnvironmentObject;

/**
 * Created by allenmoy on 10/11/16.
 * This a node known by the Control Simulator (Robot)
 */
public class ControlSimulatorNode {

    private boolean visited;

    private EnvironmentObject environmentObject;

    private boolean leftBlocking, topBlocking, rightBlocking, downBlocking;

    public ControlSimulatorNode(){

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


    public EnvironmentObject getEnvironmentObject() {
        return environmentObject;
    }

    public void setEnvironmentObject(EnvironmentObject environmentObject) {
        this.environmentObject = environmentObject;
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
}
