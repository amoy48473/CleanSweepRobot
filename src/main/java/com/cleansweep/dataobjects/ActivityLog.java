package com.cleansweep.dataobjects;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ActivityLog{
	// create an object to log the output to a file.
	public BufferedWriter activityLog;

	
	public ActivityLog(){
		try
		{
			//Create an object to write output to a file.
			activityLog = new BufferedWriter(new FileWriter("activitylog.txt", true));

		}
		catch(Exception e)
		{
			System.out.println ("File error.");
		}
		
	}


}
