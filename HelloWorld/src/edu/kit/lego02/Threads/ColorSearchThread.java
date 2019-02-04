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

    private boolean lastTurnWasLeft = false;
    private int Kp = 150;
    private int Tp = 250;

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

        colorSearch();

        finish();

    }

    private void initUSSensor() {
        robot.pointUSSensorForward();
        long time = System.currentTimeMillis();

        while (System.currentTimeMillis() < time + 1000) {
            // do nothing;
            // Robot should wait so that sensor thread can read some US sensor
            // Values
            // No idea why, but instant turning and measuring did not work
        }

    }

    private void finish() {
        Sound.beepSequence();
        Sound.beepSequenceUp();
        Sound.twoBeeps();
    }

    private void colorSearch() {
        initUSSensor();

        while (!allColorsFound()) {
            if (Thread.interrupted()) {

                BrickScreen.clearScreen();
                return;
            }
            driveTillWallFound();
            if (allColorsFound())
                return;
            turn();

        }

    }

    private void regulateDirection() {

        float sensorValue = robot.getSensorValues().getUltrasonicValue() * 100; // in
                                                                                // cm
        float error = startUSVal - sensorValue; // error in cm
        BrickScreen.clearScreen();
        BrickScreen.displayFloat(error, 0, 0);

        // In case Sensor touches the wall
        if (sensorValue == Float.POSITIVE_INFINITY) {
            if (lastTurnWasLeft) { // right wall on the way back to entry site
                drive.changeMotorSpeed(550, 450);
            } else { // right wall on the way back to entry site
                drive.changeMotorSpeed(450, 550);
            }
            return;
        }

        if (error < 0) {
            drive.changeMotorSpeed(450, 550);
        } else {
            drive.changeMotorSpeed(550, 450);
        }

    }

    /*
     * Init current Distance to wall by calculate average of (max.) 50 valid
     * values
     */
    private float initCurrentUSSensorValue() {
        float temp = 0;
        int counterForValidValues = 0;
        for (int i = 0; i < 50; i++) {
            float currentVal = robot.getSensorValues().getUltrasonicValue();
            if (currentVal == Float.POSITIVE_INFINITY) {
                continue; // try to avoid sensor errors
            }
            temp += currentVal;
            counterForValidValues++;
        }
        temp = temp / counterForValidValues; // get average
        temp = temp * 100; // get in cm

        // if(temp < 5) return 5; // we always want to be at least 5 cm away
        // from wall
        // return temp;
        return temp < 5 ? 5 : temp;

    }

    /*
     * Drive straight but first take sample to get orientation
     */
    private void driveTillWallFound() {

        startUSVal = initCurrentUSSensorValue();

        while (true) {
            if (Thread.interrupted()) {
                BrickScreen.clearScreen();
                return;
            }

            regulateDirection();
            checkForColor();
            if (allColorsFound()) {
                return;
            }

            if (wallTouched()) {
                drive.stopMotors();
                return;
            }

        }

    }

    /*
     * Check for color
     */
    private void checkForColor() {

        if (!whiteFound && isWhite()) {
            drive.stopMotors();
            // BEEEP
            Sound.twoBeeps();

            // Wait for 2 seconds
            long currentTime = System.currentTimeMillis();
            while (System.currentTimeMillis() < currentTime + 1000){
                //do nothing
            }
             
            whiteFound = true;
        }

        if (!redFound && isRed()) {
            drive.stopMotors();
            // BEEP
            Sound.twoBeeps();

            // Wait for 2 seconds
            long currentTime = System.currentTimeMillis();
            while (System.currentTimeMillis() < currentTime + 1000){
                //do nothing
            }
                
            redFound = true;

        }

    }

    private void turn() {

        if (lastTurnWasLeft) {
            turnRight180();
            drive.travelBwd(12);

        } else {
            turnLeft180();
            drive.travelBwd(15);
        }

    }

    private boolean allColorsFound() {
        return whiteFound && redFound;
    }

    private void turnLeft180() {
        // Tail of the robot would touch the wall when turning in th corner

        drive.travelBwd(5);
        drive.turnLeftInPlace(88);
        checkForColor();
        if (allColorsFound()) {
            return;
        }
        drive.travelFwd(5);
        checkForColor();
        if (allColorsFound()) {
            return;
        }
        drive.turnLeftInPlace(88);

        lastTurnWasLeft = true;

    }

    private void turnRight180() {
        drive.travelBwd(5);
        drive.turnRightInPlace(85);
        checkForColor();
        if (allColorsFound()) {
            return;
        }
        drive.travelFwd(5);
        checkForColor();
        if (allColorsFound()) {
            return;
        }
        drive.turnRightInPlace(88);
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
        return colorArray[GREEN] > COLORGREEN && colorArray[RED] > COLORRED ;
    }

    private boolean isRed() {
        colorArray = robot.getSensorValues().getColorValueArray();
        return colorArray[RED] > COLORRED && colorArray[GREEN] < 0.1 && colorArray[2] < 0.1; //Anpassen?!
    }

}
