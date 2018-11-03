package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Threads.LineFollowingThread;

public class GapState extends LineFollowingState {
	
	public GapState(LineFollowingThread thread) {
		super(thread);
	}

	@Override
	protected void grey() {
		// TODO 
		nextState = new StandardLineFollowingState(thread);
	}
	
	
}
