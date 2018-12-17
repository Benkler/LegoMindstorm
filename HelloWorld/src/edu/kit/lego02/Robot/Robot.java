package edu.kit.lego02.Robot;

import edu.kit.lego02.Sensors.SensorValuesThread;
import edu.kit.lego02.Sensors.SensorWrapper;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.SensorMode;
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
    
    private static final int US_FWD_ANGLE = 90;
    private static final int US_SKEW_ANGLE = 30;
    private static final int US_DWD_ANGLE = 0;
    private static int usAngle = US_DWD_ANGLE;
    
    
    public Robot() {
        
       
        //TODO  auch final?!?!
       try {
    	   this.ultraSonicMotor = new EV3MediumRegulatedMotor(ULTRASONIC_MOTOR_PORT);
       } catch (IllegalArgumentException e) {
    	   try {
			Thread.sleep(5);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	   ultraSonicMotor.close();
    	   this.ultraSonicMotor = new EV3MediumRegulatedMotor(ULTRASONIC_MOTOR_PORT);
       }
       
       SensorWrapper color = new SensorWrapper(colorSensor, "Red");
       SensorWrapper touchLeft = new SensorWrapper(leftTouchSensor, "Touch");
       SensorWrapper touchRight = new SensorWrapper(rightTouchSensor, "Touch");
       SensorWrapper ultrasonic = new SensorWrapper(ultrasonicSensor, "Distance");
       
       this.sensorValueThread = new SensorValuesThread(touchLeft, touchRight, color, ultrasonic);
       new Thread(sensorValueThread).start();
       
       
                
    }
    public Drive getDrive() {
        return drive;
    }
    public SensorValuesThread getSensorValues() {
        return sensorValueThread;
    }
    
    public void pointUSSensorForward() {
    	ultraSonicMotor.rotate(computeUltraSonicMotorInput(US_FWD_ANGLE));
    }
    
    public void pointUSSenorSkew() {
    	ultraSonicMotor.rotate(computeUltraSonicMotorInput(US_SKEW_ANGLE));
    }
    
    public void pointUSSensorDownward() {
    	ultraSonicMotor.rotate(computeUltraSonicMotorInput(US_DWD_ANGLE));
    }
    
    private int computeUltraSonicMotorInput(int targetAngle) {
    	return - usAngle +  targetAngle;
    }
}
