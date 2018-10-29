package edu.kit.lego02;

import java.awt.List;
import java.util.ArrayList;
import java.util.EnumSet;

public enum RobotStates {
    
   
    
    LINE_FOLLOWING("Line following",0), 
    OBSTACLE_SHIFTING("Obstacle shifting",1), 
    BRIDGE("Bridge",2),
    COLOR_SEARCH("ColorSearch",3),
    PARKOUR("Parkour",4);

    private String stateName;
    private int stateId;
    
  


    private RobotStates(String stateName, int stateId) {
        this.stateName = stateName;
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }


    public int getStateId() {
        return stateId;
    }

   

    public static String[] getRobotStatesList(){
        
     String [] returnValue = new String[5];
     returnValue[0] = LINE_FOLLOWING.stateName;
     returnValue[1] = OBSTACLE_SHIFTING.getStateName();
     returnValue[2] = BRIDGE.getStateName();
     returnValue[3] = COLOR_SEARCH.getStateName();
     returnValue[4] = PARKOUR.getStateName();
     

        return returnValue;
    }
  
    
    
    

}
