package edu.kit.lego02.Robot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.robotics.navigation.DifferentialPilot;

public class Drive {

    private static final Port LEFT_MOTOR_PORT = MotorPort.A;
    private static final Port RIGHT_MOTOR_PORT = MotorPort.B;
    
    //--------------Values
    private final float WHEEL_DIAM = 3.6f;
    private final float TRACK_WIDTH = 19.0f;

    private DifferentialPilot pilot;
    private EV3LargeRegulatedMotor rightMotor;
    private EV3LargeRegulatedMotor leftMotor;

    public Drive() {
        this.rightMotor = new EV3LargeRegulatedMotor(RIGHT_MOTOR_PORT);
        this.leftMotor = new EV3LargeRegulatedMotor(LEFT_MOTOR_PORT);
        this.pilot = new DifferentialPilot(WHEEL_DIAM, TRACK_WIDTH, leftMotor, rightMotor);

    }

    public float getLeftSpeed() {
        return leftMotor.getSpeed();
    }

    
    /**
     * Change Motor speed.
     * Positive value is forward movement
     * Negative value is backward movement
     * @param leftSpeed
     * @param rightSpeed
     */
    public void changeMotorSpeed(float leftSpeed, float rightSpeed) {
        leftMotor.startSynchronization();
        rightMotor.startSynchronization();
        
        if(leftSpeed < 0){
            leftMotor.setSpeed(-leftSpeed);
            leftMotor.backward();
        }else{
            leftMotor.setSpeed(leftSpeed);
            leftMotor.forward();
        }
        
        if(rightSpeed < 0){
            rightMotor.setSpeed(-rightSpeed);
            rightMotor.backward();
        }else{
            rightMotor.setSpeed(rightSpeed);
            rightMotor.forward();
        }


        leftMotor.endSynchronization();
        rightMotor.endSynchronization();

    }

    /**
     * 
     * @return SPeed of right motor
     */
    public float getRightSpeed() {
        return rightMotor.getSpeed();
    }
 
    /**
     * Turn Left with only one Chain Moving
     * @param angle
     * @throws IllegalArgumentException
     */
    public void turnLeftSingleChain(float angle) throws IllegalArgumentException {
        if (angle < 0 || angle > 180) { // TODO Rainer pr�ft das
            throw new IllegalArgumentException();
        }
        pilot.rotate(angle);
    }

    /**
     * Turn right with only one chain moving
     * @param angle
     * @throws IllegalArgumentException
     */
    public void turnRightSingleChain(float angle) throws IllegalArgumentException {
        if (angle < 0 || angle > 180) { // TODO das auch
            throw new IllegalArgumentException();
        }
        pilot.rotate(-angle);
    }

    /**
     * Turn left in place means that both chains are moving (in opposite direction)
     * @param angle
     */
    public void turnLeftInPlace(float angle) {
        int angleVal = (int) (1* angle);  //TODO needs adjustment
                rightMotor.forward();
                leftMotor.backward();
                rightMotor.rotate( angleVal, true); //immediate return
                leftMotor.rotate(-angleVal, false); //return when rotation finished
                
                leftMotor.forward();
                
                
    }

    /**
     * Turn right in place means that both chains are moving (in opposite direction)
     * @param angle
     */
    public void turnRightInPlace(float angle) {
        int angleVal = (int) (1* angle);
        rightMotor.backward();
        leftMotor.forward();
        rightMotor.rotate( -angleVal, true); //immediate return
        leftMotor.rotate(angleVal, false); //return when rotation finished
        
        rightMotor.forward();

    }

    /**
     * Travel forward in centimeter
     * @param distance
     */
    public void travelFwd(float distance) {
        pilot.travel(distance);

    }

    /**
     * Travel backward in centimeter
     * @param distance
     */
    public void travelBwd(float distance) {
        pilot.travel(-distance);
    }
 
    /**
     * Motor full stop
     */
    public void stopMotors() {
        pilot.stop();
        
    }
    
    

}