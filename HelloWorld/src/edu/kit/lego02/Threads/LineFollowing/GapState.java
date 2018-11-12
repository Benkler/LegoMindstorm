package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Threads.LineFollowingThread;

public class GapState extends LineFollowingState {
	
	private final int LEFT = 0;
	private final int RIGHT = 1;
	private final int TO_START = 2;
	private final int NOT_FOUND = 3;
	private final int GAP_SIZE = 18;
	
	private final int STEP_SIZE = 3;
	private final int QUARTER = 90;
	private final int HALF = 2 * QUARTER;
	private Drive drive;
	private int degree;
	private int arcSize;
	private int stepSize;
	private int searchState;
	
	public GapState(LineFollowingThread thread) {
		super(thread);
		//TODO: in Superklasse verlegen?
		stepSize = STEP_SIZE;
		drive = thread.getRobot().getDrive();
		degree = 0;
		searchState = LEFT;
	}

	@Override
    public void grey() {
		// TODO
		degree = 0;
		searchState = LEFT;
		nextState = new StandardLineFollowingState(lineFollowThread);

	}
	
    @Override
    protected void entry() {
        //Feststellen ob zum ersten Mal betreten
    	//Beim ersten Mal erstmal geradeaus fahren + Fullstop?
        drive.travelFwd(GAP_SIZE);
        travelSearchAlgorithm();
    	//TODO: Wenn Gap überfahren und zurück auf Linie, checken auf welcher Seite der Linie
    	//Kann eventuell von hier übergeben werden
    }
    
    public void travelSearchAlgorithm(){
    	//Wahlweise Suche in Halbkreisen, ZickZack, ZickZackBögen, Schlangenlinie
    	travelHalfCircles();
    	//travelZigZag();
    	//travelZigZagArcs();
    	//travelSinuousLines();
    }
    
    //Wichtig nach 90 links, 180 rechts, 90 links muss Ausgangspunkt wieder erreicht sein
    //
    public void travelHalfCircles(){
    	//Viertelkreis fahren in stepSize
    	//Prüfen was besser inPlace oder turnSingleChain
		while(degree < arcSize){
			degree += STEP_SIZE;
			drive.turnInPlace(stepSize);
        	if (!lineFollowThread.isBlack(lineFollowThread.getRobot().
        			getSensorValues().getColorValue())){
        		return;
        	}
		}
		//Zurück drehen
		drive.turnInPlace(-arcSize);
		//Andere Richtung
		stepSize = -STEP_SIZE;
		degree = 0;
		while(degree < arcSize){
			degree += STEP_SIZE;
			drive.turnInPlace(stepSize);
        	if (!lineFollowThread.isBlack(lineFollowThread.getRobot().
        			getSensorValues().getColorValue())){
        		return;
        	}
		}
		//Zurück drehen
		drive.turnInPlace(arcSize);
    }
        
    public void travelZigZag(){
    	
    }

    public void travelZigZagArcs(){
    }

    public void travelSinuousLines(){
    }

}
