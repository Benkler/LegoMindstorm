package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.userIO.BrickScreen;

public class BridgeThread implements Runnable {
	
	Robot robot;
	Drive drive;
		
	private static final float US_TARGET_VALUE = 0.03f;	
	// Proportional factor for P-control:
	private static final float KP = 100f;				// TODO adjust
	private static final float BASE_SPEED = 200f;
	
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
    	while (robot.getSensorValues().getUltrasonicValue() > US_WALL_THRESH) {
    		
    		if(Thread.currentThread().isInterrupted()){
    			drive.stopMotors();
                return;
            }
    		
    		//BrickScreen.clearScreen();
    		//BrickScreen.displayString("" + robot.getSensorValues().getUltrasonicValue(), 0, 0);
    		
    		controlDiff = robot.getSensorValues().getUltrasonicValue() - US_TARGET_VALUE;
    		// controlDiff >0 : turn right
    		// controlDiff <0 : turn left
    		if (controlDiff >= 0) { // US sensor is looking over the edge.
    			speedChange = 	KP;
    		} else {
    			speedChange = - KP;
    		}
    		
    		drive.changeMotorSpeed(
    				BASE_SPEED + speedChange, 
    				BASE_SPEED - speedChange);		
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
