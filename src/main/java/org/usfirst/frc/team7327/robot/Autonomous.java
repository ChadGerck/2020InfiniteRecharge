package org.usfirst.frc.team7327.robot;

public class Autonomous {
    public static void Auto(){
        //x,y,angle
        Robot.MoveTo(0, 0.25,0);
        Robot.MoveTo(.25, 0, 90);
        Robot.MoveTo(0, -0.25, 180);
        Robot.MoveTo(-.25, 0, -90);
        Robot.MoveTo(0, 0, 0);
        
    }   
//use -180 to 180; ie, 270 = -90
    public static void Auto2(){
        Robot.MoveTo(0,0.30,0);
        Robot.MoveTo(0,.20,90);
        Robot.MoveTo(0,0.25,180);
        Robot.MoveTo(-.25,0,-90);
                                      
    }

    public static void Auto3(){
        Robot.MoveTo(0.20,0,0);
        Robot.MoveTo(0,-.20,0);
        Robot.MoveTo(.25,0,0);
        Robot.MoveTo(-.25,0,0);
        Robot.MoveTo(0,0,0);
    }

}

