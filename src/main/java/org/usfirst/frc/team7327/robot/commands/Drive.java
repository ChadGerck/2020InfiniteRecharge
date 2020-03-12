package org.usfirst.frc.team7327.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team7327.robot.Robot;
import org.usfirst.frc.team7327.robot.SwerveMath;
import org.usfirst.frc.team7327.robot.subsystems.Drivetrain;
import static org.usfirst.frc.team7327.robot.Robot.oi;

// import org.usfirst.frc.team7327.robot.ElevatorPositions;

public class Drive extends Command {
  public Drive() { requires(Robot.swerve); }
  protected void initialize() { }
  double finalAngle, Redthrottle, ballThrottle, rotMag, rightArc, directMag, steering_adjust, x; 
  double SteerP = -0.025;
  boolean fixRotation, rocketAngle = true, evadeMode = false; 
  double speedThrottle = .25; 
  double elvthrottle = 0.3;
  DoubleSolenoid.Value Piston = Value.kReverse; 
  //ShooterThrottles
  double k = 0.315;
  double e = 270;
  //DoubleSolenoid.Value Pincher, Extendor, pullout = Value.kOff; 

  protected void execute() {


    
    if(oi.LeftBumperDown(2)){ Drivetrain.setRawElevator(0.75); }
    else if(oi.RightBumperDown(2)){ Drivetrain.setRawElevator(-0.75);}
    else{Drivetrain.setRawElevator(0);}

    if(oi.BackButton(1)){Robot.MoveTo(0, 0, 0);}
    if(oi.RSClickDown(1)){evadeMode=true;} else{evadeMode=false;}

    if(oi.LSClickDown(1)){speedThrottle = 1;}
    else if(oi.YButtonDown(1)){speedThrottle = .25;} 
    else{speedThrottle = 0.5;}

    SmartDashboard.putBoolean("evademode: ", evadeMode); 
    //SmartDashboard.putNumber("NavAngle: ", Robot.NavAngle()); 
    if(evadeMode && oi.RightMag(1)>.3){ rotMag = -0.5*oi.RightX(1); }
    else if(oi.RightMag(1) > .7 || oi.DpadUp(1) || oi.DpadDown(1) || oi.DpadLeft(1) || oi.DpadRight(1)){
      if(oi.RightMag(1) > .7) { rightArc = -oi.RightArc(1); }
      else if(oi.DpadUp(1)){ rightArc = 0; } else if(oi.DpadRight(1)){ rightArc = 270; }
      else if(oi.DpadDown(1)){ rightArc = 180; } else if(oi.DpadLeft(1)){ rightArc = 90; }
      try { Robot.swerve.turning.setYaw(rightArc + Robot.NavAngle());} catch (Exception e) {}
      rotMag = Robot.swerve.turning.getPIDOutput();
    } else{ rotMag = 0; }

    // if( oi.AButtonDown(1)){ 
    //   x = oi.LimelightTx(); if(x >= -3 && x <= 3){ steering_adjust = 0; }else{ steering_adjust = SteerP*-x; } 
    //   finalAngle = Math.toDegrees(Math.atan2(oi.LeftY(1),steering_adjust))-90; directMag = (Math.abs(steering_adjust) + Math.abs(oi.LeftY(1)))/2; 
    //   oi.LEDOn();
    // } 
    if(oi.LeftMag(1) >= .2){ oi.LEDOff(); finalAngle = Math.toDegrees(Math.atan2(oi.LeftY(1), oi.LeftX(1))) - Robot.NavAngle()-90; directMag = speedThrottle*oi.LeftMag(1); }
    else if(oi.RightBumperDown(1)) { oi.LEDOff(); finalAngle = 270; directMag = .05; } else if(oi.LeftBumperDown(1)) { finalAngle = 90; directMag = .05; }
    else if(oi.LeftTrigger(1) > .1) { oi.LEDOff(); finalAngle = 180; directMag = .05; } else if(oi.RightTrigger(1) > .1) {finalAngle = 0; directMag = .05; }
    else { oi.LEDOff(); directMag = 0; }

    if(oi.LeftBumperDown(1) || oi.RightBumperDown(1) || oi.RightTrigger(1) > .1 || oi.LeftTrigger(1) > .1 || oi.LeftMag(1) >= 0.2 || oi.RightMag(1) > 0.3 || oi.AButtonDown(1) || oi.DpadUp(1) || oi.DpadDown(1) || oi.DpadLeft(1) || oi.DpadRight(1)) {
      fixRotation = false; 
    } else{fixRotation = true;}
    SwerveMath.ComputeSwerve(finalAngle, directMag, rotMag, fixRotation); 

    SmartDashboard.putNumber ("Angle", Robot.NavAngle());
    
    //PLAYER TWO CONTROLS
    SmartDashboard.putNumber("ServoDegrees", Drivetrain.ServoMotor.getAngle());

    if(oi.BackButton(2)){Drivetrain.ServoMotor.setAngle(102);}
    //Drivetrain.Shoot(oi.LeftY(2));
    if(oi.XButtonDown(2)){Robot.swerve.shooter.setVelocity(17400);} //5400
    //else if(oi.BButtonDown(2)){Drivetrain.TopSpin(-.15); Drivetrain.BotSpin(-.15);}
    //if(oi.XButtonDown(2)){Drivetrain.Shoot(.315);}
    //else{Drivetrain.Shoot(0); }
    else{Robot.swerve.shooter.setVelocity(0);}
    SmartDashboard.putNumber("VelocityShoot", Drivetrain.ShooterMotor1.getSelectedSensorVelocity()); 
    SmartDashboard.putNumber("Throttle", k);

    if(oi.DpadDown(2)){Drivetrain.setFunnelSpeed(1);}
    else if(oi.DpadUp(2)){Drivetrain.setFunnelSpeed(-1);}
    else{Drivetrain.setFunnelSpeed(0);}

    if(oi.YButton(2)){Piston = Value.kForward;}
    else if(oi.AButton(2)){Piston = Value.kReverse;}
    Drivetrain.setPiston(Piston);
    Drivetrain.setIntakeSpeed(0.5*oi.RightY(2));
    Drivetrain.setBallSpeed(0.25*(oi.LeftTrigger(2)-oi.RightTrigger(2)));
    if(oi.StartButton(2)){Drivetrain.ResetElevator();}

    
    if(oi.StartButton(1)) { Robot.nav.reset(); } //if(oi.StartButton(2)) { Robot.swerve.ResetElevator(); }
    // ElevatorPositions.MoveElevators();

  }
  protected boolean isFinished() { return false;}
  protected void end() {}

}
