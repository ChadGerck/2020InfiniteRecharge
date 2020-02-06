package org.usfirst.frc.team7327.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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
  public Drive() { requires(Robot.swerve); }
  protected void initialize() { }
  double finalAngle, Redthrottle, ballThrottle, rotMag, rightArc, directMag, steering_adjust, x; 
  double SteerP = -0.025;
  boolean fixRotation, rocketAngle = true, evadeMode = false; 

  private ShuffleboardTab tab = Shuffleboard.getTab("RocketAngle");
  private NetworkTableEntry angleR = tab.add("RocketAngle", rocketAngle).getEntry();
  DoubleSolenoid.Value Pincher, Extendor, pullout = Value.kOff; 

  protected void execute() {

    if(Robot.oi.BackButton(1)){ if(oi.LEDValue() == 1 || oi.LEDValue() == 0){ oi.LEDOn(); } else if(oi.LEDValue() == 3){ oi.LEDOff(); }}
    //if(Robot.oi.BackButton(2)){ if(rocketAngle){ rocketAngle = false;} else{ rocketAngle = true; } angleR.setBoolean(rocketAngle); }

    if(oi.RSClick(1)){if(evadeMode){evadeMode=false;}else{evadeMode=true;}}

    SmartDashboard.putBoolean("evademode: ", evadeMode); 
    //SmartDashboard.putNumber("NavAngle: ", Robot.NavAngle()); 
    if(evadeMode && oi.RightMag(1)>.3){ rotMag = -0.5*oi.RightX(1); }
    else if(oi.RightMag(1) > .7  || oi.XButtonDown(1) || oi.YButtonDown(1) || oi.BButtonDown(1) || oi.LeftTrigger(1) > .1 || oi.RightTrigger(1) > .1){
      if(oi.RightMag(1) > .7) { rightArc = -oi.RightArc(1); }
      else if(oi.XButtonDown(1) && rocketAngle) { rightArc = 135; } else if(oi.XButtonDown(1) && !rocketAngle){ rightArc = 225; }
      else if(oi.YButtonDown(1) && rocketAngle) { rightArc = 45;  } else if(oi.YButtonDown(1) && !rocketAngle){ rightArc = 315; }
      else if(oi.BButtonDown(1)){ rightArc = 0; }
      try { Robot.swerve.turning.setYaw(rightArc + Robot.NavAngle());} catch (Exception e) {}
      rotMag = Robot.swerve.turning.getPIDOutput();
    } else{ rotMag = 0; }

    if( oi.AButtonDown(1)){ 
      x = oi.LimelightTx(); if(x >= -3 && x <= 3){ steering_adjust = 0; }else{ steering_adjust = SteerP*-x; } 
      finalAngle = Math.toDegrees(Math.atan2(oi.LeftY(1),steering_adjust))-90; directMag = (Math.abs(steering_adjust) + Math.abs(oi.LeftY(1)))/2; 
    }else if(oi.LeftMag(1) >= .2){ finalAngle = Math.toDegrees(Math.atan2(oi.LeftY(1), oi.LeftX(1))) - Robot.NavAngle(); directMag = 0.25* oi.LeftMag(1); }
    else if(oi.RightBumperDown(1)) { finalAngle = 0; directMag = .05; } else if(oi.LeftBumperDown(1)) { finalAngle = 180; directMag = .05; }
    else if(oi.LeftTrigger(1) > .1) { finalAngle = 90; directMag = .125; } else if(oi.RightTrigger(1) > .1) {finalAngle = 270; directMag = .125; }
    else { directMag = 0; }

    if(oi.LeftBumperDown(1) || oi.RightBumperDown(1) || oi.RightTrigger(1) > .1 || oi.LeftTrigger(1) > .1 || oi.LeftMag(1) >= 0.2 || oi.RightMag(1) > 0.3 || oi.AButtonDown(1) || oi.XButtonDown(1) || oi.YButtonDown(1) || oi.BButtonDown(1)) {
      fixRotation = false; 
    } else{fixRotation = true;}
    SwerveMath.ComputeSwerve(finalAngle, directMag, rotMag, fixRotation); 

    //Robot.swerve.setTalonFX(oi.RightTrigger(2)-oi.LeftTrigger(2));
    SmartDashboard.putNumber ("Angle", Robot.NavAngle());

		
		// if(oi.RightBumperDown(2)) { Redthrottle = .6; } else if(oi.LeftBumperDown(2)) { Redthrottle = -.6;}
		// else { Redthrottle = 0; } Robot.swerve.setRawIntake(Redthrottle);
		// if(oi.RightMag(2) > .3) { ballThrottle = .75*oi.RightY(2); } else if(oi.RightBumperDown(2)) { ballThrottle = .5; }
    // else{ ballThrottle = 0; } Robot.swerve.setRawBallIn(ballThrottle); 
    //SmartDashboard.putNumber("this", Robot.NavAngle());
    //
    if(oi.StartButton(1)) { Robot.nav.reset(); } //if(oi.StartButton(2)) { Robot.swerve.ResetElevator(); }
    // ElevatorPositions.MoveElevators();

    // if(oi.XButton(2)){ Pincher = Value.kForward; } else if(oi.BButton(2)){ Pincher = Value.kReverse; }
    // else { Pincher = Value.kOff; } Robot.swerve.setPincher(Pincher);
    // if(oi.YButton(2)){Extendor = Value.kForward; } else if(oi.AButton(2)){Extendor = Value.kReverse; }
    // else { Extendor = Value.kOff; } Robot.swerve.setExtendor(Extendor);
    // if(oi.LeftY(2) > .7){ pullout = Value.kForward; } else if(oi.LeftY(2) < -.7){ pullout = Value.kReverse; }
    // else { pullout = Value.kOff; } Robot.swerve.setPullout(pullout);

  }
  protected boolean isFinished() { return false;}
  protected void end() {}

}
