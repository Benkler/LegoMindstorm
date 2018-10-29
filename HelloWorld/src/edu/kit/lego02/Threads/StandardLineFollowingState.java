package edu.kit.lego02.Threads;

public class StandardLineFollowingState extends LineFollowingState {
	
	public StandardLineFollowingState(LineFollowingThread thread) {
		super(thread);
	}
	
	@Override
	protected void grey() {
		// TODO
		nextState = this;
	}
	
	protected void white() {
		// TODO
		// nextState = ...
	}
	
	protected void black() {
		// TODO
		// nextState = ...
	}
}