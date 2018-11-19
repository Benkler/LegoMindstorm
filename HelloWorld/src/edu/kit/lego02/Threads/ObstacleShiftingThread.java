package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.userIO.BrickScreen;

public class ObstacleShiftingThread implements Runnable {
	
	Robot robot;
	Drive drive;
	
	public ObstacleShiftingThread(Robot robot) {
		this.robot = robot;
		drive = robot.getDrive();
	}

    @Override
    public void run() {
        BrickScreen.show("Obstacle SHifting");
        
        drive.turnRightInPlace(45);
        
        //control for finding the box
        
    }

}
