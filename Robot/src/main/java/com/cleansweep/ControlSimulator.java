package com.cleansweep;

import com.cleansweep.dataobjects.*;
import com.cleansweep.enums.Direction;
import com.cleansweep.enums.FloorType;
import com.cleansweep.environmentobjects.ChargingStation;
import com.cleansweep.environmentobjects.Floor;
import com.cleansweep.exceptions.BumpException;
import com.cleansweep.exceptions.CleanException;
import com.cleansweep.exceptions.InvalidEnvironmentObjectException;
import com.cleansweep.services.EnergyCalculationService;
import com.cleansweep.services.EnergyCalculationServiceImpl;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

/**
 * Created by allenmoy on 10/10/16.
 */
public class ControlSimulator{


    private SensorSimulator sensorSimulator;

    private Stack<Point> stack  = new Stack<Point>();

    private JFrame jFrame;

    private ActivityLog activityLogData;

    private ControlSimulatorNode[][] nodes;
    
    private DirtCapacity dirtCapacity;
    
    private PowerLevel powerLevel;
    
    private EmptyMeIndicator emptyMeIndicator;

    // List of charging stations that the robot knows about, it is stored in memory
    private List<Point> chargingStations;

    private EnergyCalculationService energyCalculationService;
    
 
    public ControlSimulator(SensorSimulator sensorSimulator){
        this.sensorSimulator = sensorSimulator;
        this.dirtCapacity = new DirtCapacity();
        this.powerLevel = new PowerLevel();
        this.activityLogData = new ActivityLog();
        this.emptyMeIndicator = new EmptyMeIndicator();

        chargingStations = new ArrayList<Point>();

        energyCalculationService = new EnergyCalculationServiceImpl();
        
        //fullyCleaned = new boolean[sensorSimulator.getHeight()][sensorSimulator.getWidth()];
        nodes = new ControlSimulatorNode[sensorSimulator.getHeight()][sensorSimulator.getWidth()];
        for (int i=0; i<nodes.length; i++){
            ControlSimulatorNode[] row = nodes[i];
            for (int j=0; j< row.length; j++){

                nodes[i][j] = new ControlSimulatorNode();
            }
        }
    }

