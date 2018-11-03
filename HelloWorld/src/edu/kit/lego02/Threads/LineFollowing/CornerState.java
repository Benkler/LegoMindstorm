package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Threads.LineFollowingThread;

public class CornerState extends LineFollowingState {

	public CornerState(LineFollowingThread thread) {
		super(thread);
	}
	
	@Override
    public void grey() {
		// TODO
		nextState = new StandardLineFollowingState(thread);
	}

    @Override
    protected void entry() {
        
    }
	
	
}