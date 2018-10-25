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
        ArrayList<String> robotStatesList = new ArrayList<>();  
        EnumSet.allOf(RobotStates.class)
            .forEach(element -> robotStatesList.add(element.getStateName()));

        return robotStatesList.toArray(new String[robotStatesList.size()]);
    }
  
    
    
    

}
