package com.cleansweep.dataobjects;

public class EmptyMeIndicator {
	
	private boolean emptyMeIndicator;
	
	public EmptyMeIndicator(){
		this.emptyMeIndicator = false;
	}
	
    public void turnOnEmptyMeIndicator(){
    	emptyMeIndicator = true;
    }
    
    public void turnOffEmptyMeIndicator(){
    	emptyMeIndicator = false;
    }
    
    public boolean getEmptyMeIndicator(){
    	return emptyMeIndicator;
    }
    
    public void setEmptyMeIndicator(boolean emptyMe){
    	emptyMeIndicator = emptyMe;
    }
    

}
