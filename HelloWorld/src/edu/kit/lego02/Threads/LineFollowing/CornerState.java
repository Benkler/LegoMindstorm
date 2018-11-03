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
		nextState = new StandardLineFollowingState(thread);
	}
	
	/**
	 * Strategy: Drive forward a little bit, then rotate right until the robot doesn't see white anymore. 
	 */
    @Override
    protected void entry() {
    	Robot robot = thread.getRobot();
        Drive drive = robot.getDrive();
        
        drive.travelFwd(0.5f);
        
        boolean white = true;
        while(white) {
        	drive.turnRightSingleChain(TURNING_DEGREE); // might want to use turnInPlace here
        	white = thread.isWhite(robot.getSensorValues().getColorValue());
        }
    }	
}