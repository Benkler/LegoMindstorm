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
	private static final float US_TARGET_VALUE = 0.055f;	
	// Proportional factor for P-control:
	private static final float KP = 130f;				// TODO adjust
	private static final float BASE_SPEED = 150f;
	
	private static final float US_WALL_THRESH = 0.01f;		// TODO adjust
	
	public BridgeThread(Robot robot) {
		this.robot = robot;
		this.drive = robot.getDrive();
	}
	
    @Override
    public void run() {
//    	printSensorValues();
    	 	
    	robot.pointUSSensorDownward();
    	crossControlled();
    }
    
    private void crossControlled() {
    	
    	BrickScreen.clearScreen();
    	BrickScreen.displayString(" Cross controlled", 0, 0);
    	
    	float controlDiff;
    	float speedChange;
    	float leftMotorSpeed;
    	float rightMotorSpeed;
    	while (robot.getSensorValues().getUltrasonicValue() > US_WALL_THRESH) {
    		
    		controlDiff = robot.getSensorValues().getUltrasonicValue() - US_TARGET_VALUE;
    		// controlDiff >0 : turn right
    		// controlDiff <0 : turn left
    		if (controlDiff >= 0) { 
    			speedChange = 	KP;
    		} else {	// US sensor is looking over the edge.
    			speedChange = - KP;
    		}
    		
    		leftMotorSpeed = BASE_SPEED +  speedChange;
    		rightMotorSpeed = BASE_SPEED -  speedChange;
    		
    		BrickScreen.clearScreen();
    		BrickScreen.show((int) leftMotorSpeed + "   " + (int) rightMotorSpeed);
    		
    		drive.changeMotorSpeed(leftMotorSpeed, rightMotorSpeed);	
    		
    		if(Thread.currentThread().isInterrupted()){
    			drive.stopMotors();
                return;
            }
    	}
    }
    
    private void printSensorValues() {
		while(true) {
    		BrickScreen.clearScreen();
    		BrickScreen.displayString("" + robot.getSensorValues().getUltrasonicValue(), 0, 1);
    		try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
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
