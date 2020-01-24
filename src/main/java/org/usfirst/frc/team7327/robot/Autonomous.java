package org.usfirst.frc.team7327.robot;

public class Autonomous {
    public static void Auto(){
        Robot.MoveTo(0, 0.25,0);
        //Robot.MoveTo(.25,0,90); 
        //Robot.MoveTo(0,-.25, 180);
        //Robot.MoveTo(-.25,0,270);
        Robot.MoveTo(.25, 0, 90);
        Robot.MoveTo(0, -0.25, 180);
        Robot.MoveTo(-.25, 0, 270);
        

    }   
}
                                      
    
