package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowingThread;

public class ObstacleState extends LineFollowingState {

	public ObstacleState(LineFollowingThread thread) {
		super(thread);
	}

	private Robot robot;
	@Override
	protected void entry() {
		 robot = lineFollowThread.getRobot();
        Drive drive = robot.getDrive();
        
        // TODO calibrate all the parameters
        drive.travelBwd(5);
        drive.turnRightInPlace(90);
        drive.travelFwd(22);
        drive.turnLeftInPlace(90);
        drive.travelFwd(55);
        drive.turnLeftInPlace(80);
        drive.travelFwd(15);
        
        drive.changeMotorSpeed(80, 200);
        
        //drive forward until grey found
        while(true){
            if (Thread.currentThread().isInterrupted()) {
                return;
            }
            if(lineFollowThread.isGrey(robot.getSensorValues().getColorValue())){
                drive.stopMotors();
                return;
            }
            
        }
        
	}
	
	
	
	@Override
	public void grey() {
		// TODO
		nextState = new StandardLineFollowingState(lineFollowThread);
	}
	
	
	
}
