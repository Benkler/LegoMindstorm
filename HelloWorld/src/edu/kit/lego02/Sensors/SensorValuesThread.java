package edu.kit.lego02.Sensors;

public class SensorValuesThread implements Runnable{

    
    private SensorWrapper leftTouchSensor;
    private SensorWrapper righTouchSensor;
    private SensorWrapper colorSensor;
    private SensorWrapper ultrasonicSensor;
    private SensorWrapper colorIdSensor;
    
    private float colorValue;
    private float leftTouchValue;
    private float rightTouchValue;
    private float ultrasonicValue;
    private float colorIdValue;
    
  
    
    public SensorValuesThread(SensorWrapper leftTouchSensor, SensorWrapper righTouchSensor, SensorWrapper colorSensor,
            SensorWrapper ultrasonicSensor, SensorWrapper colorIdSensor) {
        super();
        this.leftTouchSensor = leftTouchSensor;
        this.righTouchSensor = righTouchSensor;
        this.colorSensor = colorSensor;
        this.ultrasonicSensor = ultrasonicSensor;
        this.colorIdSensor = colorIdSensor;
    }



    @Override
    public void run() {
        try {
            while(true){
                this.colorValue = colorSensor.getSingleSample();
                this.leftTouchValue = leftTouchSensor.getSingleSample();
                this.rightTouchValue = righTouchSensor.getSingleSample();
                this.ultrasonicValue = ultrasonicSensor.getSingleSample();
                this.colorIdValue = colorIdSensor.getSingleSample();
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



	public float getColorIdValue() {
		return colorIdValue;
	}


   

}
