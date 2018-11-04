package edu.kit.lego02.Threads.LineFollowing;

import edu.kit.lego02.Robot.Drive;
import edu.kit.lego02.Threads.LineFollowingThread;

public class GapState extends LineFollowingState {
	
	private final int STEP_SIZE = 0;
	private Drive drive;
	private int degree = 0;
	
	public GapState(LineFollowingThread thread) {
		super(thread);
		//TODO: in Superklasse verlegen?
		drive = thread.getRobot().getDrive();
	}

	@Override
    public void grey() {
		// TODO 
		nextState = new StandardLineFollowingState(thread);
	}
	
    @Override
    protected void entry() {
//        //Feststellen ob zum ersten Mal betreten
//    	//Beim ersten Mal erstmal geradeaus fahren + Fullstop?
//    	if(!firstTime){
//        	travelSearchAlgorithm();    		
//    	} else {
//        	drive.travelFwd(0);    		
//    	}
//    	//TODO: Wenn Gap überfahren und zurück auf Linie, checken auf welcher Seite der Linie
//    	//Kann eventuell von hier übergeben werden
    }
    
    public void travelSearchAlgorithm(){
//    	//Wahlweise Suche in Halbkreisen, ZickZack, ZickZackBögen, Schlangenlinie
//    	travelHalfCircles();
//    	//travelZigZag();
//    	//travelZigZagArcs();
//    	//travelSinuousLines();
    }
    
    //Wichtig nach 90 links, 180 rechts, 90 links muss Ausgangspunkt wieder erreicht sein
    //
    public void travelHalfCircles(){
//    	//Viertelkreis fahren in stepSize
//    	//Prüfen was besser inPlace oder turnSingleChain  
//		if (Math.abs(degree) < arcSize){
//			drive.turnInPlace(stepSize);
//			degree += stepSize;
//		} else {
//			setSearchState();
//    	}
    }
    
    public void setSearchState(){
//    	switch (searchState) {
//    		case LEFT:
//    			//Zuerst Linkschwenk 90°
//    			searchState = RIGHT;
//    			arcSize = HALF;
//    			break;
//    		case RIGHT:
//    			//Dann Rechtsschwenk 180°
//    			searchState = TO_START;
//    			arcSize = QUARTER;
//    			break;
//    		case TO_START:
//    			//Zurück zum Ausgang 90°
//    			searchState = NOT_FOUND;
//    			return;
//    		default:
//    			//ERROR
//    			break;
//    	}
//    	//Reset
//		degree = 0;
//		//Richtungsänderung
//		stepSize = -stepSize;
    }
    
    public void travelZigZag(){
    	
    }

    public void travelZigZagArcs(){
    }

    public void travelSinuousLines(){
    }

}
