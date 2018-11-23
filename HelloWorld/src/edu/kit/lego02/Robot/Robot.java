package edu.kit.lego02.Robot;

import edu.kit.lego02.Sensors.SensorValuesThread;
import edu.kit.lego02.Sensors.SensorWrapper;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Robot {
    

    
    
    //--------------Port
    private static final Port ULTRASONIC_MOTOR_PORT = MotorPort.C;
    private static final Port COLOR_SENSOR_PORT = SensorPort.S3;

    private static final Port LEFT_TOUCH_SENSOR_PORT = SensorPort.S2;
    private static final Port RIGHT_TOUCH_SENSOR_PORT = SensorPort.S1;
    private static final Port ULTRASONIC_SENSOR_PORT =  SensorPort.S4;

    
    // -------------Motor

    private EV3MediumRegulatedMotor ultraSonicMotor;
 
    
    //-------------------Sensors
    private final EV3ColorSensor colorSensor = new EV3ColorSensor(COLOR_SENSOR_PORT);
    private final EV3TouchSensor leftTouchSensor = new EV3TouchSensor(LEFT_TOUCH_SENSOR_PORT);
    private final EV3TouchSensor rightTouchSensor = new EV3TouchSensor(RIGHT_TOUCH_SENSOR_PORT);
    private final EV3UltrasonicSensor  ultrasonicSensor = new EV3UltrasonicSensor(ULTRASONIC_SENSOR_PORT);
    
    private SensorValuesThread sensorValueThread;
    
    
    private final Drive drive =  new Drive();
    
    private int currentUSAngle;
    
    
    public Robot() {
        
       
        //TODO  auch final?!?!
       
       this.ultraSonicMotor = new EV3MediumRegulatedMotor(ULTRASONIC_MOTOR_PORT);
       
       SensorWrapper color = new SensorWrapper(colorSensor, "Red");
       SensorWrapper touchLeft = new SensorWrapper(leftTouchSensor, "Touch");
       SensorWrapper touchRight = new SensorWrapper(rightTouchSensor, "Touch");
       SensorWrapper ultrasonic = new SensorWrapper(ultrasonicSensor, "Distance");
       SensorWrapper colorId = new SensorWrapper(colorSensor, "Color ID");
       
       this.sensorValueThread = new SensorValuesThread(touchLeft, touchRight, color, ultrasonic, colorId);
       new Thread(sensorValueThread).start();
       
       currentUSAngle = 0;
       
       
                
    }
    public Drive getDrive() {
        return drive;
    }
    public SensorValuesThread getSensorValues() {
        return sensorValueThread;
    }
    
    /**
     * Rotates US sensor to the given angle. 
     * @param angle Target angle of the sensor. 
     * 				0 	: middle
     * 				>0  : right orientation
     * 				<0	: left orientation
     */
    public void adjustUSAngle(int angle) {
    	// TODO implement
    }
    
    public int getColorID() {
    	return colorSensor.getColorID();
    }
}
