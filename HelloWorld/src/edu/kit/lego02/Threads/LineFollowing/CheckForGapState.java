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

        
     
        long start = System.currentTimeMillis();
        //drive.travelFwd(0.2f);
       // drive.turnLeftInPlace();
        
        //turn left
        drive.changeMotorSpeed(-170, 200);
      
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
        
        //drive.turnRightInPlace();
        
        //turn right
        drive.changeMotorSpeed(200, -170);
        while (System.currentTimeMillis() < start + 3100) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }

            
        }

        drive.travelFwd(3.0f);

    }

}
