package edu.kit.lego02.userIO;

import edu.kit.lego02.RobotStates;
import edu.kit.lego02.control.Controller;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.utility.TextMenu;

public class Menu extends TextMenu {

    public static final int MENU_ITEM_LINE_FOLLOWING = 0;
    public static final int MENU_ITEM_OBSTACLE_SHIFTING = 1;
    public static final int MENU_ITEM_BRIDGE = 2;
    public static final int MENU_ITEM_COLOR_SEARCH = 3;
    public static final int MENU_ITEM_PARKOUR = 4;

    private Controller controller;

    public Menu(Controller controller) {
        super(RobotStates.getRobotStatesList());
        this.controller = controller;

    }

    public void startUserInput() {

        initListeners();

        while (true) {

            int buttonID = Button.waitForAnyPress();

            if (buttonID == Button.ID_ENTER) {
                
                
                //stops running routine 
                controller.enterPressed();
                BrickScreen.clearScreen();
                switch (this.select()) {

                case MENU_ITEM_LINE_FOLLOWING:
                    BrickScreen.clearScreen();
                    BrickScreen.show(RobotStates.LINE_FOLLOWING.getStateName());
                    controller.stateChanged(RobotStates.LINE_FOLLOWING);
                    // fire LineFOllowingEvent
                    break;
                case MENU_ITEM_OBSTACLE_SHIFTING:
                    BrickScreen.clearScreen();
                    BrickScreen.show(RobotStates.OBSTACLE_SHIFTING.getStateName());
                    // fire ObstacleShiftigEvent
                    break;
                case MENU_ITEM_BRIDGE:
                    BrickScreen.clearScreen();
                    BrickScreen.show(RobotStates.BRIDGE.getStateName());
                    controller.stateChanged(RobotStates.BRIDGE);
                    break;

                case MENU_ITEM_COLOR_SEARCH:
                    BrickScreen.clearScreen();
                    BrickScreen.show(RobotStates.COLOR_SEARCH.getStateName());
                    // fire ColoSearchEvent
                    break;
                case MENU_ITEM_PARKOUR:
                    BrickScreen.clearScreen();
                    BrickScreen.show(RobotStates.PARKOUR.getStateName());
                    // fire Parcour Event
                    break;
                default:
                    // catch error

                }

            }

        }

    }

    private void initListeners() {
        Button.ESCAPE.addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(Key k) {
                // do nothing

            }

            @Override
            public void keyPressed(Key k) {
                Sound.beepSequence();
                System.exit(0);

            }
        });

    }

}
