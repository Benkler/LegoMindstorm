package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowingThread;

public class GapState extends LineFollowingState {

    private final int GAP_SIZE = 18;

    private final int SEARCH_DISTANCE = 2;

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
    protected void entry() {

        // Distance to drive blind
        drive.travelFwd(GAP_SIZE);

        travelSearchAlgorithm();

    }

    public void travelSearchAlgorithm() {
        // Wahlweise Suche in Halbkreisen, ZickZack, ZickZackBögen,
        // Schlangenlinie
        travelHalfCircles();
        // travelZigZag();
        // travelZigZagArcs();
        // travelSinuousLines();
    }

    public void travelHalfCircles() {

        /*
         * Do the half circles 10 times
         */
        for (int i = 0; i < 10; i++) {

            // Turn left until grey found or continue after 90°
            drive.turnLeftInPlaceImmediate(90);
            checkGreyAndDrive();

            // Turn right until grey fou8nd
            drive.turnRightInPlaceImmediate(180);
            checkGreyAndDrive();

            // turn Back to origin position
            drive.turnLeftInPlace(90);

            drive.travelFwd(SEARCH_DISTANCE);

        }
        
       //TODO  ERROR at this place! Line not found

    }

    private boolean checkGreyAndDrive() {
        while (drive.getRightSpeed() != 0) {
            if (Thread.currentThread().isInterrupted()
                    || lineFollowThread.isGrey(robot.getSensorValues().getColorValue())) {
                return true;
            }

        }
        return false;

    }

    public void travelZigZag() {

    }

    public void travelZigZagArcs() {
    }

    public void travelSinuousLines() {
    }

}
