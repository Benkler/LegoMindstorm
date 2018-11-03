package edu.kit.lego02.Threads;

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
