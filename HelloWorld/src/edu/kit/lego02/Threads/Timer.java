package edu.kit.lego02.Threads;

public class Timer implements Runnable {

	private int time;
	private BridgeThread caller;
	
	public Timer(int time, BridgeThread caller) {
		this.time = time;
		this.caller = caller;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//caller.signalTimeout();
	}

}