    /**
     * Scan this block and it's neighbors to check blockers, and load them into memory
     * After scanning, use DFS to move into the neighbor at the top of the stack that was just added.
     * To get to this location, the robot will use BFS to find the closest path to that location
     * @param root
     * @throws BumpException
     * @throws InterruptedException
     * @throws CleanException
     */
    private void search(Point root) throws IOException, BumpException, InterruptedException, CleanException, InvalidEnvironmentObjectException, OutOfPowerException {

        // Check if current location is a charging station, if it is, then add it to the list
        if (sensorSimulator.isChargingStation() && !chargingStations.contains(root)){
            chargingStations.add(root);
        }


        if (root == null) return;

        // Set this point as visited
        nodes[root.getY()][root.getX()].setVisited(true);


        // For every neighbor
        for (Direction direction : Direction.values()){
            Point calculatedPoint = getCalculatedPoint(direction, root);

            // if point is not blocked and we have not cleaned there, the depth first search
            if (sensorSimulator.getNavigationSensor(direction)){
                activityLogData.activityLog.write(new Date() + " Checking directional sensor: " + 
                		(sensorSimulator.getNavigationSensor(direction) ? direction + " Direction: Clear Path" : direction + " Direction: Blocked Path"));
                activityLogData.activityLog.flush();
                activityLogData.activityLog.newLine(); 

                nodes[root.getY()][root.getX()].setOpen(direction);

                if (!nodes[calculatedPoint.getY()][calculatedPoint.getX()].isCleaned()){
                    stack.push(calculatedPoint);
                }
            } else if (!sensorSimulator.getNavigationSensor(direction)){
                activityLogData.activityLog.write(new Date() + " Checking directional sensor: " + 
                		(sensorSimulator.getNavigationSensor(direction) ? direction + " Direction: Clear Path" : direction + " Direction: Blocked Path"));
                activityLogData.activityLog.flush();
                activityLogData.activityLog.newLine(); 
                nodes[root.getY()][root.getX()].setBlocking(direction);
            }


        }

        while (!stack.isEmpty()){

            Point destPoint;

            // Before continuing, make sure that the rebot can still return one of the many charging station that it knows about before
            // running out of power, take a peek of what is in the stack and see if the robot can return from there, else, rebot must return
            // immediately

            Point chargingStationLocation = getClosestChargingStationIfRequired(this.getPowerLevel(), stack.peek());
            if (chargingStationLocation!=null){
                destPoint = chargingStationLocation;
            } else {
                destPoint = stack.pop();
            }

            if (nodes[destPoint.getY()][destPoint.getX()].isCleaned() &&
                    nodes[destPoint.getY()][destPoint.getX()].isVisited() ) continue;

            List<Direction> path = bfsToDestination(getCurrentLocation(), destPoint);



            for (Direction direction: path){
                moveSensorSimulator(sensorSimulator, direction);
                activityLogData.activityLog.write(new Date() + " Moving to current point(x,y): (" + sensorSimulator.getPoint().getX() + "," 
             	+ sensorSimulator.getPoint().getY()  + 
             	"); FloorType: " + sensorSimulator.getCurrentSurface());
                activityLogData.activityLog.flush();
                activityLogData.activityLog.newLine();            

                	
                // If there is dirt here then clean it
                if (sensorSimulator.getDirtSensor()) {
                    activityLogData.activityLog.write(new Date() + " Checking Dirt Sensor: " + (sensorSimulator.getDirtSensor() ? "Floor Dirty" : "Floor Clean"));
                    activityLogData.activityLog.flush();
                    activityLogData.activityLog.newLine();

                    sensorSimulator.clean();

                    

                    Point currentPoint = sensorSimulator.getPoint();


                    // If there is still dirt here, then add it back in stack to come back later
                    if (!sensorSimulator.getDirtSensor()){
                        nodes[currentPoint.getY()][currentPoint.getX()].setCleaned(true);
                        activityLogData.activityLog.write(new Date() + " Checking Dirt Sensor: " + (sensorSimulator.getDirtSensor() ? "Floor Dirty" : "Floor Clean"));
                        activityLogData.activityLog.flush();
                        activityLogData.activityLog.newLine();
                    }
                    
                    if(sensorSimulator.isGridJustCleaned()){
                    	dirtCapacity.updateDirtCapacity();
                    	if(dirtCapacity.isMaxDirtCapacity()){
                    		emptyMeIndicator.turnOnEmptyMeIndicator();
                    	}
                        activityLogData.activityLog.write(new Date() + " Cleaning current point(x,y): (" + currentPoint.getX() + "," 
                    	+ currentPoint.getY() + "); Dirt Capacity: " + dirtCapacity.getDirtCapacity() +
                    	"; Dirt Status: " + dirtCapacity.getDirtCapacityStatus() + 
                    	"; FloorType: " + sensorSimulator.getCurrentSurface() + "; EmptyMeIndicator:" + 
                    	(emptyMeIndicator.getEmptyMeIndicator() ? "ON" : "OFF"));
                        activityLogData.activityLog.flush();
                        activityLogData.activityLog.newLine();            


                    }
                }
            }

            search(sensorSimulator.getPoint());

            }

    }

    /**
     * Method determines whether the robot needs to return to the charging station, returns null if it is not needed,
     * will return the point of the charging station otherwise
     * @param currentPowerLevel
     * @param nextLocation
     * @return
     */
    private Point getClosestChargingStationIfRequired(PowerLevel currentPowerLevel, Point nextLocation) {
        Point closest = null;
        double leastEnergy = Double.POSITIVE_INFINITY;

        // Find the closest charging station that the robot knows about that requires the least energy
        for(Point chargingStation: chargingStations){
            double energyRequired = 0;


            List<Direction> directions = bfsToDestination(nextLocation, chargingStation);
            Point tempCurrentLocation = new Point(nextLocation.getX(), nextLocation.getY());
            Point tempNextLocation;

            for (Direction direction: directions){


                // Simulate the shortest path and check the least amount of energy to get there.
                if (direction == Direction.North){
                    tempNextLocation = new Point(tempCurrentLocation.getX(), tempCurrentLocation.getY()-1);
                }
                else if (direction == Direction.East){
                    tempNextLocation = new Point(tempCurrentLocation.getX()+1, tempCurrentLocation.getY()+1);
                }
                else if (direction == Direction.South){
                    tempNextLocation = new Point(tempCurrentLocation.getX(), tempCurrentLocation.getY()+1);

                }
                else {
                    tempNextLocation = new Point(tempCurrentLocation.getX()-1, tempCurrentLocation.getY()+1);
                }

                energyRequired += energyCalculationService.calculateEnergyRequiredToTraverseFloor( ((Floor) nodes[tempCurrentLocation.getY()][tempCurrentLocation.getX()].getEnvironmentObject()).getFloorType(),
                         ((Floor) nodes[tempNextLocation.getY()][tempNextLocation.getX()].getEnvironmentObject()).getFloorType());

                tempCurrentLocation = tempNextLocation;
            }

            if (energyRequired < leastEnergy){
                leastEnergy = energyRequired;
                closest = chargingStation;
            }
        }

        if (closest == null) return null;

        // If we can't even return to the closest charging station, return before our move
        if (leastEnergy > currentPowerLevel.getPowerLevel()){
            return closest;
        }

        return null;
    }

