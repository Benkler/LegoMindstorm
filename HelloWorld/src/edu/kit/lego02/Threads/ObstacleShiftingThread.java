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
	private final float COLOR_BLUE = 0.09f;
	private final float DIST_THRESH = 0.35f;
	
	public ObstacleShiftingThread(Robot robot) {
		this.robot = robot;
		drive = robot.getDrive();
	}

    @Override
    public void run() {
        BrickScreen.show("Obstacle SHifting!!");
        
        travelToStart();
        moveAlongBox();
//        travelToBoxSignal();
//        moveBoxToWall();
//        moveBoxToCorner();
//        leaveArea();
    }

    private void travelToStart(){
		robot.getSensorValues().setRGB();

		float[] colorArray;
    	drive.turnLeftInPlace(20);
    	drive.changeMotorSpeed(800, 800);
		colorArray = robot.getSensorValues().getColorValueArray();
		float blue = colorArray[BLUE];
    	while(!(blue > COLOR_BLUE)){
    		blue = (robot.getSensorValues().getColorValueArray())[BLUE];
    	}
    	drive.stopMotors();
    }
    
    private void moveAlongBox() {
    	
    	robot.getDrive().turnRightInPlace(180);
    	robot.pointUSSensorForward();
    	drive.travelBwd(30);
    	drive.changeMotorSpeed(-500, -500);
    	BrickScreen.clearScreen();
    	BrickScreen.show("" + robot.getSensorValues().getUltrasonicValue());
    	while(robot.getSensorValues().getUltrasonicValue() > DIST_THRESH) {    		
    	}
    	drive.stopMotors();
    	drive.travelBwd(9);
    	drive.turnLeftInPlace(90);
    	drive.changeMotorSpeed(300, 300);    	
 
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