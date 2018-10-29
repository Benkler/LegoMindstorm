package edu.kit.lego02.Threads;

/**
 * Abstract class to model a state that can be used by a LineFollowingThread.  
 * Concrete States must extend this class and overwrite methods for the actions they can react to. 
 * In a LineFollowingThread, an action is delegated to the current state like this:
 * action() {
 * 	currentState.action();
 *  currentState.changeState();
 * }
 */
public abstract class LineFollowingState {
	
	protected LineFollowingThread thread;
	protected LineFollowingState nextState;
	
	/**
	 * Constructor. Creates a new LineFollowingState. 
	 * @param thread The thread whose behavior this state defines. 
	 */
	public LineFollowingState(LineFollowingThread thread) {
		this.thread = thread;
	}
	
	/**
	 * Executed when entering this state. 
	 */
	protected void entry() {
		// empty
	}
	
	/**
	 * Executed when leaving this state. 
	 */
	protected void exit() {
		// empty
	}
	
	/**
	 * Executed when the color sensor detects grey. 
	 * Action depends on the current state of the object. 
	 */
	protected void grey() {
		throw new IllegalStateException("Error, transition in not defined.");
	}
	
	/**
	 * Executed when the color sensor detects white. 
	 * Action depends on the current state of the object. 
	 */
	protected void white() {
		throw new IllegalStateException("Error, transition in not defined.");
	}
	
	/**
	 * Executed when the color sensor detects black. 
	 * Action depends on the current state of the object. 
	 */
	protected void black() {
		throw new IllegalStateException("Error, transition in not defined.");
	}
	
	/**
	 * Executed when a left corner is detected (assuming the Robot is following the right edge of the line).
	 * Action depends on the current state of the object.  
	 */
	protected void cornerDetected() {
		throw new IllegalStateException("Error, transition in not defined.");
	}
	
	/**
	 * Executed when a gap is detected. 
	 * Action depends on the current state of the object. 
	 */
	protected void gapDetected() {
		throw new IllegalStateException("Error, transition in not defined.");
	}
	
	//protected abstract LineFollowingState next();
	
	/**
	 * Updates state variable of the object. Is the new state is different from the old one, 
	 * the corresponding exit und entry methods are called. 
	 */
	protected void changeState() {
		if (nextState != this) {
			this.exit();
			nextState.entry();
			thread.currentState = nextState;
		}
	}
}
