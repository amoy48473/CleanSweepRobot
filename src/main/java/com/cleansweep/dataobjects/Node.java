package com.cleansweep.dataobjects;

import com.cleansweep.barriers.Barrier;
import com.cleansweep.enums.Direction;
import com.cleansweep.environmentobjects.EnvironmentObject;

public class Node {
    private EnvironmentObject o;
    private Barrier northBarrier;
    private Barrier southBarrier;
    private Barrier eastBarrier;
    private Barrier westBarrier;

    public Node(EnvironmentObject o, Barrier northBarrier, Barrier eastBarrier, Barrier southBarrier, Barrier westBarrier) {
        this.o = o;
        this.northBarrier = northBarrier;
        this.southBarrier = southBarrier;
        this.eastBarrier = eastBarrier;
        this.westBarrier = westBarrier;
    }

    public EnvironmentObject getEnvironmentObject() {
        return o;
    }

    public Barrier getBarrier(Direction direction) {
        if (Direction.North == direction) {
            return northBarrier;
        } else if (Direction.East == direction) {
            return eastBarrier;
        } else if (Direction.South == direction) {
            return southBarrier;
        } else {
            return westBarrier;
        }
    }


}
