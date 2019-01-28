package edu.kit.lego02.control;

import edu.kit.lego02.RobotStates;
import edu.kit.lego02.Robot.Robot;
import edu.kit.lego02.Threads.BridgeThread;
import edu.kit.lego02.Threads.ColorSearchThread;
import edu.kit.lego02.Threads.LineFollowingThread;
import edu.kit.lego02.Threads.ObstacleShiftingThread;
import edu.kit.lego02.Threads.ParkourThread;
import edu.kit.lego02.userIO.BrickScreen;
import edu.kit.lego02.userIO.Menu;

public class Controller {

    Menu menu = null;
    Thread routineThread = null;
    Robot robot = null;

    public Controller() {

        this.menu = new Menu(this);

    }

    public void startRobot() {
        // init Robot etc...
        this.robot = new Robot();

        // TODO starte Men� als Thread?!?!
        menu.startUserInput();

    }

    public void stateChanged(RobotStates state) {

        switch (state) {
        case LINE_FOLLOWING:
            // do lineFOllowing

            
            try {
                routineThread = new Thread(new LineFollowingThread(robot));
                routineThread.start();
                routineThread.join();
                
                
                
                routineThread = new Thread(new ObstacleShiftingThread(robot));
                routineThread.start(); 
                routineThread.join();
                
                routineThread = new Thread(new BridgeThread(robot));
                routineThread.start();
                routineThread.join();
                
                routineThread = new Thread(new ColorSearchThread(robot));
                routineThread.start();
                routineThread.join();
                
                
                
                
                
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            break;
        case BRIDGE:
            
            try {
                
                routineThread = new Thread(new BridgeThread(robot));
                routineThread.start();
                routineThread.join();
                
                routineThread = new Thread(new ColorSearchThread(robot));
                routineThread.start();
                routineThread.join();
                
                
                
                
                
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            break;

        case COLOR_SEARCH:
            
            try {
        
                
                routineThread = new Thread(new ColorSearchThread(robot));
                routineThread.start();
                routineThread.join();

                
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            break;

        case OBSTACLE_SHIFTING:
            
            try {
             

                routineThread = new Thread(new ObstacleShiftingThread(robot));
                routineThread.start(); 
                routineThread.join();
                
                routineThread = new Thread(new BridgeThread(robot));
                routineThread.start();
                routineThread.join();
                
                routineThread = new Thread(new ColorSearchThread(robot));
                routineThread.start();
                routineThread.join();
                
                
                
                
                
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            break;

        case PARKOUR:
            
            try {
                routineThread = new Thread(new LineFollowingThread(robot));
                routineThread.start();
                routineThread.join();
                
                
                
                routineThread = new Thread(new ObstacleShiftingThread(robot));
                routineThread.start(); 
                routineThread.join();
                
                routineThread = new Thread(new BridgeThread(robot));
                routineThread.start();
                routineThread.join();
                
                routineThread = new Thread(new ColorSearchThread(robot));
                routineThread.start();
                routineThread.join();
                
                
                
                
                
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        
            break;

        default:
            break;
        }

    }

    public void enterPressed() {
        // VIelleicht hier noch RobotReset falls Motoren weiter drehen
        if (routineThread == null) {
            return;
        }
        try {
            routineThread.interrupt();
            while(routineThread.isAlive()){
                
            };
            robot.getDrive().stopMotors();
            routineThread = null;
        } catch (Exception e) {
            BrickScreen.clearScreen();
            BrickScreen.displayString("Exception in Controller", 0, 0);
        }

      //  BrickScreen.clearScreen();
    }

}
