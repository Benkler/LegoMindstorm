package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowingThread;

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
        // turn to the left in incremental steps until either found grey/white OR 
        // turned far enough and found only black
		int targetTotalTurningDeg = ceilToIncrement(MIN_TOTAL_TURNING_DEGREE);
		int totalTurningDeg = 0;
		boolean turnedEnough = false;
		boolean black = true;
		
		while(black && !turnedEnough) {
			drive.turnLeftInPlace(TURNING_DEG_INC);
			totalTurningDeg += TURNING_DEG_INC;
			
			black = lineFollowThread.isBlack(robot.getSensorValues().getColorValue());
			turnedEnough = (totalTurningDeg == targetTotalTurningDeg);
		}
		
		if (black) {
			// rotated far enough, but the robot still only detects black => gap found
			
			// rotate right to starting position
			drive.turnRightInPlace(totalTurningDeg);
			
			// drive a bit forward, so that the robot definitely sees black in the following state
			// otherwise it could see grey or white due to inaccurate turning
			drive.travelFwd(0.5f);
		}
	}
	
	/**
	 * Rounds n up to the nearest multiple of the turning degree increment.
	 * @param n Number to round up.
	 * @return n rounded up to the nearest multiple of the turning degree increment.
	 */
	private int ceilToIncrement(int n) {
	    return (n + (TURNING_DEG_INC - 1)) / TURNING_DEG_INC * TURNING_DEG_INC;
	}
	
	
}
