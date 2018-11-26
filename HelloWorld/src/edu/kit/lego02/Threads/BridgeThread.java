package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.userIO.BrickScreen;
import lejos.hardware.Button;

public class BridgeThread implements Runnable {
	
	Robot robot;
	Drive drive;
	
	//private static final int US_SENSOR_ANGLE = 45;		
	private static final float US_TARGET_VALUE = 0.08f;	// TODO adjust
	// Proportional factor for P-control:
	private static final float KP = 1000f;				
	private static final float BASE_SPEED = 220f;
	// Used as control difference if the US sensor looks over the edge of the bridge:
	private static final float SUBSTITUTE_CONTROL_DIFF = 0.03f;	
	
	private static final float US_WALL_THRESH = 0.01f;		// TODO adjust
	private static final float MAX_DIST_TO_BLUE_LINE = 20f; // TODO adjust
	private static final int BLUE_COLOR_ID = 2; 				// TODO adjust
	
	public BridgeThread(Robot robot) {
		this.robot = robot;
		this.drive = robot.getDrive();
	}
	
    @Override
    public void run() {
//    	robot.adjustUSAngle(45);
//    	try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	robot.adjustUSAngle(0);
    	
//    	while(true) {
//    		BrickScreen.clearScreen();
//    		BrickScreen.displayString("" + robot.getUSValue(), 0, 1);
//    		try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    	}
    	
    	//crossHardcoded();
    	crossControlled();
    }
    
    private void crossControlled() {
    	
    	robot.pointUSSensorDownward();
    	
    	float controlDiff;
    	float scaledControlDiff;
    	while (robot.getSensorValues().getUltrasonicValue() > US_WALL_THRESH) {
    		
    		if(Thread.currentThread().isInterrupted()){
    			drive.stopMotors();
                return;
            }
    		
    		BrickScreen.clearScreen();
    		BrickScreen.displayString("" + robot.getSensorValues().getUltrasonicValue(), 0, 0);
    		
    		controlDiff = robot.getSensorValues().getUltrasonicValue() - US_TARGET_VALUE;
    		// controlDiff >0 : turn left
    		// controlDiff <0 : turn right
    		if (controlDiff > 0) { // US sensor is looking over the edge.
    			controlDiff = SUBSTITUTE_CONTROL_DIFF;
    		}
    		
    		scaledControlDiff = KP * controlDiff;
    		
    		drive.changeMotorSpeed(
    				BASE_SPEED - scaledControlDiff, 
    				BASE_SPEED + scaledControlDiff);
    	}
    	
    	
    	drive.travelFwdAsynchronous(MAX_DIST_TO_BLUE_LINE);
    	while (robot.getColorID() != BLUE_COLOR_ID) {
    		if(Thread.currentThread().isInterrupted()){
                return;
            }
    	}
    }
    
    private void crossHardcoded() {
       drive.travelFwd(50);
       drive.turnLeftInPlace(90);
       drive.travelFwd(100);
       drive.turnLeftInPlace(90);
       drive.travelFwd(50);
    }

}
