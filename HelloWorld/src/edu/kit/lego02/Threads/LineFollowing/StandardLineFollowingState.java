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
		nextState = new CornerState(lineFollowThread); 
	}
	
	@Override
	public void black() {
		// TODO
		nextState = new CheckForGapState(lineFollowThread); 
	}

    @Override
    protected void entry() {
        
        
        //P-Adaption
        float sensorValue = lineFollowThread.getSensorValue();
        float controlValue = lineFollowThread.P * (lineFollowThread.GREY - sensorValue);
        Drive drive = lineFollowThread.getRobot().getDrive();
        float maxSpeed = drive.getMaxSpeed() * 0.3f;
        
        
         float rightSpeed = Math.max(drive.getRightSpeed() + controlValue, maxSpeed);
         float leftSpeed = Math.max(drive.getLeftSpeed() - controlValue, maxSpeed);
        
        
        
       drive.changeMotorSpeed(leftSpeed, rightSpeed);
    }
	
	
}