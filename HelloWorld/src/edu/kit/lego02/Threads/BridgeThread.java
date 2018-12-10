package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.userIO.BrickScreen;

public class BridgeThread implements Runnable {
	
	Robot robot;
	Drive drive;
		
	// 0.4f
	// not corners: 0.05f
	// corners: 0.06f
	private static float usTargetValue;
	private static final float US_TARGET_VALUE_FIRST_PART =  0.065f;
	private static final float US_TARGET_VALUE_SECOND_PART =  0.055f;
	private static final int SWITCHING_TIME = 30000;
	// Proportional factor for P-control:
	private static final float KP = 130f;				// TODO adjust
	private static final float BASE_SPEED = 150f;
	
	
	
	private static final float US_WALL_THRESH = 0.01f;		// TODO adjust
	
	public BridgeThread(Robot robot) {
		this.robot = robot;
		this.drive = robot.getDrive();
		usTargetValue = US_TARGET_VALUE_FIRST_PART;
	}
	
    @Override
    public void run() {
//    	printSensorValues();
    	
    	robot.pointUSSensorDownward();
    	executeStartSequence();
    	crossControlled();
    	executeEndSequence();
    }
    
    private void crossControlled() {
    	
    	BrickScreen.clearScreen();
    	BrickScreen.show(" Cross controlled");
    	
    	Thread timerThread = new Thread(new Timer(SWITCHING_TIME, this));
    	//timerThread.start();
    	
    	float controlDiff;
    	float speedChange;
    	float leftMotorSpeed;
    	float rightMotorSpeed;
    	while (robot.getSensorValues().getUltrasonicValue() > US_WALL_THRESH) {
    		
    		controlDiff = robot.getSensorValues().getUltrasonicValue() - usTargetValue;
    		// controlDiff >0 : turn right
    		// controlDiff <0 : turn left
    		if (controlDiff >= 0) { 
    			leftMotorSpeed = BASE_SPEED + KP;
    			rightMotorSpeed = BASE_SPEED - KP/2;
    			//speedChange = 	KP;
    		} else {	// US sensor is looking over the edge.
    			leftMotorSpeed = BASE_SPEED - KP/2;
    			rightMotorSpeed = BASE_SPEED + KP;
    			//speedChange = - KP;
    		}
    		
    		//leftMotorSpeed = BASE_SPEED +  speedChange;
    		//rightMotorSpeed = BASE_SPEED -  speedChange;
    		
    		//BrickScreen.clearScreen();
    		//BrickScreen.show((int) leftMotorSpeed + "   " + (int) rightMotorSpeed);
    		
    		drive.changeMotorSpeed(leftMotorSpeed, rightMotorSpeed);	
    		
    		if (robot.getSensorValues().getLeftTouchValue()  > 0.9f 
    		 || robot.getSensorValues().getRightTouchValue() > 0.9f) {
    			//BrickScreen.clearScreen();
    			//BrickScreen.show("TouchSensor activated");
    			drive.stopMotors();
    			return;
    		}
    		
    		if (Thread.currentThread().isInterrupted()){
    			drive.stopMotors();
                return;
            }
    		
    	}
    }
    
    private void executeStartSequence() {
    	
    	BrickScreen.clearScreen();
    	BrickScreen.show("Start sequence");
    	
    	drive.travelFwd(13);
    	drive.turnLeftInPlace(30);
    	
//    	drive.changeMotorSpeed(100, 100);
//    	while (true) {
//    		if (robot.getSensorValues().getUltrasonicValue() >= usTargetValue) {
//    			drive.travelBwd(3);
//    			return;
//    		}
//    	}
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
    		BrickScreen.displayString("" + robot.getSensorValues().getLeftTouchValue() + 
    				"     " +  robot.getSensorValues().getRightTouchValue(), 0, 1);
    		try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}
    
    public void signalTimeout() {
    	usTargetValue = US_TARGET_VALUE_SECOND_PART;
    	BrickScreen.show("SWITCHING##############");
    }
    
//  private void handleCorner() {
//	BrickScreen.displayString("Corner", 0, 0);
//	drive.travelBwd(5);
//	drive.turnLeftInPlace(80);
//}
    
//    private void crossHardcoded() {
//       drive.travelFwd(50);
//       drive.turnLeftInPlace(90);
//       drive.travelFwd(100);
//       drive.turnLeftInPlace(90);
//       drive.travelFwd(50);
//    }

}
