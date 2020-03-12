package org.usfirst.frc.team7327.robot;
import org.usfirst.frc.team7327.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Notifier;

public class ShootModule{
    private Notifier ShootPID; 
    private double error, diffError, lastError, testPIDOutput; 
    private int topVel; 
    private volatile double PIDOutput = 0;
    static final double kP = 1, kD = .1; 
    public ShootModule() {
    	lastError = getError(); 
    	ShootPID = new Notifier(() ->  {
    		error = getError(); 
    		diffError = lastError - error; 
            testPIDOutput = kP * error + kD * diffError; 
            testPIDOutput = Math.min(testPIDOutput, .5);
            PIDOutput = Math.max(testPIDOutput, -.5); 
            lastError = error; 
    	}); 
    	ShootPID.startPeriodic(0.05);
    }
    
    public double getError(){return Drivetrain.TopVelocity() - topVel;}
    public void setVelocity(int speed){ topVel = speed; }	 
    public double getPIDOutput(){try {return PIDOutput;} catch (Exception e) {return 0; }}
}

