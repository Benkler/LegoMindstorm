package edu.kit.lego02.Sensors;

import lejos.hardware.sensor.BaseSensor;
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
            return this.samples;
        }
    
        public void setMode(String modeName) {
        	this.mode = sensor.getMode(modeName);
        }

}
