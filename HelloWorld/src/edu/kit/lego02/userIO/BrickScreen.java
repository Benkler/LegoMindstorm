package edu.kit.lego02.userIO;

import lejos.hardware.lcd.LCD;

public class BrickScreen  {
    
    private static int xPos = 0;
    private static int yPos = 0;
    
    public static void clearScreen() {
        LCD.clear();
        xPos = 0;
        yPos = 0;
    }
    
    public static void displayString(String str, int xPos, int yPos) {
        LCD.drawString(str, xPos, yPos);
    }
    
    public static void displayInt(int i, int xPos, int yPos) {
        LCD.drawInt(i, xPos, yPos);
    }
    
    public static void show(String str) {
        LCD.drawString(str, xPos, yPos++);
    }

    public static void displayFloat(float sensorValue, int xPos, int yPos) {
        String temp =String.valueOf(sensorValue);
        LCD.drawString(temp, xPos, yPos);
        
    }

}
