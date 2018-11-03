package edu.kit.lego02.Threads;

import edu.kit.lego02.Threads.LineFollowing.LineFollowingState;
import edu.kit.lego02.Threads.LineFollowing.StandardLineFollowingState;

public class CornerState extends LineFollowingState {

	public CornerState(LineFollowingThread thread) {
		super(thread);
	}
	
	@Override
	protected void grey() {
		// TODO
		nextState = new StandardLineFollowingState(thread);
	}
}