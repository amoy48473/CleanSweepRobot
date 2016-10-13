package com.cleansweep;

import com.cleansweep.dataobjects.ControlSimulatorNode;
import com.cleansweep.dataobjects.Point;
import com.cleansweep.enums.Direction;
import com.cleansweep.exceptions.BumpException;
import com.cleansweep.exceptions.CleanException;

import javax.swing.*;
import java.util.*;
import java.util.List;

/**
 * Created by allenmoy on 10/10/16.
 */
public class ControlSimulator{


    private SensorSimulator sensorSimulator;

    private Stack<Point> stack  = new Stack<Point>();

    private JFrame jFrame;



    private ControlSimulatorNode[][] nodes;

    public ControlSimulator(SensorSimulator sensorSimulator){
        this.sensorSimulator = sensorSimulator;

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
    private void search(Point root) throws BumpException, InterruptedException, CleanException {

        if (root == null) return;

        // Set this point as visited
        nodes[root.getY()][root.getX()].setVisited(true);


        // For every neighbor
        for (Direction direction : Direction.values()){
            Point calculatedPoint = getCalculatedPoint(direction, root);

            // if point is not blocked and we have not visited there, the depth first search
            if (sensorSimulator.getNavigationSensor(direction)){

                nodes[root.getY()][root.getX()].setOpen(direction);

                if (!nodes[calculatedPoint.getY()][calculatedPoint.getX()].isVisited()){
                    stack.push(calculatedPoint);
                }
            } else if (!sensorSimulator.getNavigationSensor(direction)){
                nodes[root.getY()][root.getX()].setBlocking(direction);
            }


        }

        while (!stack.isEmpty()){
            Point destPoint = stack.pop();
            if (nodes[destPoint.getY()][destPoint.getX()].isVisited()) continue;

            List<Direction> path = bfsToDestination(getCurrentLocation(), destPoint);



            for (Direction direction: path){
                moveSensorSimulator(sensorSimulator, direction);

                // If there is dirt here then clean it
                if (sensorSimulator.getDirtSensor()) {
                    sensorSimulator.clean();
                }
            }

            search(sensorSimulator.getPoint());
        }

    }

    private void moveSensorSimulator(SensorSimulator sim, Direction direction) throws InterruptedException, BumpException {
        Thread.sleep(500);

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

    public void run(JFrame frame) throws BumpException, InterruptedException, CleanException {
        this.jFrame = frame;

       search(sensorSimulator.getPoint());
    }

    public ControlSimulatorNode[][] getNodes(){
        return nodes;
    }

    public Point getCurrentLocation(){
        return sensorSimulator.getPoint();
    }




}
