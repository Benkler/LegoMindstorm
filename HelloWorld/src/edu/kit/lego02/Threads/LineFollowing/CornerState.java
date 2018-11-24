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
	    super.black();
	    //TODO hier vieleicht Fehlerbehandlung => wieder nach links drehen oder so
	    
	}
	
    @Override
    public void white() {
        //TODO hier vieleicht Fehlerbehandlung => wieder nach links drehen oder so
        super.white();
    }
	
	/**
	 * Strategy: Drive forward a little bit, then rotate right until the robot doesn't see white anymore. 
	 */
    @Override
    protected void entry() {
        
    	Robot robot = lineFollowThread.getRobot();
        Drive drive = robot.getDrive();
        
        drive.stopMotors();
        
        drive.travelBwd(0.3f);
       
        
        boolean white = true;

        drive.turnRightInPlace();
        

        while(white) {
            if(Thread.currentThread().isInterrupted()){
                return;
            }
        	white = lineFollowThread.isWhite(robot.getSensorValues().getColorValue());
        }
    }	
}