    private void moveSensorSimulator(SensorSimulator sim, Direction direction) throws InterruptedException, BumpException, InvalidEnvironmentObjectException, IOException, OutOfPowerException {
        Thread.sleep(500);

        // Update floor counter
        FloorType currentFloorType = sensorSimulator.getCurrentSurface();
        // Update the power level
        powerLevel.updatePowerLevel(currentFloorType);
        activityLogData.activityLog.write(new Date() + " Updating power level: " + powerLevel.getPowerLevel() +
                (powerLevel.isPowerLow() ? "; Power Level is Low!" : "; Power Level is OK."));
        activityLogData.activityLog.flush();
        activityLogData.activityLog.newLine();

        sensorSimulator.move(direction);



        jFrame.repaint();



    }

    /**
     * The robot knows about its destination, give start and finish and it will try
     * to traverse these with the shortest path
     * @param current
     * @param finish
     */
    private List<Direction> bfsToDestination(Point current, Point finish){
        Set<String> visited = new HashSet<String>();

        Map<String, Point> prev = new HashMap<String, Point>();
        Map<String, Direction> prevDirection = new HashMap<String, Direction>();

        Queue<Point> q = new LinkedList<Point>();

        q.add(current);

        visited.add(current.toString());



        while (!q.isEmpty()){
            current = q.remove();

            if (current.getX()==finish.getX() && current.getY()==finish.getY()){
                break;
            } else {
                for (Direction direction: Direction.values()){

                    if (current.getX()==finish.getX() && current.getY()==finish.getY()) {
                        break;
                    }

                    // Only search the areas we know about and of those only those that haven't been searched
                    Point nextPoint = getCalculatedPoint(direction, current);

                    if (notOutOfBounds(nextPoint)
                            && !isBump(current, direction)
                            && !visited.contains(nextPoint.toString())){
                        q.add(nextPoint);

                        visited.add(nextPoint.toString());
                        prev.put(nextPoint.toString(), current);
                        prevDirection.put(nextPoint.toString(), direction);
                    }

                }
            }



        }

        List<Direction> direction = new LinkedList<Direction>();
        for (Point point = finish; prev.get(point.toString())!=null; point = prev.get(point.toString())){

            direction.add(0, prevDirection.get(point.toString()));
        }


        return direction;
    }

    private Direction reverse(Direction direction) {
        switch (direction){
            case North:
                return Direction.South;
            case South:
                return Direction.North;
            case East:
                return Direction.West;
            case West:
                return Direction.East;
        }
        return null;
    }

    private boolean notOutOfBounds(Point nextPoint) {
        if (nextPoint.getX() == sensorSimulator.getWidth()
                || nextPoint.getX() < 0 || nextPoint.getY() == sensorSimulator.getHeight()
                || nextPoint.getY() < 0
                ){
            return false;
        }
        return true;
    }

    private boolean isBump(Point point, Direction direction){
        return nodes[point.getY()][point.getX()].isBlocking(direction);
    }

    private Point getCalculatedPoint(Direction direction, Point point){
        int destX = point.getX();
        int destY = point.getY();

        if (direction==Direction.North){
            destY--;
        }
        if (direction==Direction.East){
            destX++;
        }
        if (direction==Direction.South){
            destY++;
        }
        if (direction==Direction.West){
            destX--;
        }

        return new Point(destX, destY);
    }

    public void run(JFrame frame) throws IOException, BumpException, InterruptedException, CleanException, InvalidEnvironmentObjectException, OutOfPowerException {
        this.jFrame = frame;

       search(sensorSimulator.getPoint());

    }

    public ControlSimulatorNode[][] getNodes(){
        return nodes;
    }

    public Point getCurrentLocation(){
        return sensorSimulator.getPoint();
    }


    public PowerLevel getPowerLevel() {
        return powerLevel;
    }
}
