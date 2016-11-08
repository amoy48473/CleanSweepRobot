package com.cleansweep;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


import com.cleansweep.barriers.*;
import com.cleansweep.exceptions.*;
import com.cleansweep.environmentobjects.*;
import com.cleansweep.enums.*;
import com.cleansweep.dataobjects.*;


public class SensorSimulator {
    private int x;
    private int y;
    private int width;
    private int height;
    private Node[][] grid;

    // Grid cannot be cleaned twice in a row without moving
    boolean gridJustCleaned = false;

    /**
     * Constructor that takes a text file along with starting coordinates to create a grid
     * @param x
     * @param y
     * @param textFile
     * @throws IOException
     * @throws InvalidBarrierException
     * @throws InvalidEnvironmentObjectException
     */
    public SensorSimulator(int x, int y, String textFile) throws IOException, InvalidBarrierException, InvalidEnvironmentObjectException {
        this.x = x;
        this.y = y;
        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(textFile)));
        width = Integer.parseInt(br.readLine());
        height = Integer.parseInt(br.readLine());
        grid = new Node[width][height];
        for (int i = 0; i < height; i++) {
            String[] nodes = br.readLine().split("\\|");
            for (int j = 0; j < width; j++) {
                EnvironmentObject envObj;
                String[] nodeData = nodes[j].split(" ");
                if (nodeData[0].substring(0, 1).equals("F")) {
                    if (nodeData[0].substring(0, 2).equals("Fb")) {
                        int dirt = Integer.parseInt(nodeData[0].substring(2));
                        envObj = new Floor(dirt, FloorType.Bare);
                    } else if (nodeData[0].substring(0, 2).equals("Fl")) {
                        int dirt = Integer.parseInt(nodeData[0].substring(2));
                        envObj = new Floor(dirt, FloorType.LowPile);
                    } else if (nodeData[0].substring(0, 2).equals("Fh")) {
                        int dirt = Integer.parseInt(nodeData[0].substring(2));
                        envObj = new Floor(dirt, FloorType.HighPile);
                    } else {
                        throw new InvalidEnvironmentObjectException("environment object '" + nodeData[0] + "' in text file does not match any defined environment objects");
                    }
                } else if (nodeData[0].substring(0, 1).equals("O")) {
                    envObj = new Obstacle();
                } else if (nodeData[0].substring(0, 1).equals("S")) {
                    envObj = new Stairs();
                } else if (nodeData[0].substring(0, 1).equals("C")) {
                    envObj = new ChargingStation();
                } else {
                    throw new InvalidEnvironmentObjectException("environment object '" + nodeData[0] + "' in text file does not match any defined environment objects");
                }
                Barrier[] barriers = new Barrier[4];
                for (int k = 0; k < barriers.length; k++) {
                    String barrier = nodeData[k + 1];
                    if (barrier.equals("N")) {
                        barriers[k] = new NoBarrier();
                    } else if (barrier.equals("W")) {
                        barriers[k] = new Wall();
                    } else if (barrier.equals("Do")) {
                        barriers[k] = new Door(true);
                    } else if (barrier.equals("Dc")) {
                        barriers[k] = new Door(false);
                    } else {
                        throw new InvalidBarrierException("barrier '" + barrier + "' in text file does not match any defined environment objects");
                    }
                }
                grid[j][i] = new Node(envObj, barriers[0], barriers[1], barriers[2], barriers[3]);
            }
        }
        br.close();
        if (!(grid[x][y].getEnvironmentObject() instanceof ChargingStation)) {
            throw new IllegalArgumentException("Starting x and y coordinates of Clean Sweep must be on a charging station");
        }
    }

    /**
     * Checks whether the passed in direction is blocking
     * @param direction
     * @return
     */
    public boolean getNavigationSensor(Direction direction) {
        if (direction == Direction.North) {
            if (y == 0) {
                return false;
            }
            if (grid[x][y - 1].getEnvironmentObject().isBlocking()) {
                return false;
            }
            if (grid[x][y].getBarrier(Direction.North).isBlocking()) {
                return false;
            }
            return true;
        } else if (direction == Direction.East) {
            if (x == width - 1) {
                return false;
            }
            if (grid[x + 1][y].getEnvironmentObject().isBlocking()) {
                return false;
            }
            if (grid[x][y].getBarrier(Direction.East).isBlocking()) {
                return false;
            }
            return true;
        } else if (direction == Direction.South) {
            if (y == height - 1) {
                return false;
            }
            if (grid[x][y + 1].getEnvironmentObject().isBlocking()) {
                return false;
            }
            if (grid[x][y].getBarrier(Direction.South).isBlocking()) {
                return false;
            }
            return true;
        } else {
            if (x == 0) {
                return false;
            }
            if (grid[x - 1][y].getEnvironmentObject().isBlocking()) {
                return false;
            }
            if (grid[x][y].getBarrier(Direction.West).isBlocking()) {
                return false;
            }
            return true;
        }
    }

    /**
     * Movies the robot to direction. validates that direction is not blocking
     * @param direction
     * @throws BumpException
     */
    public void move(Direction direction) throws BumpException, InterruptedException {
        // Robot moved, you man clean again
        gridJustCleaned = false;

        if (direction == Direction.North) {
            if (y == 0) {
                throw new BumpException("Clean Sweep bumped into an obstruction");
            }
            if (grid[x][y - 1].getEnvironmentObject().isBlocking()) {
                throw new BumpException("Clean Sweep bumped into an obstruction");
            }
            if (grid[x][y].getBarrier(Direction.North).isBlocking()) {
                throw new BumpException("Clean Sweep bumped into an obstruction");
            }
            y--;
        } else if (direction == Direction.East) {
            if (x == width - 1) {
                throw new BumpException("Clean Sweep bumped into an obstruction");
            }
            if (grid[x + 1][y].getEnvironmentObject().isBlocking()) {
                throw new BumpException("Clean Sweep bumped into an obstruction");
            }
            if (grid[x][y].getBarrier(Direction.East).isBlocking()) {
                throw new BumpException("Clean Sweep bumped into an obstruction");
            }
            x++;
        } else if (direction == Direction.South) {
            if (y == height - 1) {
                throw new BumpException("Clean Sweep bumped into an obstruction");
            }
            if (grid[x][y + 1].getEnvironmentObject().isBlocking()) {
                throw new BumpException("Clean Sweep bumped into an obstruction");
            }
            if (grid[x][y].getBarrier(Direction.South).isBlocking()) {
                throw new BumpException("Clean Sweep bumped into an obstruction");
            }
            y++;
        } else {
            if (x == 0) {
                throw new BumpException("Clean Sweep bumped into an obstruction");
            }
            if (grid[x - 1][y].getEnvironmentObject().isBlocking()) {
                throw new BumpException("Clean Sweep bumped into an obstruction");
            }
            if (grid[x][y].getBarrier(Direction.West).isBlocking()) {
                throw new BumpException("Clean Sweep bumped into an obstruction");
            }
            x--;
        }


        //System.out.println("Moving: " + direction + " to: "+ x+ ", " +  y );

    }



    public void reverse(Direction direction) throws InterruptedException, BumpException {
        switch (direction){
            case North:
                move(Direction.South);
                break;
            case East:
                move(Direction.West);
                break;
            case South:
                move(Direction.North);
                break;
            case West:
                move(Direction.East);
                break;
            default:
                return;
        }

    }

    /**
     * Gets the current surface that the robot is currently on
     * @return
     */
    public FloorType getCurrentSurface() {
        if (grid[x][y].getEnvironmentObject() instanceof Floor) {
            return ((Floor) grid[x][y].getEnvironmentObject()).getFloorType();
        } else {
            return FloorType.NotFloor;
        }
    }

    /**
     * Gets the surface of the destination if heading in the specific direction
     * @param direction
     * @return
     */
    public FloorType getSurface(Direction direction) {
        if (direction == Direction.North) {
            if (y == 0) {
                return FloorType.NotFloor;
            }
            if (grid[x][y - 1].getEnvironmentObject().isBlocking()) {
                return FloorType.NotFloor;
            }
            if (grid[x][y].getBarrier(Direction.North).isBlocking()) {
                return FloorType.NotFloor;
            }
            if (grid[x][y - 1].getEnvironmentObject() instanceof Floor) {
                return ((Floor) grid[x][y - 1].getEnvironmentObject()).getFloorType();
            }
        } else if (direction == Direction.East) {
            if (x == width - 1) {
                return FloorType.NotFloor;
            }
            if (grid[x + 1][y].getEnvironmentObject().isBlocking()) {
                return FloorType.NotFloor;
            }
            if (grid[x][y].getBarrier(Direction.East).isBlocking()) {
                return FloorType.NotFloor;
            }
            if (grid[x + 1][y].getEnvironmentObject() instanceof Floor) {
                return ((Floor) grid[x + 1][y].getEnvironmentObject()).getFloorType();
            }
        } else if (direction == Direction.South) {
            if (y == height - 1) {
                return FloorType.NotFloor;
            }
            if (grid[x][y + 1].getEnvironmentObject().isBlocking()) {
                return FloorType.NotFloor;
            }
            if (grid[x][y].getBarrier(Direction.South).isBlocking()) {
                return FloorType.NotFloor;
            }
            if (grid[x][y + 1].getEnvironmentObject() instanceof Floor) {
                return ((Floor) grid[x][y + 1].getEnvironmentObject()).getFloorType();
            }
        } else if (direction == Direction.West) {
            if (x == 0) {
                return FloorType.NotFloor;
            }
            if (grid[x - 1][y].getEnvironmentObject().isBlocking()) {
                return FloorType.NotFloor;
            }
            if (grid[x][y].getBarrier(Direction.West).isBlocking()) {
                return FloorType.NotFloor;
            }
            if (grid[x - 1][y].getEnvironmentObject() instanceof Floor) {
                return ((Floor) grid[x - 1][y].getEnvironmentObject()).getFloorType();
            }
        }
        return FloorType.NotFloor;
    }

    public Point getPoint(){
        return new Point(x, y);
    }


    public boolean getDirtSensor() {
        if (grid[x][y].getEnvironmentObject() instanceof Floor) {
            if (((Floor) grid[x][y].getEnvironmentObject()).getDirtUnits() > 0) {
                return true;
            }
        }
        return false;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void clean() throws CleanException {
        if (gridJustCleaned){
            throw new CleanException("Floor is just cleaned. Cannot clean twice in row without moving");
        }
        ((Floor) grid[x][y].getEnvironmentObject()).clean();

        // Floor cleaned, a move is required before next move
        gridJustCleaned = true;
    }

    public boolean isGridJustCleaned(){
        return gridJustCleaned;
    }


    public boolean isChargingStation(){
        return grid[x][y].getEnvironmentObject().isChargingStation();
    }



}
