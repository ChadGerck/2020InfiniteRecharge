package org.usfirst.frc.team7327.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;
import org.usfirst.frc.team7327.robot.subsystems.Drivetrain;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Counter;

public class Robot extends TimedRobot {
  private Counter m_LIDAR;
  public static final Drivetrain swerve = new Drivetrain();
  public static final OI oi = new OI();
  public static AHRS nav; 
  public boolean flag = true; 
  static double finalAngle, directMag; 
  
  final double off  = 10; //offset for sensor. test with tape measure
  @Override public void robotInit() { nav = new AHRS(I2C.Port.kMXP); 
    CameraServer.getInstance().startAutomaticCapture();
  }
  @Override public void teleopInit() { 
    
    m_LIDAR = new Counter(0); //plug the lidar into PWM 0
    m_LIDAR.setMaxPeriod(1.00); //set the max period that can be measured
    m_LIDAR.setSemiPeriodMode(true); //Set the counter to period measurement
    m_LIDAR.reset();
  /*swerve.SetElevatorStatus(); swerve.ConfigElevator();*/
  }
  @Override public void robotPeriodic() { 
    swerve.updateDashboard();
    double dist;
    try {
      if(m_LIDAR.get() < 1)
      dist = 0;
    else
      dist = (m_LIDAR.getPeriod()*1000000.0/10.0) - off; //convert to distance. sensor is high 10 us for every centimeter. 
    SmartDashboard.putNumber("Distance", dist); //put the distance on the dashboard
    } catch (Exception e) {
      //TODO: handle exception
    }
    
  
  }
  @Override public void autonomousInit() { 
    swerve.OdoReset();
    nav.reset();
    Autonomous.Auto();
  }
  
  public static void MoveY(double y){
    if (y > 0){
      while (swerve.ODOY() < y){ SwerveMath.ComputeSwerve(90,.15,0,false); 
        Drivetrain.updateOdometry(); swerve.updateDashboard();
      }
    }else if (y < 0){
      while (swerve.ODOY() > y){ SwerveMath.ComputeSwerve(90,-.15,0,false); 
        Drivetrain.updateOdometry(); swerve.updateDashboard();
      }
    }
    SwerveMath.ComputeSwerve(0,0,0,false);
  }
  public static void MovetryY(double y){
    //how can I make the code be able to flip?
    y = -y; 
    if (y > 0){
      while (swerve.ODOY() < y){ SwerveMath.ComputeSwerve(90,.15,0,false); 
        Drivetrain.updateOdometry(); swerve.updateDashboard();
      }
    }else if (y < 0){
      while (swerve.ODOY() > y){ SwerveMath.ComputeSwerve(90,-.15,0,false); 
        Drivetrain.updateOdometry(); swerve.updateDashboard();
      }
    }
    SwerveMath.ComputeSwerve(0,0,0,false);
  }
  
  public static void MoveX(double x){
    if (x > 0){
      while(swerve.ODOX() < x){ SwerveMath.ComputeSwerve(0,.15,0,false); 
        Drivetrain.updateOdometry(); swerve.updateDashboard();
      }
    }else if(x < 0){
      while (swerve.ODOX() > x){ SwerveMath.ComputeSwerve(0,-.15,0,false); 
        Drivetrain.updateOdometry(); swerve.updateDashboard();
      }
    }
    SwerveMath.ComputeSwerve(0,0,0,false);
  }
  public static void MoveTo(double x, double y){
    y = -y; 
    finalAngle = 0; 
    directMag = 0;  
    while(Math.abs(swerve.ODOX()-x)+Math.abs(swerve.ODOY()-y) > .1){
      finalAngle = Math.toDegrees(Math.atan2(-(swerve.ODOY()-y),-(swerve.ODOX()-x)))-Robot.NavAngle(); 
      directMag = Math.hypot(swerve.ODOY()-y,swerve.ODOX()-x);
      SwerveMath.ComputeSwerve(finalAngle, directMag, 0, false);
      Drivetrain.updateOdometry(); swerve.updateDashboard();
    }
    SwerveMath.ComputeSwerve(finalAngle, 0, 0, false);
  }
  public static void MoveTo(double x, double y, double angle){
    y = -y; 
    angle = -angle; 
    while(Math.abs(swerve.ODOX()-x)+Math.abs(swerve.ODOY()-y)+(Math.abs(-angle-Robot.NavAngle())/50) > .1){
      try { Robot.swerve.turning.setYaw(angle + Robot.NavAngle());} catch (Exception e) {}
      finalAngle = Math.toDegrees(Math.atan2(-(swerve.ODOY()-y),-(swerve.ODOX()-x)))-Robot.NavAngle(); 
      directMag = Math.hypot(swerve.ODOY()-y,swerve.ODOX()-x);
      SwerveMath.ComputeSwerve(finalAngle, directMag, Robot.swerve.turning.getPIDOutput(), false);
      Drivetrain.updateOdometry(); swerve.updateDashboard();
    }
    SwerveMath.ComputeSwerve(finalAngle, 0, 0, false);
  }
  @Override public void autonomousPeriodic() {
    Drivetrain.updateOdometry();
  }
  @Override public void teleopPeriodic() { Scheduler.getInstance().run();
    Drivetrain.updateOdometry();
    SmartDashboard.putNumber("ODOX", Drivetrain.m_odometry.getPoseMeters().getTranslation().getX());
    SmartDashboard.putNumber("ODOY", Drivetrain.m_odometry.getPoseMeters().getTranslation().getY());
    
    // if(oi.LSClick(oi.Controller1)){
    //   if(flag){ c0.setClosedLoopControl(false); flag = false; }
    //   else{ c0.setClosedLoopControl(true); flag = true; }
    // } 
  }
  @Override public void testPeriodic() {}
  public static double NavAngle() {return NavAngle(0);}
  public static double NavAngle(double add){double angle = Robot.nav.getAngle()+add;
    while(angle>180)angle-=360;while(angle<-180)angle+=360;return angle; 
  }
}
