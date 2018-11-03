package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Threads.LineFollowingThread;

public class CheckForGapState extends LineFollowingState {
	
	public CheckForGapState(LineFollowingThread thread) {
		super(thread);
	}

	@Override
    public void cornerDetected() {
		// TODO 
		nextState = new CornerState(thread);
	}

	@Override
    public void gapDetected() {
		// TODO 
		nextState = new GapState(thread);
	}
	
	
}
