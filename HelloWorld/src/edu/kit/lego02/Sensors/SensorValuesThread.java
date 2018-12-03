package edu.kit.lego02.Sensors;

import lejos.hardware.sensor.SensorMode;

public class SensorValuesThread implements Runnable{

    
    private SensorWrapper leftTouchSensor;
    private SensorWrapper righTouchSensor;
    private SensorWrapper colorSensor;
    private SensorWrapper ultrasonicSensor;
    
    private float colorValue;
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
                this.leftTouchValue = leftTouchSensor.getSingleSample();
                this.rightTouchValue = righTouchSensor.getSingleSample();
                this.ultrasonicValue = ultrasonicSensor.getSingleSample();
                Thread.sleep(5); //TODO Needs adjustment
            }
        } catch (InterruptedException e) {
            
            e.printStackTrace();
        }
        
    }



    public float getColorValue() {
        return colorValue;
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
    	colorSensor.setMode(modeName);
    }
   

}
