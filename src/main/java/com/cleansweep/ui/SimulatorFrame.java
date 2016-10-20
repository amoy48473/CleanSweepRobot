package com.cleansweep.ui;

import com.cleansweep.ControlSimulator;
import com.cleansweep.dataobjects.ControlSimulatorNode;

import javax.swing.*;
import java.awt.*;

/**
 * Created by allenmoy on 10/11/16.
 */
public class SimulatorFrame extends JPanel{

    private ControlSimulator controlSimulator;

    public SimulatorFrame(ControlSimulator controlSimulator){
        this.controlSimulator = controlSimulator;
    }


    @Override
    public void paint(Graphics g) {

        ControlSimulatorNode[][] nodes = controlSimulator.getNodes();


        int cellWidth = getWidth() / nodes.length;
        int cellHeight = getHeight() / nodes[0].length;


        for (int i = 0; i < nodes.length; i++) {

            for (int j = 0; j < nodes[i].length; j++) {

                ControlSimulatorNode node = nodes[i][j];
                // draw top line
                if (node.isTopBlocking() ){
                    g.drawLine(j*cellWidth, i*cellHeight, (j*cellWidth+cellWidth),  i*cellHeight);
                }

                // draw bottom line
                if (node.isDownBlocking()){
                    g.drawLine(j*cellWidth, (i*cellHeight+cellHeight), j*cellWidth+cellWidth, i*cellHeight+cellHeight );  ;
                }

                // draw right line
                if (node.isRightBlocking()){
                    g.drawLine((j*cellWidth+cellWidth),  i*cellHeight, j*cellWidth+cellWidth, i*cellHeight+cellHeight);
                }

                // draw left line
                if (node.isLeftBlocking()){
                    g.drawLine(j*cellWidth, i*cellHeight, j*cellWidth, (i*cellHeight+cellHeight));

                }

                com.cleansweep.dataobjects.Point currentLocation = controlSimulator.getCurrentLocation();
                if (i==currentLocation.getY() && j==currentLocation.getX()){
                    g.setColor(Color.RED);
                    g.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                    g.setColor(Color.black);
                }

                g.drawString((node.isCleaned() ? "1" : "0"), (j * cellWidth) + ((int) (0.5 * cellWidth)), (i*cellHeight)+((int)(0.5*cellHeight)));

            }

        }

    }



}
