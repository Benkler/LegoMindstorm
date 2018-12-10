package edu.kit.lego02.Sensors;

import java.util.ArrayList;

import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class SensorWrapper {
    
    
        
        private SensorMode mode;
        private float[] samples;
        private BaseSensor sensor;

        public SensorWrapper(BaseSensor sensor, String mode) {
            this.mode = sensor.getMode(mode);
           
            this.samples = new float[this.mode.sampleSize()];
            this.sensor = sensor;
        }
        
        public SensorWrapper(BaseSensor sensor, int mode) {
            this.mode = sensor.getMode(mode);
            this.samples = new float[this.mode.sampleSize()];
            this.sensor = sensor;
        }
        
        public float getSingleSample()
        {
            mode.fetchSample(samples, 0);
            return samples[0];
        }
        
        public float[] getSampleArray() {
        	float[] temp = new float[3]; 
        	
            this.mode.fetchSample(temp, 0);
            return temp;
        }
        
        public void setColorMode(String mode){
        	this.mode = ((EV3ColorSensor) sensor).getRGBMode();
        }


        public void setRGB(){
        	this.mode = ((EV3ColorSensor) sensor).getRGBMode();
        	this.getSampleArray();
        }

        public int getColorMode(){
        	return sensor.getCurrentMode();
        }


        public ArrayList<String>  getAvailableModes(){
        	return sensor.getAvailableModes();
        }
}
