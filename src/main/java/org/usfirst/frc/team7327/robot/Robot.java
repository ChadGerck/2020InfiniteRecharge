package org.usfirst.frc.team7327.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;
import org.usfirst.frc.team7327.robot.subsystems.Drivetrain;

// import edu.wpi.first.cameraserver.CameraServer;
//import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.I2C;

public class Robot extends TimedRobot {
  public static final Drivetrain m_swerve = new Drivetrain();
  public static final OI oi = new OI();
  public static AHRS nav; 
  public boolean flag = true; 
  //Compressor c0 = new Compressor(0);
  @Override public void robotInit() { nav = new AHRS(I2C.Port.kMXP); 
    // CameraServer.getInstance().startAutomaticCapture();
    // c0.setClosedLoopControl(true); 
  }
  @Override public void robotPeriodic() { m_swerve.updateDashboard();}
@Override public void teleopInit() { /*m_swerve.SetElevatorStatus(); m_swerve.ConfigElevator();*/ }
  @Override public void autonomousInit() { 
    nav.reset();
    
    RobotMoveY(1);
    RobotMoveY(1);
    RobotMoveX(1);
    RobotMoveY(4);
  }
  public void RobotMoveY(int y){
    if (y > 0){while (m_swerve.ODOY() > 1){
      m_swerve.drive (0, 1, 0, true);}
    }else if (y < 0){while (m_swerve.m_odometry.getPoseMeters().getTranslation().getY() < 1){
      m_swerve.drive (0, -1, 0, true);}
    }
}public void RobotMoveX(int x){
    if (x > 0){while(m_swerve.ODOX() > 1){
      m_swerve.drive (1, 0, 0, true); }
    }else if(x < 0){while (m_swerve.m_odometry.getPoseMeters().getTranslation().getX() < 1){
      m_swerve.drive (-1, 0, 0, true); 
    }
  }
  @Override public void autonomousPeriodic() { Scheduler.getInstance().run(); }
  @Override public void teleopPeriodic() { Scheduler.getInstance().run();
    Drivetrain.updateOdometry();
    SmartDashboard.putNumber("ODOX", m_swerve.m_odometry.getPoseMeters().getTranslation().getX());
    SmartDashboard.putNumber("ODOY", m_swerve.m_odometry.getPoseMeters().getTranslation().getY());
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
