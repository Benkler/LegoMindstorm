package edu.kit.lego02.Threads;

import java.util.LinkedList;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.userIO.BrickScreen;

public class BridgeThread implements Runnable {
	
	Robot robot;
	Drive drive;
		
	// last: 0.067f
	// measured: 0.067f
	// zu weit rechts -> größer
	private static float usTargetValue =  0.08f;
	// Proportional factor for P-control: (try 130 if it doesnt work)
	private static final float KP = 180f;				// TODO adjust
	private static final float BASE_SPEED = 100f;
	private static final int CONTROL_DIFF_HIST_SIZE = 20;
	
	private static final float blackTapeTresh = 0.06f;
	
	private static boolean beforeFirstCorner = true;
	private static boolean endReached = false;
	
	private LinkedList<Float> controlDiffHistory = new LinkedList<Float>();
	
	public BridgeThread(Robot robot) {
		this.robot = robot;
		this.drive = robot.getDrive();
		//robot.getSensorValues().setColorMode("Red");
	}
	
    @Override
    public void run() {
    	
    	robot.pointUSSensorSkew();
    	
//    	printSensorValues();
    	
//    	executeStartSequence();
    	crossControlled();
//    	executeEndSequence();
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
    		controlDiff = usTargetValue - usValue;
    		controlDiffHistory.addLast(new Float(Math.signum(controlDiff)));
    		if (controlDiffHistory.size() > CONTROL_DIFF_HIST_SIZE) {
    			controlDiffHistory.removeFirst();
    		}
    		controlDiff = getHistoryAvg();
    		motorSpeedShift = KP * controlDiff;
    		
    		// controlDiff <0 : turn right
    		// controlDiff >=0 : turn left
    		//BrickScreen.clearScreen();
    		BrickScreen.displayString("US " + usValue + " CD " + controlDiff , 0, 1);
    		
    		if (controlDiff < 0) { 
    			// turn right
    			
    			leftMotorSpeed = BASE_SPEED - motorSpeedShift;
    			rightMotorSpeed = BASE_SPEED + (motorSpeedShift /*/ 2f*/); //KP/2
    			//BrickScreen.displayString("RIGHT " + controlDiff, 0, 1);
    			
    		} else {	
    			// turn left
    			
    			leftMotorSpeed = BASE_SPEED - (motorSpeedShift /*/ 2f*/); //KP/2
    			rightMotorSpeed = BASE_SPEED + motorSpeedShift; 
    			//BrickScreen.displayString("LEFT " +  controlDiff, 0, 1);
    		}
    		
    		drive.changeMotorSpeed(leftMotorSpeed, rightMotorSpeed);
    		
    		//checkForStateChange();
    		
    		try {
				Thread.sleep(50);
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
    	drive.travelFwd(17f);
		drive.turnLeftInPlace(90);
		drive.travelFwd(20f);
		drive.turnLeftInPlace(20);
		findEdge();
    }
    
    private void executeStartSequence() {
    	BrickScreen.clearScreen();
    	BrickScreen.show("Start sequence");
    	
    	drive.travelFwd(16);
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
    	drive.turnLeftInPlace(25);
    	
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
