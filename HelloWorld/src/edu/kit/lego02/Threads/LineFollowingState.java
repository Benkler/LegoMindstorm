package edu.kit.lego02.Threads;

public abstract class LineFollowingState {
	
	protected LineFollowingThread thread;
	protected LineFollowingState nextState;
	
	public LineFollowingState(LineFollowingThread thread) {
		this.thread = thread;
	}
	
	protected void entry() {
		// empty
	}
	
	protected void exit() {
		// empty
	}
	
	protected void grey() {
		throw new IllegalStateException("Error, transition in not defined.");
	}
	
	protected void white() {
		throw new IllegalStateException("Error, transition in not defined.");
	}
	
	protected void black() {
		throw new IllegalStateException("Error, transition in not defined.");
	}
	
	protected void cornerDetected() {
		throw new IllegalStateException("Error, transition in not defined.");
	}
	
	protected void gapDetected() {
		throw new IllegalStateException("Error, transition in not defined.");
	}
	
	//protected abstract LineFollowingState next();
	
	protected void changeState() {
		if (nextState != this) {
			this.exit();
			nextState.entry();
			thread.currentState = nextState;
		}
	}
}
