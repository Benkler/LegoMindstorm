package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowing.LineFollowingState;
import edu.kit.lego02.Threads.LineFollowing.StandardLineFollowingState;
import edu.kit.lego02.userIO.BrickScreen;


public class LineFollowingThread implements Runnable {

    private LineFollowingState currentState = new StandardLineFollowingState(this);
    private float sensorValue;
    private Robot robot;

    private final float WHITE_THRESH = 0.74f; // TODO parameter need adjustement
    private final float BLACK_THRESH = 0.18f;
    private final float US_THRESH = 0.15f; // TODO calibrate
    public final float GREY = ((WHITE_THRESH + BLACK_THRESH) / 2);
    private final float BLACK_THRESH_CORNER = 0.25f;
    private final float WHITE_THRESH_CORNER = 0.70f;
    private final float TOUCH_PRESSED = 1.0f;
    
    private boolean alreadyDoneWithObstacle = false;
    private boolean lineFollowingFinished = false;

    /*s
     * Target power level ==> Max speed for Robot on line
     */
    public final float Tp = 200f; //200 works but slow

    /*
     * Constant for P controller
     */
    public final float Kp = (Tp / (WHITE_THRESH - GREY)) * 1.2f;

    public LineFollowingThread(Robot robot) {
        this.robot = robot;

    }

    
   public void setLineFollowingFinished(){
        lineFollowingFinished = true;
    }
    @Override
    public void run() {

        BrickScreen.show("Line Following Running");
        robot.getSensorValues().setColorMode("Red");
        
       
        

        try {
            while (!lineFollowingFinished) { //Set after last check for gap state
                if (Thread.interrupted()) {

                    BrickScreen.clearScreen();
                    return;
                }
                
                if(isObstacle() && !alreadyDoneWithObstacle){
                    alreadyDoneWithObstacle = true;
                    obstacleDetected();
                }
                
                sensorValue = robot.getSensorValues().getColorValue();

                if (isBlack(sensorValue)) {
                    black();
                } else if (isWhite(sensorValue)) {
                    white();
                } else {
                    grey();
                }

                Thread.sleep(5); // TODO wie schnell regeln?

            }
        } catch (InterruptedException e) {

        }
    }

    public LineFollowingState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(LineFollowingState currentState) {
        this.currentState = currentState;
    }

    public boolean isAlreadyDoneWithObstacle() {
        return alreadyDoneWithObstacle;
    }

    public boolean isObstacle(){
        if(robot.getSensorValues().getLeftTouchValue()==TOUCH_PRESSED || robot.getSensorValues().getRightTouchValue() == TOUCH_PRESSED){
            
            
            //check obstacle value for more than one cycle!
            return true;
        }
        return false;
    }
    
    public boolean isWhite(float sensorValue) {

        return sensorValue > WHITE_THRESH;
    }
    
    public boolean isWhiteCorner(float sensorValue){
        
        return sensorValue > WHITE_THRESH_CORNER;
    }
    
    public boolean isBlackCorner(float sensorValue){
        
        return sensorValue < BLACK_THRESH_CORNER;
    }
    
    public boolean isGreyCorner(float sensorVaue) {
        return !isBlackCorner(sensorVaue) && !isWhiteCorner(sensorVaue);
    }

    public boolean isBlack(float sensorValue) {
        return sensorValue < BLACK_THRESH;
    }

    public boolean isGrey(float sensorVaue) {
        return !isBlack(sensorVaue) && !isWhite(sensorVaue);
    }

    public float getSensorValue() {
        return sensorValue;
    }

    public Robot getRobot() {
        return robot;
    }

    private void grey() {
        currentState.grey();
        currentState.changeState();
    }

    private void black() {
        currentState.black();
        currentState.changeState();
    }

    private void white() {
        currentState.white();
        currentState.changeState();

    }

    private void blue() {
        currentState.blue();
        currentState.changeState();
    }

    private void obstacleDetected() {
        currentState.obstacleDetected();
        currentState.changeState();
    }

}

// }

// }