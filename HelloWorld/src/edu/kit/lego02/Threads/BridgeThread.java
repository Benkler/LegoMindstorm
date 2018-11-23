package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.userIO.BrickScreen;

public class BridgeThread implements Runnable {
	
	Robot robot;
	Drive drive;
	
	private static final int US_SENSOR_ANGLE = 45;		// TODO adjust
	private static final float US_TARGET_VALUE = 0.2f;	// TODO adjust
	// Proportional factor for P-control:
	private static final float KP = 0.2f;				// TODO adjust
	private static final float BASE_SPEED = 220f;
	// Used as control difference if the US sensor looks over the edge of the bridge:
	private static final float SUBSTITUTE_CONTROL_DIFF = -5f;	// TODO adjust
	
	private static final float US_WALL_THRESH = 0.1f;		// TODO adjust
	private static final float MAX_DIST_TO_BLUE_LINE = 20f; // TODO adjust
	private static final int BLUE_COLOR_ID = 2; 				// TODO adjust
	
	public BridgeThread(Robot robot) {
		this.robot = robot;
		this.drive = robot.getDrive();
	}
	
    @Override
    public void run() {
    	crossHardcoded();
    	//crossControlled();
    }
    
    private void crossControlled() {
    	
    	robot.adjustUSAngle(US_SENSOR_ANGLE);
    	
    	float controlDiff;
    	float scaledControlDiff;
    	while (robot.getSensorValues().getUltrasonicValue() > US_WALL_THRESH) {
    		controlDiff = US_TARGET_VALUE - robot.getSensorValues().getUltrasonicValue();
    		// controlDiff >0 : turn right
    		// controlDiff <0 : turn left
    		if (controlDiff < 0) { // US sensor is looking over the edge.
    			controlDiff = SUBSTITUTE_CONTROL_DIFF;
    		}
    		
    		scaledControlDiff = KP * controlDiff;
    		
    		drive.changeMotorSpeed(
    				BASE_SPEED + scaledControlDiff, 
    				BASE_SPEED - scaledControlDiff);
    	}
    	
    	robot.adjustUSAngle(0);
    	
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
