package org.usfirst.frc.team7327.robot;

public class Autonomous {
    public static void Auto(){
        Robot.MoveTo(0, 0.5,0);
        Robot.MoveTo(.5, 0, 90);
        Robot.MoveTo(0, -0.5, 180);
        Robot.MoveTo(-.5, 0, -90);
        Robot.MoveTo(0,0,0); 
        System.out.println("finished!");
        
    }   
}
                                      
    
