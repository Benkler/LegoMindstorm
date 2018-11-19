package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowing.LineFollowingState;
import edu.kit.lego02.Threads.LineFollowing.StandardLineFollowingState;
import edu.kit.lego02.control.Controller;
import edu.kit.lego02.userIO.BrickScreen;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class LineFollowingThread implements Runnable {
    
private LineFollowingState currentState = new StandardLineFollowingState(this);
private float sensorValue;
private Robot robot;
private float maxSpeed;





private final float WHITE_THRESH = 0.78f; //TODO parameter need adjustement
private final float BLACK_THRESH = 0.14f;
private final float US_THRESH = 0.01f; // TODO calibrate 
public final float GREY = ((WHITE_THRESH+BLACK_THRESH)/2);

/*
 * Target power level ==> Max speed for Robot on line
 */
public final float Tp = 220f;

/*
 * Constant for P controller
 */
public final float Kp = (Tp/(WHITE_THRESH-GREY)) * 1.3f;




    

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
//    	for (int i = 0; i < 90; i++) {
//			robot.getDrive().turnRightInPlace(3);
//		}
//    	System.exit(0);
    	
//    	while (true) {
//    		BrickScreen.displayFloat(robot.getSensorValues().getUltrasonicValue(), 0, 0);
//    		try {
//				Thread.sleep(300);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    	}
    	
        BrickScreen.show("Line Following Running");
        //robot.getDrive().changeMotorSpeed(maxSpeed*0.3f, maxSpeed*0.3f);
        
        
       

        try {
            while (true) {
              if(Thread.currentThread().isInterrupted()){
                  BrickScreen.clearScreen();
                  return;
              }
                sensorValue = robot.getSensorValues().getColorValue();
                
                if(robot.getSensorValues().getUltrasonicValue() < US_THRESH) {
                	//BrickScreen.displayString("OBSTACLE", 0, 0);
                	obstacleDetected();
                	continue;
                }
               
                if(isBlack(sensorValue)){
                    //BrickScreen.displayString("BLACK", 0, 0);
                    black();
                    
                    //robot.getDrive().stopMotors();
         
                    
                }else if(isWhite(sensorValue)){
                	//BrickScreen.displayString("WHITE", 0, 0);
                    white();

                    
                    //robot.getDrive().stopMotors();
                    
                }else{
                    grey();
                }
                
                Thread.sleep(5); //TODO wie schnell regeln?


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
       
        return sensorValue > WHITE_THRESH;
    }
    
    
    public boolean isBlack(float sensorValue){
        return sensorValue < BLACK_THRESH;
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
    
    private void obstacleDetected() {
    	currentState.obstacleDetected();
    	currentState.changeState();
    }
    
}

// }

// }