package org.usfirst.frc.team7327.robot;
import org.usfirst.frc.team7327.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShootModule{
    private Notifier ShootPID; 
    private double error, diffError, lastError, testPIDOutput; 
    private int topVel; 
    private volatile double PIDOutput = 0;
<<<<<<< HEAD
    static final double kP = .0002049999, kD = 0; 
=======
    static final double kP = .001, kD = 0; 
>>>>>>> 37bd622878286f71d0606b7fdb843dca8abca53d
    public ShootModule() {
    	lastError = getError(); 
    	ShootPID = new Notifier(() ->  {
            error = getError(); 
            SmartDashboard.putNumber("errorShoot", error); 
    		diffError = lastError - error; 
            testPIDOutput = kP * error + kD * diffError; 
            testPIDOutput = Math.min(testPIDOutput, 1);
            PIDOutput = Math.max(testPIDOutput, -1); 
            Drivetrain.Shoot(PIDOutput);
            lastError = error; 
            SmartDashboard.putNumber("PIDOutput: ", PIDOutput); 
    	}); 
    	ShootPID.startPeriodic(0.05);
    }
    
    public double getError(){return topVel +Drivetrain.TopVelocity();}
    public void setVelocity(int speed){ topVel = speed; }	 
    public double getPIDOutput(){try {return PIDOutput;} catch (Exception e) {return 0; }}
}

