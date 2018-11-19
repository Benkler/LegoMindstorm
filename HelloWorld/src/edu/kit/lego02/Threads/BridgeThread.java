package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.userIO.BrickScreen;

public class BridgeThread implements Runnable {
	
	Robot robot;
	Drive drive;
	
	public BridgeThread(Robot robot) {
		this.robot = robot;
		this.drive = robot.getDrive();
	}
	
    @Override
    public void run() {
       BrickScreen.show("Bridge Crossing");
       
       drive.travelFwd(50);
       drive.turnLeftInPlace(90);
       drive.travelFwd(100);
       drive.turnLeftInPlace(90);
       drive.travelFwd(50);
    }

}
