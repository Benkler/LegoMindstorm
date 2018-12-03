package edu.kit.lego02.Sensors;

import lejos.hardware.sensor.SensorMode;
import java.util.ArrayList;

public class SensorValuesThread implements Runnable{

    
    private SensorWrapper leftTouchSensor;
    private SensorWrapper righTouchSensor;
    private SensorWrapper colorSensor;
    private SensorWrapper ultrasonicSensor;
    
    private float colorValue;
    private float[] colorValueArray;
    private float leftTouchValue;
    private float rightTouchValue;
    private float ultrasonicValue;
    
  
    
    public SensorValuesThread(SensorWrapper leftTouchSensor, SensorWrapper righTouchSensor, SensorWrapper colorSensor,
            SensorWrapper ultrasonicSensor) {
        super();
        this.leftTouchSensor = leftTouchSensor;
        this.righTouchSensor = righTouchSensor;
        this.colorSensor = colorSensor;
        this.ultrasonicSensor = ultrasonicSensor;
    }



    @Override
    public void run() {
        try {
            while(true){
                this.colorValue = colorSensor.getSingleSample();
                this.colorValueArray = colorSensor.getSampleArray();
                this.leftTouchValue = leftTouchSensor.getSingleSample();
                this.rightTouchValue = righTouchSensor.getSingleSample();
                this.ultrasonicValue = ultrasonicSensor.getSingleSample();
                Thread.sleep(5); //TODO Needs adjustment
            }
        } catch (InterruptedException e) {
            
            e.printStackTrace();
        }
        
    }
    
    public void setColorMode(String mode){
    	this.colorSensor.setColorMode(mode);
    }
    
    public ArrayList<String> getAvailableColorModes (){
    	return colorSensor.getAvailableModes();
    }


    public float getColorValue() {
        return colorValue;
    }

    public float[] getColorValueArray() {
        return colorValueArray;
    }


    public float getLeftTouchValue() {
        return leftTouchValue;
    }



    public float getRightTouchValue() {
        return rightTouchValue;
    }



    public float getUltrasonicValue() {
        return ultrasonicValue;
    }


    public void setColorSensorMode(String modeName) {
    	colorSensor.setColorMode(modeName);
    }
}
