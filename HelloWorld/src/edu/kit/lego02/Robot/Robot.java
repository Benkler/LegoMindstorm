package edu.kit.lego02.Robot;

import edu.kit.lego02.Sensors.SensorValuesThread;
import edu.kit.lego02.Sensors.SensorWrapper;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Robot {
    
    //--------------Values
    private final float WHEEL_DIAM = 3.6f;
    private final float TRACK_WIDTH = 19.0f;
    
    
    //--------------Port
    private static final Port LEFT_MOTOR_PORT = MotorPort.A;
    private static final Port RIGHT_MOTOR_PORT = MotorPort.B;
    private static final Port ULTRASONIC_MOTOR_PORT = MotorPort.C;
    private static final Port COLOR_SENSOR_PORT = SensorPort.S3;

    private static final Port LEFT_TOUCH_SENSOR_PORT = SensorPort.S2;
    private static final Port RIGHT_TOUCH_SENSOR_PORT = SensorPort.S1;
    private static final Port ULTRASONIC_SENSOR_PORT =  SensorPort.S4;

    
    // -------------Motor
    private DifferentialPilot pilot;
    private EV3LargeRegulatedMotor rightMotor;
    private EV3LargeRegulatedMotor leftMotor;
    private EV3MediumRegulatedMotor ultraSonicMotor;
 
    
    //-------------------Sensors
    private final EV3ColorSensor colorSensor = new EV3ColorSensor(COLOR_SENSOR_PORT);
    private final EV3TouchSensor leftTouchSensor = new EV3TouchSensor(LEFT_TOUCH_SENSOR_PORT);
    private final EV3TouchSensor rightTouchSensor = new EV3TouchSensor(RIGHT_TOUCH_SENSOR_PORT);
    private final EV3UltrasonicSensor  ultrasonicSensor = new EV3UltrasonicSensor(ULTRASONIC_SENSOR_PORT);
    
    private SensorValuesThread sensorValueThread;
    public Robot() {
       
        //TODO  auch final?!?!
       this.rightMotor = new EV3LargeRegulatedMotor(RIGHT_MOTOR_PORT);
       this.leftMotor = new EV3LargeRegulatedMotor(LEFT_MOTOR_PORT);
       this.ultraSonicMotor = new EV3MediumRegulatedMotor(ULTRASONIC_MOTOR_PORT);
       
       SensorWrapper color = new SensorWrapper(colorSensor, "Red");
       SensorWrapper touchLeft = new SensorWrapper(leftTouchSensor, "Touch");
       SensorWrapper touchRight = new SensorWrapper(rightTouchSensor, "Touch");
       SensorWrapper ultrasonic = new SensorWrapper(ultrasonicSensor, "Distance");
       
       this.sensorValueThread = new SensorValuesThread(touchLeft, touchRight, color, ultrasonic);
       new Thread(sensorValueThread).start();
       
       
                
    }
    public SensorValuesThread getSensorValues() {
        return sensorValueThread;
    }
    
    

}
