package org.usfirst.frc.team7327.robot;

public class Autonomous {
    public static void Auto(){
        //x,y,angle
        Robot.MoveTo(0, 0.25,0);
        Robot.MoveTo(.25, 0, 90);
        Robot.MoveTo(0, -0.25, 180);
        Robot.MoveTo(-.25, 0, 270);
        

    }   

    public static void Auto2(){
        Robot.MoveTo(0,0.30,0);
        Robot.MoveTo(0,.20,90);
        Robot.MoveTo(0,0.25,180);
        Robot.MoveTo(-.25,0,270);
                                      
    }


}


