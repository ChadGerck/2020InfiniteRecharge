package org.usfirst.frc.team7327.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team7327.robot.Robot;

import org.usfirst.frc.team7327.robot.SwerveMath;

import static org.usfirst.frc.team7327.robot.Robot.oi;
// import org.usfirst.frc.team7327.robot.ElevatorPositions;

public class Drive extends Command {
  public Drive() { requires(Robot.Drivetrain); }
  protected void initialize() { }
  public static XboxController P1 = oi.Controller0;//, P2 = oi.Controller1;  
  double finalAngle, Redthrottle, ballThrottle, rotMag, rightArc, directMag, steering_adjust, x; 
  double SteerP = -0.025;
  boolean fixRotation, rocketAngle = true, evadeMode = false; 

  private ShuffleboardTab tab = Shuffleboard.getTab("RocketAngle");
  private NetworkTableEntry angleR = tab.add("RocketAngle", rocketAngle).getEntry();
  DoubleSolenoid.Value Pincher, Extendor, pullout = Value.kOff; 




  protected void execute() {

    //if(Robot.oi.BackButton(P2)){ if(oi.LEDValue() == 1 || oi.LEDValue() == 0){ oi.LEDOn(); } else if(oi.LEDValue() == 3){ oi.LEDOff(); }}
    if(Robot.oi.BackButton(P1)){ if(rocketAngle){ rocketAngle = false;} else{ rocketAngle = true; } angleR.setBoolean(rocketAngle); }

    if(oi.RSClick(P1)){if(evadeMode){evadeMode=false;}else{evadeMode=true;}}

    SmartDashboard.putBoolean("evademode: ", evadeMode); 
    //SmartDashboard.putNumber("NavAngle: ", Robot.NavAngle()); 
    if(evadeMode){ rotMag = oi.RightX(P1); }
    else if(oi.RightMag(P1) > .7  || oi.XButtonDown(P1) || oi.YButtonDown(P1) || oi.BButtonDown(P1) || oi.LeftTrigger(P1) > .1 || oi.RightTrigger(P1) > .1){
      if(oi.RightMag(P1) > .7) { rightArc = oi.RightArc(P1); }
      else if(oi.XButtonDown(P1) && rocketAngle) { rightArc = 135; } else if(oi.XButtonDown(P1) && !rocketAngle){ rightArc = 225; }
      else if(oi.YButtonDown(P1) && rocketAngle) { rightArc = 45;  } else if(oi.YButtonDown(P1) && !rocketAngle){ rightArc = 315; }
      else if(oi.LeftTrigger(P1) > .1) { rightArc = 270; }           else if(oi.RightTrigger(P1) > .1) {rightArc = 90; }
      else if(oi.BButtonDown(P1)){ rightArc = 0; }
      try { Robot.Drivetrain.turning.setYaw(rightArc - Robot.NavAngle());} catch (Exception e) {}
      rotMag = Robot.Drivetrain.turning.getPIDOutput();
    } else{ rotMag = 0; }

    if( oi.AButtonDown(P1)){ 
      x = oi.LimelightTx(); if(x >= -3 && x <= 3){ steering_adjust = 0; }else{ steering_adjust = SteerP*-x; } 
      finalAngle = Math.toDegrees(Math.atan2(oi.LeftY(P1),steering_adjust))-90; directMag = (Math.abs(steering_adjust) + Math.abs(oi.LeftY(P1)))/2; 
    }else if(oi.LeftMag(P1) >= .2){ finalAngle = Math.toDegrees(Math.atan2(oi.LeftX(P1), oi.LeftY(P1))) + Robot.NavAngle(); directMag = .5*oi.LeftMag(P1); }
    else if(oi.RightBumperDown(P1)) { finalAngle = 90; directMag = .05; } else if(oi.LeftBumperDown(P1)) { finalAngle = 270; directMag = .05; }
    else { directMag = 0; }

    if(oi.LeftBumperDown(P1) || oi.RightBumperDown(P1) || oi.RightTrigger(P1) > .1 || oi.LeftTrigger(P1) > .1 || oi.LeftMag(P1) >= 0.2 || oi.RightMag(P1) > 0.7 || oi.AButtonDown(P1) || oi.XButtonDown(P1) || oi.YButtonDown(P1) || oi.BButtonDown(P1)) {
      fixRotation = false; 
    } else{fixRotation = true;}
    SwerveMath.ComputeSwerve(finalAngle, directMag, rotMag, fixRotation); 

    SmartDashboard.putNumber ("Angle", Robot.NavAngle());

		
		// if(oi.RightBumperDown(P2)) { Redthrottle = .6; } else if(oi.LeftBumperDown(P2)) { Redthrottle = -.6;}
		// else { Redthrottle = 0; } Robot.Drivetrain.setRawIntake(Redthrottle);
		// if(oi.RightMag(P2) > .3) { ballThrottle = .75*oi.RightY(P2); } else if(oi.RightBumperDown(P2)) { ballThrottle = .5; }
    // else{ ballThrottle = 0; } Robot.Drivetrain.setRawBallIn(ballThrottle); 
    //SmartDashboard.putNumber("this", Robot.NavAngle());
    //
    //if(oi.StartButton(P1)) { Robot.nav.reset(); } //if(oi.StartButton(P2)) { Robot.Drivetrain.ResetElevator(); }
    // ElevatorPositions.MoveElevators();

    // if(oi.XButton(P2)){ Pincher = Value.kForward; } else if(oi.BButton(P2)){ Pincher = Value.kReverse; }
    // else { Pincher = Value.kOff; } Robot.Drivetrain.setPincher(Pincher);
    // if(oi.YButton(P2)){Extendor = Value.kForward; } else if(oi.AButton(P2)){Extendor = Value.kReverse; }
    // else { Extendor = Value.kOff; } Robot.Drivetrain.setExtendor(Extendor);
    // if(oi.LeftY(P2) > .7){ pullout = Value.kForward; } else if(oi.LeftY(P2) < -.7){ pullout = Value.kReverse; }
    // else { pullout = Value.kOff; } Robot.Drivetrain.setPullout(pullout);

  }
  protected boolean isFinished() { return false;}
  protected void end() {}

}
