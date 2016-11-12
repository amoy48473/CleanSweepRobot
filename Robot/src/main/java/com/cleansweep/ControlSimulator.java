package com.cleansweep;

import com.cleansweep.dataobjects.*;
import com.cleansweep.enums.Direction;
import com.cleansweep.enums.FloorType;
import com.cleansweep.exceptions.*;
import com.cleansweep.services.EnergyCalculationService;
import com.cleansweep.services.EnergyCalculationServiceImpl;
import com.cleansweep.ui.FrameListener;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

/**
 * Created by allenmoy on 10/10/16.
 * Control simulator class that represents the Robot Object
 */
public class ControlSimulator implements FrameListener{

    private int delayTime;

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

    public boolean returningToChargingStation = false;

    private Point travelingTo;

 
    public ControlSimulator(SensorSimulator sensorSimulator, int delayTime){
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

        this.delayTime = delayTime;
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
    private void search(Point root) throws IOException, BumpException, InterruptedException, CleanException, InvalidEnvironmentObjectException, OutOfPowerException, CapacityFullException {

        // Check if current location is a charging station, if it is, then add it to the list
        if (sensorSimulator.isChargingStation() && !chargingStations.contains(root)){

            chargingStations.add(root);
            nodes[root.getY()][root.getX()].setChargingStation(true);
        }


        if (root == null) return;

        // Set this point as visited
        ControlSimulatorNode rootNode =  nodes[root.getY()][root.getX()];
        rootNode.setVisited(true);



        // For every neighbor
        for (Direction direction : Direction.values()){
            Point calculatedPoint = getCalculatedPoint(direction, root);

            // if point is not blocked and we have not cleaned there, the depth first search
            if (sensorSimulator.getNavigationSensor(direction)){
                activityLogData.activityLog.write(new Date() + " Checking directional sensor: " + 
                		(sensorSimulator.getNavigationSensor(direction) ? direction + " Direction: Clear Path" : direction + " Direction: Blocked Path"));
                activityLogData.activityLog.flush();
                activityLogData.activityLog.newLine(); 

                ControlSimulatorNode nextNode = nodes[root.getY()][root.getX()];
                nextNode.setOpen(direction);

                nodes[calculatedPoint.getY()][calculatedPoint.getX()].setFloorType(sensorSimulator.getSurface(direction));


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

            destPoint = stack.pop();

            if (nodes[destPoint.getY()][destPoint.getX()].isCleaned() &&
                    nodes[destPoint.getY()][destPoint.getX()].isVisited() ) continue;

            travelingTo = destPoint;
            List<Direction> path = bfsToDestination(getCurrentLocation(), destPoint);

            traverseDirections(path);


            // Logic to charge the robot if it is looking for charging station and it is on top of a charging station
            if (returningToChargingStation && nodes[getCurrentLocation().getY()][getCurrentLocation().getX()].isChargingStation()){

                // If tray needs emptying, them empty
                if (emptyMeIndicator.getEmptyMeIndicator()){
                    empty();
                }

                // If battery needs charging, then charge
                if (powerLevel.isPowerLow()){
                    recharge();
                }


            }

            search(sensorSimulator.getPoint());



        }

    }

    private void recharge() throws IOException, InterruptedException {
        powerLevel.charge(this);
        activityLogData.activityLog.write("Recharging battery");

        returningToChargingStation = false;
    }

    private void empty() throws InterruptedException {
        dirtCapacity.emptyDirtTray(this);
        emptyMeIndicator.turnOffEmptyMeIndicator();

        returningToChargingStation = false;

    }

    /**
     * Method determines whether the robot needs to return to the charging station, returns null if it is not needed,
     * will return the point of the charging station otherwise
     * @param currentPowerLevel
     * @param currentLocation
     * @return
     */
    private List<Direction> getPathToClosestChargingStationIfNecessary(PowerLevel currentPowerLevel, Point currentLocation) {
        List<Direction> path = null;
        double leastEnergy = Double.POSITIVE_INFINITY;

        // Find the closest charging station that the robot knows about that requires the least energy
        for(Point chargingStation: chargingStations){
            double energyRequired = 0;


            List<Direction> directions = bfsViaKnownLocations(currentLocation, chargingStation);
            Point tempCurrentLocation = new Point(currentLocation.getX(), currentLocation.getY());
            Point tempNextLocation;

            for (Direction direction: directions){


                // Simulate the shortest path and check the least amount of energy to get there.
                if (direction == Direction.North){
                    tempNextLocation = new Point(tempCurrentLocation.getX(), tempCurrentLocation.getY()-1);
                }
                else if (direction == Direction.East){
                    tempNextLocation = new Point(tempCurrentLocation.getX()+1, tempCurrentLocation.getY());
                }
                else if (direction == Direction.South){
                    tempNextLocation = new Point(tempCurrentLocation.getX(), tempCurrentLocation.getY()+1);

                }
                else {
                    tempNextLocation = new Point(tempCurrentLocation.getX()-1, tempCurrentLocation.getY());
                }

                double moveEnergy = energyCalculationService.calculateEnergyRequiredToTraverseFloor(nodes[tempCurrentLocation.getY()][tempCurrentLocation.getX()],
                        nodes[tempNextLocation.getY()][tempNextLocation.getX()]);


                energyRequired += moveEnergy;

                tempCurrentLocation = tempNextLocation;
            }

            if (energyRequired < leastEnergy){
                leastEnergy = energyRequired;
                path = directions;
            }

        }

        if (path == null) return null;


        // If returning takes the robot to below the critical power level, return at once
            if (currentPowerLevel.getPowerLevel() - leastEnergy <= PowerLevel.CRITICAL_POWER_LEVEL ||
                    emptyMeIndicator.getEmptyMeIndicator()){

            return path;
        }

        return null;
    }

    private void moveSensorSimulator(SensorSimulator sim, Direction direction) throws InterruptedException, BumpException, InvalidEnvironmentObjectException, IOException, OutOfPowerException {
        Thread.sleep(getDelayTime());


        activityLogData.activityLog.write(new Date() + " Updating power level: " + powerLevel.getPowerLevel() +
                (powerLevel.isPowerLow() ? "; Power Level is Low!" : "; Power Level is OK."));
        activityLogData.activityLog.flush();
        activityLogData.activityLog.newLine();

        sensorSimulator.move(direction);



        // Update floor counter
        FloorType currentFloorType = sensorSimulator.getCurrentSurface();
        // Update the power level
        powerLevel.updatePowerLevel(currentFloorType);


        jFrame.repaint();



    }

    /**
     * The robot knows about its destination, give start and finish and it will try
     * to traverse these with the shortest path
     * @param current
     * @param finish
     */
    public List<Direction> bfsToDestination(Point current, Point finish){
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

    /**
     * The robot knows about its location that it is traveling through, give start and finish and it will try
     * to traverse these with the shortest path by taking a safe route that doesn't cause it to crash
     * @param current
     * @param finish
     */
    public List<Direction> bfsViaKnownLocations(Point current, Point finish){
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
                            && nodes[nextPoint.getY()][nextPoint.getX()].isVisited()
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

    /**
     * Get Revversal of a direction
     * @param direction
     * @return
     */
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

    /**
     * Checks whether a coordinate is out of bounds
     * @param nextPoint
     * @return
     */
    private boolean notOutOfBounds(Point nextPoint) {
        return !(nextPoint.getX() == sensorSimulator.getWidth()
                || nextPoint.getX() < 0 || nextPoint.getY() == sensorSimulator.getHeight()
                || nextPoint.getY() < 0);
    }

    /**
     * Checks whether the direction from the specified location is blocked
     * @param point
     * @param direction
     * @return
     */
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


    /**
     * The Run method that makes the robot automatically roam the roam until it is finished cleaning the whole room
     * @param frame
     * @throws IOException
     * @throws BumpException
     * @throws InterruptedException
     * @throws CleanException
     * @throws InvalidEnvironmentObjectException
     * @throws OutOfPowerException
     */
    public void run(JFrame frame) throws IOException, BumpException, InterruptedException, CleanException, InvalidEnvironmentObjectException, OutOfPowerException, CapacityFullException {
        this.jFrame = frame;

       search(sensorSimulator.getPoint());

    }

    /**
     * Takes in a path and executes the movements on the robot
     * @param path
     * @throws InterruptedException
     * @throws InvalidEnvironmentObjectException
     * @throws IOException
     * @throws OutOfPowerException
     * @throws BumpException
     * @throws CleanException
     */
    private void traverseDirections(List<Direction> path) throws InterruptedException, InvalidEnvironmentObjectException, IOException, OutOfPowerException, BumpException, CleanException, CapacityFullException {

        for (Direction direction: path){


            // If not already returning to charging station.
            if (!returningToChargingStation) {

                // For each move, check to make sure that robot has enough energy to return to charging station, else return immediately and store current destination for future
                List<Direction> pathToChargingStation = getPathToClosestChargingStationIfNecessary(this.getPowerLevel(), getCurrentLocation());
                if (pathToChargingStation != null) {


                    // Put destination back in the stack so when done charging, robot will first return there
                    stack.push(travelingTo);

                    path = pathToChargingStation;
                    returningToChargingStation = true;

                    traverseDirections(path);

                    return;
                }
            }



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

                // If empty me indicate is not on, then continue cleaning, else turn off cleaning function
                if (!emptyMeIndicator.getEmptyMeIndicator()) {
                    clean();
                }



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
    }

    public void clean() throws CleanException, CapacityFullException {

        // Cannot clean, throw an exception
        if (emptyMeIndicator.getEmptyMeIndicator()) {
            throw new CapacityFullException();
        }

        sensorSimulator.clean();
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

    public DirtCapacity getDirtCapacity() {
        return dirtCapacity;
    }

    public EmptyMeIndicator getEmptyMeIndicator() {
        return emptyMeIndicator;
    }


    public void updatePanel() {
        jFrame.repaint();
    }

    public int getDelayTime() {
        return delayTime;
    }
}
