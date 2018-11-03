package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.LineFollowing.LineFollowingState;
import edu.kit.lego02.Threads.LineFollowing.StandardLineFollowingState;
import edu.kit.lego02.userIO.BrickScreen;

public class LineFollowingThread implements Runnable {
private LineFollowingState currentState = new StandardLineFollowingState(this);
private float sensorValue;
private Robot robot = null;

private final float WHITE_THRESH = 3.0f;
private final float BLACK_THRESH = 5.8f;
public final float GREY = 4.0f;
public final float P = 2.0f;



    

    public LineFollowingThread(Robot robot) {
        this.robot = robot;
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
        
        
       

        try {
            while (true) {
                

                sensorValue = robot.getSensorValues().getColorValue();
                
                if(isBlack(sensorValue)){
                   black();
                    
                }else if(isWhite(sensorValue)){
                    white();
                }else{
                    grey();
                }
                
        

                Thread.sleep(25); //TODO wie schnell regeln?

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
    
    
}

// }

// }