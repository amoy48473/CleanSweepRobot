package com.cleansweep.dataobjects;

import com.cleansweep.enums.DirtCapacityStatus;

public class DirtCapacity {
    private int dirtCapacity;
    private DirtCapacityStatus dirtCapacityStatus;
    public static  final int MAX_DIRT_CAPACITY = 50;
    
    public DirtCapacity(){
    	this.dirtCapacity = 0;
    	dirtCapacityStatus = DirtCapacityStatus.Empty;
    }
    
    
    public void updateDirtCapacity()
    {
    	dirtCapacity++;
    	updateDirtCapacityStatus();
    }
    
    public int getDirtCapacity(){
    	return dirtCapacity;
    }
    
    public boolean isMaxDirtCapacity(){
    	return dirtCapacity >= MAX_DIRT_CAPACITY;
    }
    
    public int getMaxDirtCapacity(){
    	return MAX_DIRT_CAPACITY;
    }
    
    public void setDirtCapacity(int dirtCap){
    	dirtCapacity = dirtCap;
    	updateDirtCapacityStatus();
    }
    
    public void emptyDirtTray(){
    	dirtCapacity = 0;
    	updateDirtCapacityStatus();
    }
    
    public boolean isDirtTrayEmpty(){
    	return dirtCapacity == 0;
    }
    
    public void setDirtCapacityStatus(DirtCapacityStatus dirtCapStatus){
    	dirtCapacityStatus = dirtCapStatus;
    }
    
    public DirtCapacityStatus getDirtCapacityStatus(){
    	return dirtCapacityStatus;
    }
    
    public void updateDirtCapacityStatus(){
    	if(isMaxDirtCapacity()){
        	dirtCapacityStatus = DirtCapacityStatus.Full;
    	}else if(dirtCapacity > 0 && dirtCapacity < MAX_DIRT_CAPACITY){
        	dirtCapacityStatus = DirtCapacityStatus.NotFull;
    	}else{
        	dirtCapacityStatus = DirtCapacityStatus.Empty;
    	}
    }


}
