package org.usfirst.frc.team7327.robot;
import org.usfirst.frc.team7327.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShootModule{
    private Notifier ShootPID; 
    private double terror, berror, testPIDOutput,estimated; 
    private int topVel, botVel; 
    private volatile double PIDOutput = 0;
    static final double kP = .0002049999, kD = 0;
    private boolean on = false; 
    public ShootModule() { 
    	ShootPID = new Notifier(() ->  {
            terror = topError(); 
            berror = botError(); 
            SmartDashboard.putNumber("errorShoot", terror); 
            testPIDOutput = kP * terror - estimated; 
            testPIDOutput = Math.min(testPIDOutput, 1);
            PIDOutput = Math.max(testPIDOutput, -1); 
            if(on){Drivetrain.TopSpin(PIDOutput);}
            testPIDOutput = kP * berror + estimated; 
            testPIDOutput = Math.min(testPIDOutput, 1);
            PIDOutput = Math.max(testPIDOutput, -1); 
            if(on){Drivetrain.BotSpin(PIDOutput);}
            SmartDashboard.putNumber("PIDOutput: ", PIDOutput); 
    	}); 
    	ShootPID.startPeriodic(0.05);
    }
    
    public double topError(){return topVel +Drivetrain.TopVelocity();}
    public double botError(){return botVel +Drivetrain.BotVelocity();}
    public void setVelocity(int topspeed, int botspeed, double vEstimated){ topVel = topspeed; botVel = botspeed; estimated = vEstimated; }	 
    public double getPIDOutput(){try {return PIDOutput;} catch (Exception e) {return 0; }}
    public void setOn(boolean ON){ on = ON; }
}

