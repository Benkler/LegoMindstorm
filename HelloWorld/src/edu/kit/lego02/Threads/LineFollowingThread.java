package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.userIO.BrickScreen;

public class LineFollowingThread implements Runnable {
	
	LineFollowingState currentState = new StandardLineFollowingState(this);

	Robot robot = null;
	
	public LineFollowingThread (Robot robot){
		this.robot = robot;
	}
	
    @Override
    public void run() {
        BrickScreen.show("Line Following Running");
        float min = 30.0f, max = 54.0f,  // Diese Parameter adaptieren!!!  element [0,1]
                refLight = 42.0f, Mspeed = 80.0f, Mspeed2 = 15.0f;
        float readLight = 0.0f;       
        

       // EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4); //Richtiger port?
       
        
//        while (true) {
//            Motor.C.forward();
//            Motor.B.forward();
//            
//            readLight = colorSensor.getRedMode().;;; // Vielleicht den anderen w�hlen
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
// * 
// *      import lejos.nxt.*;

//public class Line5e {
    
//    public static void main(String [] args) {
//        int refdist = 15, objdist = 11, min = 30, max = 54,
/*    refLight = 42, readLight = 0, motorSpeed = 400,
    motorSpeed1 = 300, turnSpeed = 37, Mspeed = 80,
    Mspeed2 = 15, insideWheelSpeed = 250;
        
        UltrasonicSensor uss = new UltrasonicSensor(SensorPort.S3);
        LightSensor ls = new LightSensor(SensorPort.S4);
        LCD lcd = new LCD();
        
        while (Button.readButtons() == 0) {
            Motor.C.forward();
            Motor.B.forward();
=======
        while (true) {*/
            //Motor.C.forward();
            //Motor.B.forward();
//            
//            readLight = robot.getSensorValues().getColorValue(); // Vielleicht den anderen w�hlen
//	        if (readLight < min){
//	        readLight = min+1;
//	        }
	        
//	        if (max < readLight){
//	        readLight = max-1;
//        }
        
            //Motor.B.setSpeed(Mspeed + (Mspeed2 * (readLight - min)));
            //Motor.C.setSpeed(Mspeed + (Mspeed2 * (max - readLight)));
            
//            BrickScreen.clearScreen();
//            BrickScreen.show("Sensorvalue" + readLight);
            //BrickScreen.displayInt(Mspeed * (readLight - min), 0, 1);
            //BrickScreen.displayInt(Mspeed * (max - readLight), 0, 2);
   
//        }

//    }

//}