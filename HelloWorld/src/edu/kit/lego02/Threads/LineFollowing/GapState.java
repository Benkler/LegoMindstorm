package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowingThread;
import edu.kit.lego02.userIO.BrickScreen;

public class GapState extends LineFollowingState {

    private final int GAP_SIZE = 15;

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
        
        /*
         * Drive until blue found!
         */
        if(lineFollowThread.isAlreadyDoneWithObstacle()){
            travelUntilBlueFound();
            return;
        }

        // Distance to drive blind
        drive.travelFwd(GAP_SIZE);

        travelSearchAlgorithm();

    }

    private void travelUntilBlueFound() {
        robot.getDrive().stopMotors();
        BrickScreen.clearScreen();
        BrickScreen.displayString("FINISH", 0, 0);
        long time = System.currentTimeMillis();
        while(System.currentTimeMillis() < time + 5000){
            //Entfernen wieder!!!!
        }
        
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

            long start = System.currentTimeMillis();
            drive.turnLeftInPlace();
            while (System.currentTimeMillis() < start + 1800) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                if (lineFollowThread.isGreyCorner(robot.getSensorValues().getColorValue())) {
                    return;
                }
            }

            start = System.currentTimeMillis();
            drive.turnRightInPlace();
            while (System.currentTimeMillis() < start + 1900) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                if (lineFollowThread.isGreyCorner(robot.getSensorValues().getColorValue())) {

                    // TODO hier etwas mehr drehen weil gefunden
                    return;
                }
            }

            drive.travelFwd(SEARCH_DISTANCE);

        }

        // TODO ERROR at this place! Line not found

    }

    public void travelZigZag() {

    }

    public void travelZigZagArcs() {
    }

    public void travelSinuousLines() {
    }

}
