package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowingThread;
import edu.kit.lego02.userIO.BrickScreen;

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
	public void obstacleDetected() {
		nextState = new ObstacleState(lineFollowThread);
	}

	@Override
    protected void entry() {
         Drive drive = lineFollowThread.getRobot().getDrive();
        
        //P-Adaption
        float sensorValue = lineFollowThread.getSensorValue();
        float error = lineFollowThread.GREY -sensorValue;
        float controlValue = lineFollowThread.Kp * error;       
        
        
        
        float rightSpeed = lineFollowThread.Tp + controlValue;
        float leftSpeed = lineFollowThread.Tp - controlValue;
        
 
       drive.changeMotorSpeed(leftSpeed, rightSpeed);
    }
	
	
}