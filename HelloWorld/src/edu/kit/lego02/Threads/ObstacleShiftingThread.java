package edu.kit.lego02.Threads;

import java.util.ArrayList;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.userIO.BrickScreen;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;

public class ObstacleShiftingThread implements Runnable {
	
	Robot robot;
	Drive drive;
	
	private final int BLUE = 2;
	private final float COLOR_BLUE = 0.08f;
	private final float DIST_THRESH = 0.1f;
	
	public ObstacleShiftingThread(Robot robot) {
		this.robot = robot;
		drive = robot.getDrive();
	}

    @Override
    public void run() {
        BrickScreen.show("Obstacle SHifting!!");
        
        travelToStart();
//        travelToBoxSignal();
//        moveBoxToWall();
//        moveBoxToCorner();
//        leaveArea();
    }

    private void travelToStart(){
		BrickScreen.show("" + robot.getSensorValues().getColorMode());
		robot.getSensorValues().setRGB();

		float[] colorArray;
		BrickScreen.show("" + robot.getSensorValues().getColorMode());
    	//drive.turnLeftInPlace(20);
    	//drive.travelFwd(200);
    	try{
	    	//while(!(blue > COLOR_BLUE)){
	    	while(true){
	    		Thread.sleep(5);
	    		colorArray = robot.getSensorValues().getColorValueArray();
	    		BrickScreen.clearScreen();
	    		for(int i = 0; i < colorArray.length; i++){
		    		BrickScreen.show(i + " : " + colorArray[i]);	    			
	    		}
	    	}
	    	//drive.stopMotors();
    		//BrickScreen.show("Vorwärts");
    	} catch(InterruptedException e){
    	}
    	drive.travelFwd(50);	
    }
    
    private void travelToBoxSignal(){
    	float lastDistance = 0f; 
    	float distance = robot.getSensorValues().getUltrasonicValue();    	
    	drive.turnRightInPlace(90);    	
    	drive.turnRightInPlace();    	
    	try{    		
	    	while(isSinkingDistance(distance, lastDistance)){
	        	distance = robot.getSensorValues().getUltrasonicValue();
	    	    Thread.sleep(5);
	    	}
    	} catch(InterruptedException e){
    		
    	}
    	drive.stopMotors();
    	//Korrekturbewegung
    }    
    
    private boolean isSinkingDistance(float distance, float lastDistance){
    	if (distance > 40 || lastDistance - distance > 0){
    		return true;
    	}
    	return false;
    }
    
    private void moveBoxToWall(){
    	//drive.travelArc(10, 140);
    	//drive.stopMotors();
    	drive.travelFwd(200);
    }
    
    private void moveBoxToCorner(){
    	drive.travelBwd(15);
    	drive.turnRightInPlace(45);
    	drive.travelBwd(10);
    	drive.turnRightInPlace(90);
    	drive.travelFwd(30);
    	drive.turnRightInPlace(90);
    	drive.travelFwd(100);
    }
    
    private void leaveArea(){
    	
    }
}