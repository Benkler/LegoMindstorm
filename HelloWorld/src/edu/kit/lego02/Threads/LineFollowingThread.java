package edu.kit.lego02.Threads;

import edu.kit.lego02.userIO.BrickScreen;

public class LineFollowingThread implements Runnable {

    @Override
    public void run() {
        BrickScreen.show("Line FOllowing Running");
        int min = 30, max = 54,  // Diese Parameter adaptieren!!!  element [0,1]
                refLight = 42, Mspeed = 80, Mspeed2 = 15;
       // SensorMode readLight = 0;
        
       // EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4); //Richtiger port?
       
        
//        while (true) {
//            Motor.C.forward();
//            Motor.B.forward();
//            
//            readLight = colorSensor.getRedMode().;;; // Vielleicht den anderen wählen
//        if (readLight < min){
//        readLight = min+1;
//        }
//        
//        if (max < readLight){
//        readLight = max-1;
//        }
//        
//            Motor.B.setSpeed(Mspeed + (Mspeed2 * (readLight - min)));
//            Motor.C.setSpeed(Mspeed + (Mspeed2 * (max - readLight)));
//            
//            BrickScreen.clearScreen();
//            BrickScreen.displayInt(readLight, 0, 0);
//           
//            BrickScreen.displayInt(Mspeed * (readLight - min), 0, 1);
//            BrickScreen.displayInt(Mspeed * (max - readLight), 0, 2);
//   
//        }

    }

}

/*
 * 
 *      import lejos.nxt.*;

public class Line5e {
    
    public static void main(String [] args) {
        int refdist = 15, objdist = 11, min = 30, max = 54,
    refLight = 42, readLight = 0, motorSpeed = 400,
    motorSpeed1 = 300, turnSpeed = 37, Mspeed = 80,
    Mspeed2 = 15, insideWheelSpeed = 250;
        
        UltrasonicSensor uss = new UltrasonicSensor(SensorPort.S3);
        LightSensor ls = new LightSensor(SensorPort.S4);
        LCD lcd = new LCD();
        
        while (Button.readButtons() == 0) {
            Motor.C.forward();
            Motor.B.forward();
            
            readLight = ls.readValue();
        if (readLight < min){
        readLight = min+1;
        }
        
        if (max < readLight){
        readLight = max-1;
        }
        
            Motor.B.setSpeed(Mspeed + (Mspeed2 * (readLight - min)));
            Motor.C.setSpeed(Mspeed + (Mspeed2 * (max - readLight)));
            
            lcd.clear();
            lcd.drawInt(readLight, 0, 0);
        lcd.drawInt(Mspeed * (readLight - min), 0, 1);
        lcd.drawInt(Mspeed * (max - readLight), 0, 2);
        }
    }
}       
 * 
 */
