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
    
    private boolean USSensorPointsForward;
    
    
    public Robot() {
        
       
        //TODO  auch final?!?!
       
       this.ultraSonicMotor = new EV3MediumRegulatedMotor(ULTRASONIC_MOTOR_PORT);
       
       SensorWrapper color = new SensorWrapper(colorSensor, "Red");
       SensorWrapper touchLeft = new SensorWrapper(leftTouchSensor, "Touch");
       SensorWrapper touchRight = new SensorWrapper(rightTouchSensor, "Touch");
       SensorWrapper ultrasonic = new SensorWrapper(ultrasonicSensor, "Distance");
       
       this.sensorValueThread = new SensorValuesThread(touchLeft, touchRight, color, ultrasonic);
       new Thread(sensorValueThread).start();
       
       USSensorPointsForward = true;
       
       
                
    }
    public Drive getDrive() {
        return drive;
    }
    public SensorValuesThread getSensorValues() {
        return sensorValueThread;
    }
    
//    /**
//     * Rotates US sensor to the given angle. 
//     * @param angle Target angle of the sensor. 
//     * 				0 	: middle
//     * 				>0  : right orientation
//     * 				<0	: left orientation
//     */
//    public void adjustUSAngle(int angle) throws IllegalArgumentException {
//    	if ( angle > 90 || angle < -90) {
//    		throw new IllegalArgumentException("Error, ultrasound angle is out of bounds.");
//    	}
//    	
//    	ultraSonicMotor.rotate(angle - currentUSAngle);
//    	currentUSAngle = angle;
//    }
    
    public void pointUSSensorForward() {
    	if (!USSensorPointsForward) {
    		USSensorPointsForward = true;
    		ultraSonicMotor.rotate(90);
    	}
    }
    
    public void pointUSSensorDownward() {
    	if (USSensorPointsForward) {
    		USSensorPointsForward = false;
    		ultraSonicMotor.rotate(-90);
    	}
    }
    
    public int getColorID() {
    	return colorSensor.getColorID();
    }
    
//    public void setColorSensorToAmbient() {
//    	colorSensor.setCurrentMode("Ambient");
//    }
    
    public float getAmbient() {
    	colorSensor.setFloodlight(Color.NONE);
    	float[] sampleArray = new float[1];
    	colorSensor.getAmbientMode().fetchSample(sampleArray, 0);
    	return sampleArray[0];
    }
    
    public float getUSValue() {
    	SampleProvider sampleProvider = ultrasonicSensor.getDistanceMode();
    	float[] sample = new float[1];
    	sampleProvider.fetchSample(sample, 0);
    	return sample[0];
    }
}
