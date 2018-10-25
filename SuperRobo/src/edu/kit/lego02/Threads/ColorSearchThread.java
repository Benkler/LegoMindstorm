package edu.kit.lego02.Threads;

import edu.kit.lego02.userIO.BrickScreen;

public class ColorSearchThread implements Runnable {

    @Override
    public void run() {
        BrickScreen.show("ColoSearch ongoing");

    }

}
