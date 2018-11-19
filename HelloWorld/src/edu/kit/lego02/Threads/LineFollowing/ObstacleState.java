package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowingThread;

public class ObstacleState extends LineFollowingState {

	public ObstacleState(LineFollowingThread thread) {
		super(thread);
	}

	@Override
	protected void entry() {
		Robot robot = lineFollowThread.getRobot();
        Drive drive = robot.getDrive();
        
        // TODO calibrate all the parameters
        drive.turnRightInPlace(100.f);
        drive.travelFwd(20);
        drive.turnLeftInPlace(70);
        drive.travelFwd(40);
        drive.turnLeftInPlace(70);
        
        //drive forward until you hit grey
        while(!isGrey(robot.getSensorValues().getColorValue())) {
        	drive.travelFwd(0.3f);
        }
	}
	
	private boolean isGrey(float sensorValue) {
		return !lineFollowThread.isBlack(sensorValue) 
    			&& !lineFollowThread.isWhite(sensorValue);
	}

	@Override
	public void grey() {
		// TODO
		nextState = new StandardLineFollowingState(lineFollowThread);
	}
	
	
	
}
