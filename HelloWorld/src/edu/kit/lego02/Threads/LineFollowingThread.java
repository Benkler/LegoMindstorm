package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowing.LineFollowingState;
import edu.kit.lego02.Threads.LineFollowing.StandardLineFollowingState;
import edu.kit.lego02.userIO.BrickScreen;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class LineFollowingThread implements Runnable {
    
private LineFollowingState currentState = new StandardLineFollowingState(this);
private float sensorValue;
private Robot robot;
private float maxSpeed;


private final float WHITE_THRESH = 0.8f; //TODO parameter need adjustement
private final float BLACK_THRESH = 0.2f;
public final float GREY = (WHITE_THRESH+BLACK_THRESH)/2;
public final float P = 30.0f;




    

    public LineFollowingThread(Robot robot) {
        this.robot = robot;
        this.maxSpeed = robot.getDrive().getMaxSpeed();
        
    }
    
    public float getSensorValue(){
        return sensorValue;
    }

    public Robot getRobot() {
        return robot;
    }

    @Override
    public void run() {
        BrickScreen.show("Line Following Running");
        robot.getDrive().changeMotorSpeed(maxSpeed*0.4f, maxSpeed*0.4f);
        
        
       

        try {
            while (true) {
                

                sensorValue = robot.getSensorValues().getColorValue();
                
                // TODO check for blue
                // TODO if blue, call blue()
                
                BrickScreen.clearScreen();
                BrickScreen.displayFloat(maxSpeed, 0   , 0);
                grey();
//                if(isBlack(sensorValue)){
//                   black();
//                    
//                }else if(isWhite(sensorValue)){
//                    white();
//                }else{
//                    grey();
//                }
//                
//        

                Thread.sleep(10); //TODO wie schnell regeln?

            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public LineFollowingState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(LineFollowingState currentState) {
        this.currentState = currentState;
    }
    
 
    public boolean isWhite(float sensorValue){
       
        return sensorValue < WHITE_THRESH;
    }
    
    
    public boolean isBlack(float sensorValue){
        return sensorValue > BLACK_THRESH;
    }
    

    private void grey() {
           currentState.grey();
           currentState.changeState();
    }
    
    private void black(){
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
    
    
}

// }

// }