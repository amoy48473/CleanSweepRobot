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

                // TODO: Work in progress
                ControlSimulatorNode node = nodes[i][j];
                // draw top line
                if (node.isTopBlocking() || true){
                    //g.drawLine(i*cellHeight, j*cellWidth, j*cellHeight, (j*cellHeight)+cellHeight);
                }

                // draw bottom line
                if (node.isDownBlocking() || true){

                }

                // draw right line
                if (node.isRightBlocking() || true){

                }

                // draw left line
                if (node.isLeftBlocking() || true){

                }

                //g.drawRect(j*cellWidth, i*cellHeight, cellWidth, cellHeight);
                g.drawString((node.isVisited() ? "1" : "0"), j*cellWidth, i*cellHeight);
            }

        }

    }



}
