package org.usfirst.frc.team7327.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.concurrent.TimeUnit;

import com.kauailabs.navx.frc.AHRS;
import org.usfirst.frc.team7327.robot.subsystems.Drivetrain;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Counter;
//import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.I2C;

public class Robot extends TimedRobot {
  public static final Drivetrain swerve = new Drivetrain();
  public static Timer myTimer = new Timer();
  public static final OI oi = new OI();
  public static AHRS nav; 
  public boolean flag = true; 
  static double finalAngle, directMag; 
  private Counter m_LIDAR;
  final double off  = 10; //offset for sensor. test with tape measure
  //Compressor c0 = new Compressor(0);
  @Override public void robotInit() { 
    m_LIDAR = new Counter(0); //plug the lidar into PWM 0
    m_LIDAR.setMaxPeriod(1.00); //set the max period that can be measured
    m_LIDAR.setSemiPeriodMode(true); //Set the counter to period measurement
    m_LIDAR.reset();
    nav = new AHRS(I2C.Port.kMXP); 
    CameraServer.getInstance().startAutomaticCapture();
    // c0.setClosedLoopControl(true); 
  }
  @Override public void robotPeriodic() { 
    double dist;
    if(m_LIDAR.get() < 1)
      dist = 0;
    else
      dist = (m_LIDAR.getPeriod()*1000000.0/10.0) - off; //convert to distance. sensor is high 10 us for every centimeter. 
    SmartDashboard.putNumber("Distance", dist); //put the distance on the dashboard
    swerve.updateDashboard();
  }
  @Override public void teleopInit() { 
    swerve.setALLBrake(false); 
    swerve.OdoReset(); 
  /*swerve.SetElevatorStatus(); swerve.ConfigElevator();*/
 }
  @Override public void autonomousInit() { 
    swerve.setALLBrake(true); 
		myTimer.reset();
		myTimer.start();
    swerve.OdoReset();
    nav.reset();
    Autonomous.Auto(); 
    swerve.setALLBrake(false); 
  }
  
  public static void MoveTo(double x, double y, double angle){
    y = -y; 
    angle = -angle; 
    finalAngle = 0; 
    directMag = 0; 
    while((Math.sqrt(Math.pow(swerve.ODOX()-x,2)+Math.pow(swerve.ODOY()-y,2)) > .1 || Math.abs(-angle-Robot.NavAngle()) > 5)){
      SmartDashboard.putNumber("Time: ", myTimer.get());
      if(myTimer.get() > 20){ break; }
      try { Robot.swerve.turning.setYaw(angle + Robot.NavAngle());} catch (Exception e) {}
      finalAngle = Math.toDegrees(Math.atan2(-(swerve.ODOY()-y),-(swerve.ODOX()-x)))-Robot.NavAngle(); 
      directMag = Math.hypot(swerve.ODOY()-y,swerve.ODOX()-x);
      SwerveMath.ComputeSwerve(finalAngle, directMag, Robot.swerve.turning.getPIDOutput(), false);
      Drivetrain.updateOdometry(); swerve.updateDashboard();
      SmartDashboard.putNumber("x", x);
      SmartDashboard.putNumber("y", y);
      SmartDashboard.putNumber("angle", angle);
    }
    SwerveMath.ComputeSwerve(finalAngle, 0, 0, false);
  }
  public static void SleepFor(long x){try { TimeUnit.SECONDS.sleep(x); } catch (Exception e) {}}
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
