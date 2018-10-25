package edu.kit.lego02.Threads;

import java.util.concurrent.TimeUnit;

import edu.kit.lego02.userIO.BrickScreen;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class LineFollowingThread implements Runnable {

    @Override
    public void run() {
        BrickScreen.show("Line FOllowing Running");

        
        
        
        //Sample driving
        DifferentialPilot pilot = new DifferentialPilot(3.6f, 19.0f, Motor.A, Motor.B);

        pilot.travel(-35);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
