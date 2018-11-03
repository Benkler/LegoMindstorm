package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowingThread;

public class StandardLineFollowingState extends LineFollowingState {
	
	public StandardLineFollowingState(LineFollowingThread thread) {
		super(thread);
	}
	
	@Override
	public void grey() {
		// TODO
		nextState = this;
	}
	
	@Override
	public void white() {
		// TODO
		nextState = new CornerState(thread); 
	}
	
	@Override
	public void black() {
		// TODO
		nextState = new CheckForGapState(thread); 
	}

    @Override
    protected void entry() {
        
        //P-Adaption
        float sensorValue = thread.getSensorValue();
        float controlValue = thread.P * (thread.GREY - sensorValue);
        Drive drive = thread.getRobot().getDrive();
        float leftSpeed =drive.getLeftSpeed() + controlValue;
        float rightSpeed = drive.getRightSpeed() - controlValue;
       drive.changeMotorSpeed(leftSpeed, rightSpeed);
    }
	
	
}