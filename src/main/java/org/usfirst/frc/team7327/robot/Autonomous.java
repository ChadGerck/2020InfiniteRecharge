package org.usfirst.frc.team7327.robot;
import org.usfirst.frc.team7327.robot.subsystems.Drivetrain;

// import org.usfirst.frc.team7327.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.DoubleSolenoid;


public class Autonomous {    

    public static void Auto(){
        //x,y,angle
        Robot.MoveTo(0, 0.25,0);
        Robot.SleepFor(2);
        Robot.MoveTo(.25, 0, 90);
        Robot.SleepFor(2);
        Robot.MoveTo(0, -0.25, 180);
        Robot.SleepFor(2);
        Robot.MoveTo(-.25, 0, -90);
        Robot.SleepFor(2);
        Robot.MoveTo(0, 0, 0);
        Robot.LimeAlign();
    }   
//use -180 to 180; ie, 270 = -90
    public static void Auto2(){
        Robot.MoveTo(0,0.30,0);
        Robot.MoveTo(0,.20,90);
        Robot.MoveTo(0,0.25,180);
        Robot.MoveTo(-.25,0,-90);
        Robot.LimeAlign();                          
    }

    public static void Auto3(){
        Robot.MoveTo(0.20,0,0);
        Robot.MoveTo(0,-.20,0);
        Robot.MoveTo(.25,0,0);
        Robot.MoveTo(-.25,0,0);
        Robot.MoveTo(0,0,0);
        Robot.LimeAlign();
    }
    public static void Auto4(){
        Robot.MoveTo(0,5.5,0); 
        Robot.MoveTo(1,5.5,180);
        Robot.MoveTo(0,0,0); 
        Robot.LimeAlign();
    }
    public static void LeftRealign(){
        Drivetrain.TopSpin(.5);
        Drivetrain.BotSpin(.5);
        Robot.SleepFor(5);
        Drivetrain.setBallSpeed(-.25);
        Robot.SleepFor(4); 
        Robot.MoveTo(0, 1, -20);
    }
    public static void Auto6(){}
    public static void Auto7(){}
    public static void Auto8(){}
    public static void MidDefault(){
        Drivetrain.TopSpin(.315);
        Drivetrain.BotSpin(.315);
        Robot.SleepFor(5);
        Drivetrain.setBallSpeed(-.25);
        Robot.SleepFor(4); 
        Robot.MoveTo(0, 1, 180);
    }
    public static void MidPlayerStation(){
        Drivetrain.TopSpin(.315);
        Drivetrain.BotSpin(.315);
        Robot.SleepFor(5);
        Drivetrain.setBallSpeed(-.25);
        Robot.SleepFor(4); 
        Robot.MoveTo(0, 6.5, 180);
    }
    public static void MidRight2Balls(){
        Drivetrain.TopSpin(.315);
        Drivetrain.BotSpin(.315);
        Robot.SleepFor(5);
        Drivetrain.setBallSpeed(-.25);
        Robot.SleepFor(4); 
        Robot.MoveTo(0,-2.6,119.3);
        Drivetrain.setIntakeMotors(0.5, DoubleSolenoid.Value.kForward);
        Robot.MoveTo(-.66,-2.87,119.3);
        Robot.MoveTo(-.5,-3.26,119.3); 
        Drivetrain.setIntakeMotors(0,DoubleSolenoid.Value.kReverse);
    }
    public static void MidRight3Balls(){
        Drivetrain.TopSpin(.315);
        Drivetrain.BotSpin(.315);
        Robot.SleepFor(5);
        Drivetrain.setBallSpeed(-.25);
        Robot.SleepFor(4); 
        
    }
    public static void Auto13(){}
    public static void Auto14(){}
    public static void Auto15(){}
    public static void Auto16(){}
    public static void Auto17(){}
    public static void Auto18(){}
    public static void Auto19(){}
    public static void Auto20(){}
    public static void Auto21(){}
    public static void Auto22(){}
    public static void Auto23(){}
    public static void Auto24(){}
    public static void Auto25(){}
    public static void Auto26(){}

    public static void AutoTest(){
        Robot.MoveTo(0, 1, 0);
    }
    // public static void BlueTrench5(){
    //     Robot.MoveTo(0,-2.223,0); //tune middle alue as needed
    //     Drivetrain.setIntakeMotors(0.75, DoubleSolenoid.Value.kForward);
    //     Robot.MoveTo(0,-5.446,0);
    //     Drivetrain.setIntakeMotors(0,DoubleSolenoid.Value.kReverse);
    //     Robot.MoveTo(7.228,0,0);
    //     Robot.LimeAlign();
    //     Drivetrain.Shoot(0.5, 0.5);
    // }
    // public static void RedSpinner6(){
    //     Robot.MoveTo(0,-3,0);
    //     Drivetrain.setIntakeMotors(0.75, DoubleSolenoid.Value.kForward);
    //     Robot.MoveTo(0,-3.941,0);
    //     Drivetrain.setIntakeMotors(0,DoubleSolenoid.Value.kReverse);
    //     Robot.MoveTo(2.458,3.941,0);
    //     Robot.LimeAlign();
    //     Drivetrain.Shoot(0.5, 0.5);
    // }
    // public static void DoubleCorner7(){
    //     Robot.MoveTo(0,-2.5,67.5);
    //     Robot.MoveTo(0,-0.7865,0);
    //     //intake
    //     Robot.MoveTo(0,-0.7865,0);
    // }
    // public static void point32(){
    //     Robot.LimeAlign();
    //     Drivetrain.Shoot(0.5, 0.5);

    // }
        

    }
    


