package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.userIO.BrickScreen;
import lejos.hardware.Brick;
import lejos.hardware.Sound;

public class ColorSearchThread implements Runnable {

    private Robot robot;
    private Drive drive;
    private float[] colorArray;
    private boolean whiteFound = false;
    private boolean redFound = false;
    private boolean corner = true;

    private boolean lastTurnWasLeft = false;
    private int Kp = 150;
    private int Tp = 300;

    /*
     * Distance to right wall with US Sensor, Right wall is the wall on the
     * right side when coming down from the bridge
     */
    private float startUSVal;
    private final float TOUCH_PRESSED = 1.0f;
   
    private final int GREEN = 1;
    private final int RED = 0;
    private final float COLORRED = 0.15f;
    private final float COLORGREEN = 0.35f;

    public ColorSearchThread(Robot robot) {
        this.robot = robot;
        this.drive = robot.getDrive();

    }

    @Override
    public void run() {
        BrickScreen.clearScreen();
        BrickScreen.show("Color Search Running");

        robot.getSensorValues().setRGB();
        initUSSensor();
        colorSearch();

        finish();

    }

    private void initUSSensor() {
        robot.pointUSSensorForward();


    }

    private void finish() {
        Sound.beepSequence();
        Sound.beepSequenceUp();
        Sound.twoBeeps();
    }

    private void colorSearch() {

        while (!allColorsFound()) {
            if (Thread.interrupted()) {

                BrickScreen.clearScreen();
                return;
            }
            drive.changeMotorSpeed(400, 400);
            driveTillWallFound();
            if (allColorsFound())
                return;
            turn();

        }

    }

    private void regulateDirection() {
        
        float sensorValue = robot.getSensorValues().getUltrasonicValue();
        float error =  startUSVal - sensorValue;
        float controlValue = this.Kp * error;       
        BrickScreen.clearScreen();
        BrickScreen.displayFloat(sensorValue, 0, 0);
        
        if(error < 0){
            //BrickScreen.clearScreen();
            //BrickScreen.displayFloat(sensorValue, 0, 0);
            drive.changeMotorSpeed(380, 420);
        }else{
            //BrickScreen.clearScreen();
            //BrickScreen.displayString("Right", 0, 0);
            drive.changeMotorSpeed(420, 380);
        }
        
        
        

    }
    
    private void initCurrentUSSensorValue(){
        
        
    }

    private void driveTillWallFound() {
        
       startUSVal = robot.getSensorValues().getUltrasonicValue();
       
        while (true) {
            if (Thread.interrupted()) {

                BrickScreen.clearScreen();
                return;
            }

            regulateDirection();

            if (wallTouched()) {
                drive.stopMotors();
                Sound.twoBeeps();
                return;
            }
            if (isWhite()) {
                drive.stopMotors();

                // BEEEP
                Sound.beep();

                // Wait for 2 seconds
                long currentTime = System.currentTimeMillis();
                while (System.currentTimeMillis() < currentTime + 20);
                    

                whiteFound = true;
                if (allColorsFound())
                    return;
            }

            if (isRed()) {
                // BEEP
                Sound.beep();

                // Wait for 2 seconds
                long currentTime = System.currentTimeMillis();
                while (System.currentTimeMillis() < currentTime + 20)
                    ;

                redFound = true;
                if (allColorsFound())
                    return;
            }
        }

    }

    private void turn() {

        if (lastTurnWasLeft) {
            turnRight180();

        } else {
            turnLeft180();
        }

    }

    private boolean allColorsFound() {
        return whiteFound && redFound;
    }

    private void turnLeft180() {
        // Tail of the robot would touch the wall when turning in th corner

        if (corner) {
            // Get little bit more distance to wall before turning
            drive.travelBwd(6);
            drive.turnRightInPlace(15);
            drive.travelBwd(4);
            drive.turnLeftInPlace(15);

            drive.turnLeftInPlace(85);
            drive.travelFwd(1);
            drive.turnLeftInPlace(85);
            corner = false;
        } else {
            drive.travelBwd(5);
            drive.turnLeftInPlace(85);
            drive.travelFwd(5);
            drive.turnLeftInPlace(85);
        }

        lastTurnWasLeft = true;

    }

    private void turnRight180() {
        drive.travelBwd(5);
        drive.turnRightInPlace(85);
        drive.travelFwd(5);
        drive.turnRightInPlace(85);
        lastTurnWasLeft = false;
    }

    private boolean wallTouched() {

        if (robot.getSensorValues().getLeftTouchValue() == TOUCH_PRESSED
                || robot.getSensorValues().getRightTouchValue() == TOUCH_PRESSED) {

            // check obstacle value for more than one cycle!
            return true;
        }
        return false;

    }

    private boolean isWhite() {
        colorArray = robot.getSensorValues().getColorValueArray();
        return colorArray[GREEN] > COLORGREEN;
    }

    private boolean isRed() {
        colorArray = robot.getSensorValues().getColorValueArray();
        return colorArray[RED] > COLORRED;
    }

}
