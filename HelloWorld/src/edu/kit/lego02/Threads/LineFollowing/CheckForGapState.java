package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowingThread;
import edu.kit.lego02.userIO.BrickScreen;

public class CheckForGapState extends LineFollowingState {

   

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
    public void obstacleDetected() {
        nextState = new ObstacleState(lineFollowThread);
    }

    @Override
    protected void entry() {
        Robot robot = lineFollowThread.getRobot();
        Drive drive = robot.getDrive();

        
        //Get some distance to line
        drive.travelFwd(1);
     
        long start = System.currentTimeMillis();

        //turn left
        drive.changeMotorSpeed(-220, 250); //we do not use drive.turnLeftInPlace as we need to do a little turn
      
        while (System.currentTimeMillis() < start + 2800) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }
            if (lineFollowThread.isGreyCorner(robot.getSensorValues().getColorValue())) {
                //Corner found!
                return;
            }
        }

        start = System.currentTimeMillis();
        
        
        
        //turn right
        drive.changeMotorSpeed(250, -220);
        while (System.currentTimeMillis() < start + 3100) { //3000
            if (Thread.currentThread().isInterrupted()) {
                return;
            }

            
        }

        //Drive forward to get away from end of line
        drive.changeMotorSpeed(200, 200);
        while (System.currentTimeMillis() < start + 200) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }

            
        }
        //drive.travelFwd(3.0f);  use motorSpeed instead

    }

}
