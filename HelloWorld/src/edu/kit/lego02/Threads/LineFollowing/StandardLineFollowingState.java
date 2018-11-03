package edu.kit.lego02.Threads.LineFollowing;

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
        Robot robot = thread.getRobot();
        float leftSpeed = robot.getLeftSpeed() + controlValue;
        float rightSpeed = robot.getRightSpeed() - controlValue;
        thread.getRobot().changeMotorSpeed(leftSpeed, rightSpeed);
    }
	
	
}