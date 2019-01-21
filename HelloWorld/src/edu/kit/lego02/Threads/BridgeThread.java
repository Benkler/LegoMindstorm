package edu.kit.lego02.Threads;

import java.util.LinkedList;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.userIO.BrickScreen;

public class BridgeThread implements Runnable {
	
	Robot robot;
	Drive drive;
	// last: 0.13f
	// measured: 0.067f
	// zu weit rechts -> größer
	private static float usTargetValue =  0.13f; // gut: 0.22f
	// Proportional factor for P-control:
	private static final float KP = 130f;	// 180f
	private static final float KI = 0.15f;	// between 0 and 1! // 0.25f
	private static final float BASE_SPEED = 120f;
	private static final int CONTROL_DIFF_HIST_SIZE = 120;
	
	private static final float blackTapeTresh = 0.06f;
	
	private static boolean beforeFirstCorner = true;
	private static boolean endReached = false;
	
	private LinkedList<Float> controlDiffHistory = new LinkedList<Float>();
	
	public BridgeThread(Robot robot) {
		this.robot = robot;
		this.drive = robot.getDrive();
	}
	
    @Override
    public void run() {
    	
    	robot.pointUSSensorSkew();
    	
//    	printSensorValues();
    	
    	executeStartSequence();
    	crossControlled();
    	executeEndSequence();
    }
    
    private void crossControlled() {
    	BrickScreen.clearScreen();
    	BrickScreen.show(" Cross controlled");
    	
    	float controlDiff;
    	float usValue;
    	float motorSpeedShift;
    	float leftMotorSpeed;
    	float rightMotorSpeed;
    	
    	while (!endReached) {
    		
    		usValue = robot.getUltrasonicValue();
    		controlDiff = Math.signum(usTargetValue - usValue);
    		controlDiffHistory.addLast(new Float(controlDiff));
    		if (controlDiffHistory.size() > CONTROL_DIFF_HIST_SIZE) {
    			controlDiffHistory.removeFirst();
    		}
    		motorSpeedShift = KP * ((1f - KI) * controlDiff + KI * getHistoryAvg());
    		if (usValue > 1.5f) {
    			motorSpeedShift = 100;
    		}
    		
    		// controlDiff <0 : turn right
    		// controlDiff >=0 : turn left
    		//BrickScreen.clearScreen();
    		BrickScreen.displayString("US " + usValue + " CD " + controlDiff , 0, 1);
    		
    		if (motorSpeedShift < 0) { 
    			// turn right
    			leftMotorSpeed = BASE_SPEED - motorSpeedShift;
    			rightMotorSpeed = BASE_SPEED + (motorSpeedShift /*/ 2f*/);
    			
    		} else {	
    			// turn left
    			leftMotorSpeed = BASE_SPEED - (motorSpeedShift /*/ 2f*/);
    			rightMotorSpeed = BASE_SPEED + motorSpeedShift; 
    		}
    		
    		drive.changeMotorSpeed(leftMotorSpeed, rightMotorSpeed);
    		
    		checkForStateChange();
    		
    		try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				drive.stopMotors();
    			robot.pointUSSensorForward();
                return;
			}
    	}
    	
    	BrickScreen.clearScreen();
		BrickScreen.show("END");	
    }
    
    private float getHistoryAvg() {
    	float sum = 0f;
    	for (Float current : controlDiffHistory) {
			sum += current.floatValue();
		}
    	return sum / ((float) controlDiffHistory.size());
    }
    
    private void checkForStateChange() {
    	if (beforeFirstCorner) {
			if (robot.getSensorValues().getColorValue() < blackTapeTresh) {
				BrickScreen.clearScreen();
				BrickScreen.show("First corner detected");
				
				executeFirstCornerSequence();
				//usTargetValue = 0.13f;
				beforeFirstCorner = false;
			}
		} else {
			if (robot.getSensorValues().getLeftTouchValue()  > 0.9f 
					|| robot.getSensorValues().getRightTouchValue() > 0.9f) {
				drive.stopMotors();
				endReached = true;
				return;
			}
		}
    }
    
    private void executeFirstCornerSequence() {
    	drive.travelFwd(20f);
		drive.turnLeftInPlace(80);
		drive.travelFwd(20f);
		drive.turnLeftInPlace(20);
		findEdge();
    }
    
    private void executeStartSequence() {
    	BrickScreen.clearScreen();
    	BrickScreen.show("Start sequence");
    	
    	drive.travelFwd(25);
    	drive.turnLeftInPlace(20);
    	findEdge();
    }
    
    private void findEdge() {
    	drive.stopMotors();
    	drive.changeMotorSpeed(150, 150);
    	
    	while (true) {
    		if (robot.getSensorValues().getUltrasonicValue() >= usTargetValue) {
    			drive.stopMotors();
    			return;
    		}
    	}
    }
    
    private void executeEndSequence() {
    	BrickScreen.clearScreen();
    	BrickScreen.show("End sequence");
    	
    	drive.travelBwd(8);
    	drive.turnRightInPlace(25);
    	drive.travelFwd(8);
    	//drive.turnLeftInPlace(25);
    	
    	// Find blue line
//    	drive.travelFwdAsynchronous(50);
//    	while(true) {
//    		// TODO check for blue line via color sensor
//    	}
    }
    
    private void printSensorValues() {
		while(true) {
    		BrickScreen.clearScreen();
    		BrickScreen.displayString("" + robot.getUltrasonicValue(), 0, 1);
    		try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}
}
