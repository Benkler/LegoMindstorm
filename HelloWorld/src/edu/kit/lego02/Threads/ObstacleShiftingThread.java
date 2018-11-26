package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.userIO.BrickScreen;
import lejos.hardware.sensor.SensorMode;

public class ObstacleShiftingThread implements Runnable {
	
	Robot robot;
	Drive drive;

	private final int BLUE = 2;
	private final float COLOR_BLUE = 0.1f;
	private final float DIST_THRESH = 0.1f;
	
	public ObstacleShiftingThread(Robot robot) {
		this.robot = robot;
		drive = robot.getDrive();
	}

    @Override
    public void run() {
        BrickScreen.show("Obstacle SHifting!!");
        
//        travelToBlueLine();
//        travelToUSSignal();
//        travelToBox();
        moveBoxToWall();
        moveBoxToCorner();
//        leaveArea();
    }

    private void travelToBlueLine(){
		BrickScreen.clearScreen();
		SensorMode colors = robot.getColors();
		float[] colorArray = new float[3];
		colors.fetchSample(colorArray, 0);
		float blue = colorArray[BLUE];
    	drive.turnLeftInPlace(15);
    	drive.travelFwd(100);
    	try{
	    	while(!(blue > 100)){
	    		Thread.sleep(5);
	    		colors.fetchSample(colorArray, 0);
	    		blue = colorArray[BLUE];
	    		BrickScreen.clearScreen();
	    		BrickScreen.show("Blau: " + blue);
	    	}
    	} catch(InterruptedException e){
    		
    	}
    }
    
    private void travelToUSSignal(){
    	drive.turnRightInPlace(45);
    	drive.travelFwd(100);
    	try{    		
	    	while(true){
	    		Thread.sleep(5);
	    	}
    	} catch(InterruptedException e){
    		
    	}    	
    }

    private void travelToBox(){
    	
    }
    
    
    private void moveBoxToWall(){
    	drive.travelArc(20, 110);
    	drive.travelFwd(100);
    }
    
    private void moveBoxToCorner(){
    	drive.travelBwd(15);
    	drive.turnRightInPlace(45);
    	drive.travelBwd(10);
    	drive.turnRightInPlace(90);
    	drive.travelFwd(30);
    	drive.turnRightInPlace(90);
    	drive.travelFwd(100);
    }
    
    private void leaveArea(){
    	
    }
}