package edu.kit.lego02.Threads;

import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.userIO.BrickScreen;

public class LineFollowingThread implements Runnable {

    LineFollowingState currentState = new StandardLineFollowingState(this);

    Robot robot = null;

    public LineFollowingThread(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void run() {
        BrickScreen.show("Line Following Running");
        float min = 30.0f, max = 54.0f, // Diese Parameter adaptieren!!! element
                                        // [0,1]
                refLight = 42.0f, Mspeed = 80.0f, Mspeed2 = 15.0f;
        float readLight = 0.0f;

        try {
            while (true) {
                // Motor.C.forward();
                // Motor.B.forward();

                readLight = robot.getSensorValues().getColorValue(); // Vielleicht
                                                                     // den
                                                                     // anderen
                                                                     // wählen
                if (readLight < min) {
                    readLight = min + 1;
                }

                if (max < readLight) {
                    readLight = max - 1;
                }

                // Motor.B.setSpeed(Mspeed + (Mspeed2 * (readLight - min)));
                // Motor.C.setSpeed(Mspeed + (Mspeed2 * (max - readLight)));

                BrickScreen.clearScreen();
                BrickScreen.show("Sensorvalue" + readLight);

                Thread.sleep(500);

                // BrickScreen.displayInt(Mspeed * (readLight - min), 0, 1);
                // BrickScreen.displayInt(Mspeed * (max - readLight), 0, 2);

            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

// }

// }