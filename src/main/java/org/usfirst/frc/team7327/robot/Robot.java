package org.usfirst.frc.team7327.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import com.kauailabs.navx.frc.AHRS;
import org.usfirst.frc.team7327.robot.subsystems.Drivetrain;

import edu.wpi.first.cameraserver.CameraServer;
//import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.I2C;

public class Robot extends TimedRobot {
  public static final Drivetrain Drivetrain = new Drivetrain();
  public static final OI oi = new OI();
  public static AHRS nav; 
  public boolean flag = true; 
  //Compressor c0 = new Compressor(0);
  @Override public void robotInit() { nav = new AHRS(I2C.Port.kMXP); 
    CameraServer.getInstance().startAutomaticCapture();
    // c0.setClosedLoopControl(true); 
  }
  @Override public void robotPeriodic() { Drivetrain.updateDashboard(); }
@Override public void teleopInit() { /*kDrivetrain.SetElevatorStatus(); kDrivetrain.ConfigElevator();*/ }
  @Override public void autonomousInit() { nav.reset(); }
  @Override public void autonomousPeriodic() { Scheduler.getInstance().run(); }
  @Override public void teleopPeriodic() { Scheduler.getInstance().run();
    // if(oi.LSClick(oi.Controller1)){
    //   if(flag){ c0.setClosedLoopControl(false); flag = false; }
    //   else{ c0.setClosedLoopControl(true); flag = true; }
    // }
  }
  @Override public void testPeriodic() {}
  public static double NavAngle() {return NavAngle(0);}
  public static double NavAngle(double add){double angle = Robot.nav.getAngle()+add;
    while(angle>360)angle-=360;while(angle<0)angle+=360;return angle; 
  }
}
