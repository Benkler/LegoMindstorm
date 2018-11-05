package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowingThread;

public class BlueLineState extends LineFollowingState {

	public BlueLineState(LineFollowingThread thread) {
		super(thread);
	}

	@Override
	protected void entry() {
		Robot robot = lineFollowThread.getRobot();
        Drive drive = robot.getDrive();
        
        drive.stopMotors();
        
        drive.travelFwd(5.0f);
        
        // TODO call controller.stateChanged to switch to obstacle shifting mode
        // TODO terminate this thread
	}
	
	

}
