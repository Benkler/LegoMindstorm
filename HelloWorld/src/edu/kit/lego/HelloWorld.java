package edu.kit.lego;

import java.util.concurrent.TimeUnit;

import lejos.hardware.lcd.LCD;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class HelloWorld {
    
    static EV3ColorSensor colorSensor = null;
    
    

	public static void main(String[] args) throws InterruptedException {
		//System.out.println("Hello World!");
		
		LCD.drawString("Hello World", 0, 0);
		
		//DifferentialPilot pilot = new DifferentialPilot(wheelDiameter, trackWidth, leftMotor, rightMotor)
		DifferentialPilot pilot = new DifferentialPilot(3.6f, 19.0f, Motor.A, Motor.B);
		
		
		 
		 colorSensor = new EV3ColorSensor(SensorPort.S3);
		 //SensorMode mode = colorSensor.getColorIDMode();
		 
		 while(true){
		     
		     LCD.clear();
	         
	         LCD.drawString(getColor().toString(), 0, 0);
	       //  pilot.travel(-35);
	         LCD.drawString("Hello World", 0, 0);
	         TimeUnit.SECONDS.sleep(1);
		     
		 }
		 
		 
		 
		 
		 
		//pilot.rotate(180);
		//pilot.travel(-35);
		
		
		
		
		
		
		
	}
	
	//TODO   HIER NOCH ÜBERARBEITEN
	public static ColorEnum getColor() {
        int colorId = colorSensor.getColorID();
        if (colorId == Color.BLACK || colorId == Color.NONE || colorId == Color.BROWN || colorId == Color.DARK_GRAY) {
            return ColorEnum.BACKGROUND;
        } else if (colorId == Color.YELLOW || colorId == Color.WHITE || colorId == Color.LIGHT_GRAY) {
            return ColorEnum.LINE;
        } else if (colorId == Color.RED || colorId == Color.MAGENTA || colorId == Color.PINK
                || colorId == Color.ORANGE) {
            return ColorEnum.MAZEMARKER;
        } else if (colorId == Color.BLUE || colorId == Color.CYAN) {
            return ColorEnum.BLUEMARKER;
        }
        return null;
    }

}
