package edu.kit.lego02;

import edu.kit.lego02.control.Controller;
import lejos.hardware.lcd.LCD;


public class SuperRobot {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        LCD.drawString("Start Main", 0, 0);
        
        Controller controller = new Controller();
        controller.startRobot();
        
        
        
        
        

    }

}
