package com.cleansweep.dataobjects;

import com.cleansweep.environmentobjects.EnvironmentObject;

/**
 * Created by allenmoy on 10/11/16.
 */
public class ControlSimulatorNode {

    private boolean visited;

    private EnvironmentObject environmentObject;

    private boolean leftBlocking, topBlocking, rightBlocking, downBlocking;

    public ControlSimulatorNode(){

    }


    public boolean isDownBlocking() {
        return downBlocking;
    }

    public void setDownBlocking(boolean downBlocking) {
        this.downBlocking = downBlocking;
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

    public void setLeftBlocking(boolean leftBlocking) {
        this.leftBlocking = leftBlocking;
    }

    public boolean isRightBlocking() {
        return rightBlocking;
    }

    public void setRightBlocking(boolean rightBlocking) {
        this.rightBlocking = rightBlocking;
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
