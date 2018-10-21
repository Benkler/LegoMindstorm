package edu.kit.lego;

import lejos.hardware.lcd.LCD;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class HelloWorld {

	public static void main(String[] args) {
		//System.out.println("Hello World!");
		
		LCD.drawString("Hello World", 0, 0);
		
		//DifferentialPilot pilot = new DifferentialPilot(wheelDiameter, trackWidth, leftMotor, rightMotor)
		DifferentialPilot pilot = new DifferentialPilot(1.5f, 10, Motor.A, Motor.B);
		pilot.travel(15);
		
		
		
		
		
		
	}

}
