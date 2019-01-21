package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowingThread;
import edu.kit.lego02.userIO.BrickScreen;

public class GapState extends LineFollowingState {

    private final int GAP_SIZE = 17; //15 was to less

    private final int SEARCH_DISTANCE = 3; // 2 was

    private Drive drive;

    private Robot robot;

    public GapState(LineFollowingThread thread) {
        super(thread);

        drive = thread.getRobot().getDrive();
        robot = thread.getRobot();

    }

    @Override
    public void grey() {

        nextState = new StandardLineFollowingState(lineFollowThread);

    }
    
    @Override
    public void white() {
        //Error solution: Still on white line
       nextState = new CornerState(lineFollowThread);
    }
    
   @Override
    public void black() {
       // Error solution: Check on more time
       nextState = new CheckForGapState(lineFollowThread);
    }

    @Override
    protected void entry() {

       
         if(lineFollowThread.isAlreadyDoneWithObstacle()){
             lineFollowThread.setLineFollowingFinished();
             drive.stopMotors();
             BrickScreen.clearScreen();
             BrickScreen.displayString("Finish Line Following", 0, 0);
         return;
         }

        // Distance to drive blind
        drive.travelFwd(GAP_SIZE);

        travelSearchAlgorithm();

    }

    

    public void travelSearchAlgorithm() {
        
        //Already on line
       if(lineFollowThread.isWhite(robot.getSensorValues().getColorValue())){
           
           //Just get to the right edge of the line
           drive.turnRightInPlace();
           while(lineFollowThread.isWhite(robot.getSensorValues().getColorValue())){
               if (Thread.currentThread().isInterrupted()) {
                   return;
               }
           }
           //Right Edge found! No search algorithm needed
          return; 
       }
        
        
        //Not on Line: We need travel Search algorithm
        travelHalfCircles();
       
    }

    public void travelHalfCircles() {

        /*
         * Do the half circles 10 times
         */
        for (int i = 0; i < 10; i++) {

            long start = System.currentTimeMillis();

            /*
             * Search while driving left
             */

            drive.turnLeftInPlace();
            while (System.currentTimeMillis() < start + 1800) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                if (lineFollowThread.isGreyCorner(robot.getSensorValues().getColorValue())) {
                    return;
                }
            }

            /*
             * Search while driving right
             */
            start = System.currentTimeMillis();
            drive.turnRightInPlace();
            while (System.currentTimeMillis() < start + 3800) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                /*
                 * Need to cross line as we need to drive on the right edge
                 */
                if (lineFollowThread.isGreyCorner(robot.getSensorValues().getColorValue())) {

                    /*
                     * Drive as long as on white line!
                     */
                    while (lineFollowThread.isWhite(robot.getSensorValues().getColorValue())) {
                        if (Thread.currentThread().isInterrupted()) {
                            // Return as soon as Line was crossed
                            return;
                        }

                    }
                    return;
                }
            }

            /*
             * Get Back to origin position
             */
            start = System.currentTimeMillis();
            drive.turnLeftInPlace();
            while (System.currentTimeMillis() < start + 1800) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

            }

            //Drive a bit forward and do half cirlce again
            drive.travelFwd(SEARCH_DISTANCE);

        }

        // TODO ERROR at this place! Line not found

    }

   
}
