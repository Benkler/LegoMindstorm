package edu.kit.lego02.Threads;

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