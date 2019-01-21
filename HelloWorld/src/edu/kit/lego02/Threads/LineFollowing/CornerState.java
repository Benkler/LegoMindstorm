package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowingThread;

public class CornerState extends LineFollowingState {
	

	public CornerState(LineFollowingThread thread) {
		super(thread);
	}
	
	@Override
    public void grey() {
		
		nextState = new StandardLineFollowingState(lineFollowThread); 
	}
	

	@Override
	public void black() {
	    //super.black();
	    //Error solution:
	    nextState = new CheckForGapState(lineFollowThread); //////////////////////////Sicher?!
	    
	}
	
	  @Override
	    public void obstacleDetected() {
	        nextState = new ObstacleState(lineFollowThread);  //////////////////////////Sicher?!
	    }
	
    @Override
    public void white() {
        
        //super.white();
        //Error solution:
        nextState = this; //Still seing white
    }
	
	/**
	 * Strategy: Drive forward a little bit, then rotate right until the robot doesn't see white anymore. 
	 */
    @Override
    protected void entry() {
        
    	Robot robot = lineFollowThread.getRobot();
        Drive drive = robot.getDrive();

       // drive.travelBwd(0.3f); Improve!!!
        drive.turnRightInPlace(5);
        
        
        boolean white = true;

        drive.turnRightInPlace();
        
        while(white) {
            if(Thread.currentThread().isInterrupted()){
                return;
            }
        	white = lineFollowThread.isWhiteCorner(robot.getSensorValues().getColorValue());
        }
    }	
}