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
        BrickScreen.clearScreen();
        BrickScreen.show("Obstacle SHifting!!");
        
        
        boolean temp = true;
       // while(temp);
        
        travelToWall();
        travelToBox();
        moveBoxToCorner();
        leaveArea();
    }

    private void travelToWall(){
		BrickScreen.show("" + robot.getSensorValues().getColorMode());
		robot.getSensorValues().setRGB();
		robot.pointUSSensorForward();

		drive.turnLeftInPlace(30);
		drive.travelFwd(15);
		drive.turnRightInPlace(30);
		float[] colorArray;
		float distance = 5.0f; //distance to Wall
    	float difference = (robot.getSensorValues().getUltrasonicValue() * 100) - distance; //meter to centimeter
    	float blue = robot.getSensorValues().getColorValueArray()[2];
    	while (!(robot.getSensorValues().getLeftTouchValue() == 1)){
    		driveAlongWall(600.0f, difference);
        	difference = (robot.getSensorValues().getUltrasonicValue() * 100) - distance; //meter to centimeter
        	//blue = robot.getSensorValues().getColorValueArray()[2];
    	}
    	drive.stopMotors();
    	drive.travelBwd(8);
    	drive.turnInPlace(175); //170 Batterie
    }
      
    private void travelToBox(){
    	float distance = 0;
    	for(int i = 0; i < 20; i++){
    		distance += robot.getSensorValues().getUltrasonicValue();
    	}
    	distance = distance * 5;
    	float difference;
    	float adistance = robot.getSensorValues().getUltrasonicValue() * 100;
    	while (adistance > 35.0f){
        	difference = ((robot.getSensorValues().getUltrasonicValue() * 100) - distance); //meter to centimeter
    		driveAlongWall(300.0f, difference);
        	adistance = robot.getSensorValues().getUltrasonicValue() * 100;
    	}
    	drive.stopMotors();
    	drive.travelFwd(17);
    	drive.turnLeftInPlace(85); //260 Batterie
    }
    private void moveBoxToCorner(){	//drive.travelFwd(70);
    	
    	//while (!(robot.getSensorValues().getLeftTouchValue() == 1)){
        //	drive.changeMotorSpeed(400, 400);
    	//}
    	drive.travelFwd(70); //Zur Wand fahren
    	drive.travelBwd(7);
    	drive.turnRightInPlace(85);//85 Batterie
    	drive.travelBwd(30);
    	drive.turnLeftInPlace(85);//85 Batterie
    	while (!(robot.getSensorValues().getLeftTouchValue() == 1)){
        	drive.changeMotorSpeed(500, 500);
    	}
    	drive.travelBwd(3);
    	drive.turnRightInPlace(85); //85 Batterie
    	float distance = 4.0f;
    	float difference;
    	drive.travelFwd(69);
//    	while (!(robot.getSensorValues().getLeftTouchValue() == 1)){
//        	difference = (robot.getSensorValues().getUltrasonicValue() * 100) - distance; //meter to centimeter
//    		driveAlongWall(300.0f, difference);
//    	}
    }
    private void leaveArea(){
    	drive.travelBwd(8);
    	drive.turnRightInPlace(85); //85 Batterie
    	drive.travelFwd(27);
    	drive.turnLeftInPlace(85);//85 Batterie
    	drive.travelFwd(35);
    }

    private void driveAlongWall(float velocity, float difference){
    	final float proportional = 100.0f;
    	final float pdifference = difference * proportional;
    	float leftSpeed;
    	float rightSpeed;
    	//if (Math.abs(pdifference) > 0.4 * velocity){
    		if (pdifference > 0) {
    			leftSpeed = velocity - 60;
    			rightSpeed = velocity + 60;
    		} else {
    			leftSpeed = velocity + 60;
    			rightSpeed = velocity - 60;    			
    		}
    	//} else { 
    	//	leftSpeed = velocity - (pdifference);
    	//	rightSpeed = velocity + (pdifference);
    	//}
    	drive.changeMotorSpeed(leftSpeed, rightSpeed);
    }    
}