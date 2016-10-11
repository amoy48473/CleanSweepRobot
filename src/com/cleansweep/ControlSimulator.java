package com.cleansweep;

import com.cleansweep.dataobjects.Point;
import com.cleansweep.enums.Direction;
import com.cleansweep.exceptions.BumpException;
import com.cleansweep.exceptions.CleanException;

import java.util.Stack;

/**
 * Created by allenmoy on 10/10/16.
 */
public class ControlSimulator {


    private SensorSimulator sensorSimulator;

    private Stack<Point> stack  = new Stack<Point>();

    private boolean[][] fullyCleaned;

    public ControlSimulator(SensorSimulator sensorSimulator){
        this.sensorSimulator = sensorSimulator;

        stack.add(sensorSimulator.getPoint());
        fullyCleaned = new boolean[sensorSimulator.getHeight()][sensorSimulator.getWidth()];
    }

    private Stack<Direction> search(Point current) throws BumpException, InterruptedException, CleanException {
        Stack<Direction> path = new Stack<>();

        for (Direction direction: Direction.values()){


            if (sensorSimulator.getNavigationSensor(direction)){
                Point newPoint = sensorSimulator.getPoint();

                if (!fullyCleaned[newPoint.getY()][newPoint.getX()]) {


                    sensorSimulator.move(direction);
                    if (sensorSimulator.getDirtSensor()) {
                        sensorSimulator.clean();
                    } else {
                        fullyCleaned[sensorSimulator.getPoint().getY()][sensorSimulator.getPoint().getX()] = true;
                    }

                    path.push(direction);

                    Stack<Direction> returnStack = search(sensorSimulator.getPoint());


                    path.addAll(returnStack);





                    // Return backwards to origin
                    System.out.println("Returning...");
                    while ( returnStack.peek() !=null){
                        Direction returnDirection = returnStack.pop();
                        sensorSimulator.reverse(returnDirection);
                    }
                    System.out.println("Done Returning...");

                }
            }

        }

        return path;

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

    public void run() throws BumpException, InterruptedException, CleanException {

       search(sensorSimulator.getPoint());
    }

}
