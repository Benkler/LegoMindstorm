package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowingThread;

public class CornerState extends LineFollowingState {
	
	private final int TURNING_DEGREE = 3;

	public CornerState(LineFollowingThread thread) {
		super(thread);
	}
	
	@Override
    public void grey() {
		// TODO
		nextState = new StandardLineFollowingState(lineFollowThread);
	}
	
	/**
	 * Strategy: Drive forward a little bit, then rotate right until the robot doesn't see white anymore. 
	 */
    @Override
    protected void entry() {
        
    	Robot robot = lineFollowThread.getRobot();
        Drive drive = robot.getDrive();
        
        drive.stopMotors();
        
        drive.travelBwd(0.3f);
       // drive.travelFwd(0.5f);
        
        boolean white = true;
        drive.turnRightInPlace(90);
        while(white) {
            if(Thread.currentThread().isInterrupted()){
                return;
            }
        	//drive.turnRightInPlace(TURNING_DEGREE); // might want to use turnInPlace here
        	white = lineFollowThread.isWhite(robot.getSensorValues().getColorValue());
        }
    }	
}