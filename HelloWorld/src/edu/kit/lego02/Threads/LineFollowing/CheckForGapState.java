package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Threads.CornerState;
import edu.kit.lego02.Threads.LineFollowingThread;

public class CheckForGapState extends LineFollowingState {
	
	public CheckForGapState(LineFollowingThread thread) {
		super(thread);
	}

	@Override
	protected void cornerDetected() {
		// TODO 
		nextState = new CornerState(thread);
	}

	@Override
	protected void gapDetected() {
		// TODO 
		nextState = new GapState(thread);
	}
	
	
}
