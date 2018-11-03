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
	
	@Override
	protected void white() {
		// TODO
		nextState = new CornerState(thread); 
	}
	
	@Override
	protected void black() {
		// TODO
		nextState = new CheckForGapState(thread); 
	}
}