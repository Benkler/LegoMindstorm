package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.userIO.BrickScreen;
import lejos.hardware.Brick;
import lejos.hardware.Sound;

public class ColorSearchThread implements Runnable {

    private Robot robot;
    private Drive drive;
    private float [] colorArray;
    private boolean whiteFound = false;
    private boolean redFound = false;
    
    private boolean lastTurnWasLeft = false;
    private final float TOUCH_PRESSED = 1.0f;
    private final int BLUE = 2;
    private final int GREEN= 1;
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

    }

    
    private void colorSearch(){
        
        while(!allColorsFound() ){
           drive.changeMotorSpeed(200, 200);
           driveTillWallFound();
           if (allColorsFound()) return;
           turn();
            
            
        }
    }
    
    
    private void driveTillWallFound(){
        while(true){
            if (Thread.interrupted()) {

                BrickScreen.clearScreen();
                return;
            }
            
            
            if(wallTouched()){
                Sound.twoBeeps();
                return;
             }
             if(isWhite()){
                 //BEEEP
                 Sound.beep();
                 whiteFound = true;
                 if(allColorsFound()) return;
             }
             
             if(isRed()){
                 //BEEP
                 Sound.beep();
                 redFound =true;
                 if(allColorsFound()) return;
             }
        }
        
            
        
    }
    
    private void turn(){
        
        if(lastTurnWasLeft){
            turnRight180();
            
        }else{
            turnLeft180();
        }
        
    }
    
    private boolean allColorsFound(){
        return whiteFound && redFound;
    }
    
    
    private void turnLeft180(){
        drive.travelBwd(5);
        drive.turnLeftInPlace(90);
        drive.travelFwd(5);
        drive.turnInPlace(90);
        lastTurnWasLeft = true;
        
    }
    
    private void turnRight180(){
        drive.travelBwd(5);
        drive.turnRightInPlace(90);
        drive.travelBwd(5);
        drive.turnRightInPlace(90);
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
