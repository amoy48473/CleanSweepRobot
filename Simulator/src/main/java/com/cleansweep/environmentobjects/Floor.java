package com.cleansweep.environmentobjects;

import com.cleansweep.enums.FloorType;
import com.cleansweep.exceptions.CleanException;

public class Floor implements EnvironmentObject {
    private int dirtUnits;
    private FloorType floorType;

    public Floor(int dirt, FloorType type) {
        if (dirt < 0) {
            throw new IllegalArgumentException("Dirt units cannot be negative");
        }
        dirtUnits = dirt;
        floorType = type;
    }

    public int getDirtUnits() {
        return dirtUnits;
    }

    public FloorType getFloorType() {
        return floorType;
    }

    public boolean isBlocking() {
        return false;
    }

    public void clean() throws CleanException {
        if (dirtUnits <= 0) {
            throw new CleanException("Cannot clean a floor that is already clean");
        }
        dirtUnits--;
    }

}
