package org.usfirst.frc.team7327.robot;

import org.usfirst.frc.team7327.robot.subsystems.Drivetrain;

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
    public static void Auto5(){}
    public static void Auto6(){}
    public static void Auto7(){}
    public static void Auto8(){}
    public static void Auto9(){}
    public static void Auto10(){}
    public static void Auto11(){}
    public static void Auto12(){}
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


    public static void BlueTrench5(){
        Robot.MoveTo(0,-2.223,0); //tune middle alue as needed
        Drivetrain.setIntakeMotors(0.75, DoubleSolenoid.Value.kForward);
        Robot.MoveTo(0,-5.446,0);
        Drivetrain.setIntakeMotors(0,DoubleSolenoid.Value.kReverse);
        Robot.MoveTo(7.228,0,0);
        Robot.LimeAlign();
        Drivetrain.Shoot(0.5, 0.5);
    }
    public static void RedSpinner6(){
        Robot.MoveTo(0,-3,0);
        Drivetrain.setIntakeMotors(0.75, DoubleSolenoid.Value.kForward);
        Robot.MoveTo(0,-3.941,0);
        Drivetrain.setIntakeMotors(0,DoubleSolenoid.Value.kReverse);
        Robot.MoveTo(2.458,3.941,0);
        Robot.LimeAlign();
        Drivetrain.Shoot(0.5, 0.5);
    }
    public static void DoubleCorner7(){
        Robot.MoveTo(0,-2.5,67.5);
        Robot.MoveTo(0,-0.7865,0);
        Drivetrain.setIntakeMotors(0.75, DoubleSolenoid.Value.kForward);
        Robot.MoveTo(0,-0.7865,0);
        Drivetrain.setIntakeMotors(0,DoubleSolenoid.Value.kReverse);
    }
    public static void point32(){
        Robot.LimeAlign();
        Drivetrain.Shoot(0.5, 0.5);
        Robot.MoveTo(1.7,-2.2,0);
        Drivetrain.setIntakeMotors(0.75, DoubleSolenoid.Value.kForward);
        Robot.MoveTo(1.7,-5,0);
        Robot.MoveTo(-.5,-3.360,101.25);
        Robot.MoveTo(-0.660,-2.970,101.25);
        Drivetrain.setIntakeMotors(0,DoubleSolenoid.Value.kReverse);
        Robot.MoveTo(0,-2.9,0);
        Robot.MoveTo(0,0,0);
        Robot.LimeAlign();
        Drivetrain.Shoot(0.5, 0.5);
    }
    public static void FarLeft(){
        Drivetrain.setIntakeMotors(0.75, DoubleSolenoid.Value.kForward);
        Robot.MoveTo(0,-3.4,0);
        Robot.MoveTo(-.535,-3.4,90);
        Drivetrain.setIntakeMotors(0,DoubleSolenoid.Value.kReverse);
        Robot.MoveTo(4.8,0,0);
        Robot.LimeAlign();
        Drivetrain.Shoot(0.5, 0.5);
        
    }
    public static void FarRight(){
        Robot.MoveTo(0,0,327.8);
        Drivetrain.Shoot(.5,.5);
        Drivetrain.setIntakeMotors(0.75, DoubleSolenoid.Value.kForward);
        Robot.MoveTo(0,-5,0);
        Drivetrain.setIntakeMotors(0,DoubleSolenoid.Value.kReverse);
        Robot.MoveTo(0,0,327.8);
        Robot.LimeAlign();
        Drivetrain.Shoot(0.5, 0.5);
    }
    public static void Middle(){
        Robot.MoveTo(0,-2.6,299.3);
        Drivetrain.setIntakeMotors(0.75, DoubleSolenoid.Value.kForward);
        Robot.MoveTo(-.66,-2.87,299.3);
        Robot.MoveTo(-.5,-3.26,299.3);
        Drivetrain.setIntakeMotors(0,DoubleSolenoid.Value.kReverse);
        Robot.MoveTo(0,0,0);
        Robot.LimeAlign();
        Drivetrain.Shoot(0.5, 0.5);
    }
    public static void Left (){
        Robot.MoveTo(0,0,67.2);
        Robot.LimeAlign();
        Drivetrain.Shoot(0.5, 0.5);
        Drivetrain.setIntakeMotors(0.75, DoubleSolenoid.Value.kForward);
        Robot.MoveTo(0,-2.4,353.6);
        Robot.MoveTo(-.4,-2.6,353.6);
        Robot.MoveTo(-.8,-2.8,353.6);
        Drivetrain.setIntakeMotors(0,DoubleSolenoid.Value.kReverse);
        Robot.MoveTo(1.3,0,0);
        Robot.LimeAlign();
        Drivetrain.Shoot(0.5, 0.5); 
    }

}
    


