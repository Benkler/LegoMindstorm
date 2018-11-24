package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowingThread;
import edu.kit.lego02.userIO.BrickScreen;

public class CheckForGapState extends LineFollowingState {
	
	private final int TURNING_DEG_INC = 3; // turning degree increment
	private final int MIN_TOTAL_TURNING_DEGREE = 100;
	
	public CheckForGapState(LineFollowingThread thread) {
		super(thread);
	}

	@Override
    public void grey() {
		// robot drove left corner
		nextState = new StandardLineFollowingState(lineFollowThread);
	}

	@Override
    public void black() {
		// as gap was detected
		nextState = new GapState(lineFollowThread);
	}

	@Override
	protected void entry() {
		Robot robot = lineFollowThread.getRobot();
        Drive drive = robot.getDrive();
		
        drive.stopMotors();
		
        /*
         * With immediate return
         */
		drive.turnLeftInPlaceImmediate(100);
		
		
		while(drive.getRightSpeed() != 0 ){
		    if(Thread.currentThread().isInterrupted()){
                return;
            }
		    
		    if(lineFollowThread.isGrey(robot.getSensorValues().getColorValue())){
		        return;
		    }
		}
		
		
		
			
			drive.turnRightInPlace(100);
			
			drive.travelFwd(0.5f);
		
		
		BrickScreen.clearScreen();
		BrickScreen.displayString("22222222", 0, 0);
	}
	
	
}
