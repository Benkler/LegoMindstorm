package edu.kit.lego02.Threads;

import edu.kit.lego02.userIO.BrickScreen;

public class BridgeThread implements Runnable {

    @Override
    public void run() {
       BrickScreen.show("Brisge Crossing");

    }

}
