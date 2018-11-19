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

        // TODO starte Menü als Thread?!?!
        menu.startUserInput();

    }

    public void stateChanged(RobotStates state) {

        switch (state) {
        case LINE_FOLLOWING:
            // do lineFOllowing

            routineThread = new Thread(new LineFollowingThread(robot));

            routineThread.start();

            break;
        case BRIDGE:
            routineThread = new Thread(new BridgeThread());
            routineThread.start();
            break;

        case COLOR_SEARCH:
            routineThread = new Thread(new ColorSearchThread());
            routineThread.start();
            break;

        case OBSTACLE_SHIFTING:
            routineThread = new Thread(new ObstacleShiftingThread());
            routineThread.start();
            break;

        case PARKOUR:
            routineThread = new Thread(new ParkourThread());
            routineThread.start();
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
            routineThread = null;
        } catch (Exception e) {
            // TODO: handle exception
        }

        BrickScreen.clearScreen();
    }

}
