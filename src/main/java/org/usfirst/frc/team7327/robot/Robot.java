 package org.usfirst.frc.team7327.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import java.util.concurrent.TimeUnit;

import com.kauailabs.navx.frc.AHRS;
import org.usfirst.frc.team7327.robot.subsystems.Drivetrain;

//import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.I2C;

public class Robot extends TimedRobot {
  public static final Drivetrain swerve = new Drivetrain();
  public static Timer myTimer = new Timer();
  public static final OI oi = new OI();
  private static final String 
  FarL = "FarL", Left = "Left", Mid = "Mid", Front = "Front", FarR = "FarR",
  Default = "Default", P2 = "A1.2", P3 = "A1.3", HailMary = "HailMary", Defense = "Defense"; 
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final SendableChooser<String> m_chosen = new SendableChooser<>();
  public static AHRS nav; 
  static double autoAngle = 0;
  static double autoX = 0; 
  static double autoY = 0; 
  public boolean flag = true; 
  static double finalAngle, directMag, steering_adjust, x, rotMag;
  static Boolean fixRotation;
  private Counter m_LIDAR;
  static double SteerP = -0.025;
  final double off  = 10; //offset for sensor. test with tape measure
  public static Compressor c0 = new Compressor(0);
  @Override public void robotInit() { 
    m_LIDAR = new Counter(0); //plug the lidar into PWM 0
    m_LIDAR.setMaxPeriod(1.00); //set the max period that can be measured
    m_LIDAR.setSemiPeriodMode(true); //Set the counter to period measurement
    m_LIDAR.reset();
    nav = new AHRS(I2C.Port.kMXP); 
    
    nav.reset();
    //CameraServer.getInstance().startAutomaticCapture();
    c0.setClosedLoopControl(true); 

    m_chooser.setDefaultOption("FarL", FarL); m_chooser.addOption("Left", Left); m_chooser.addOption("Mid", Mid); m_chooser.addOption("Front", Front);  m_chooser.addOption("FarR", FarR);
    SmartDashboard.putData("Auto choices", m_chooser);
    
    m_chosen.setDefaultOption("Default", Default); m_chosen.addOption("PlayerStation", P2);
    m_chosen.addOption("P3", P3); m_chosen.addOption("HailMary", HailMary);
    m_chosen.addOption("Defense", Defense);
    SmartDashboard.putData("the Auto: ", m_chosen);
  }
  @Override public void robotPeriodic() { 
    double dist;
    if(m_LIDAR.get() < 1) dist = 0;
    else dist = (m_LIDAR.getPeriod()*1000000.0/10.0) - off; //convert to distance. sensor is high 10 us for every centimeter. 
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
    swerve.setALLBrake(false); 
    Autonomous.Auto();

  }
  public static void LimeAlign(){
    do{
      x = oi.LimelightTx();
      steering_adjust = SteerP*-x;
      finalAngle = Math.toDegrees(Math.atan2(oi.LeftY(1),steering_adjust))-90; 
      directMag = (Math.abs(steering_adjust) + Math.abs(oi.LeftY(1)))/2; 
      SwerveMath.ComputeSwerve(finalAngle, directMag, rotMag, fixRotation); 
    }while(x<-3 || x > 3);
  }
  public static void MoveForward(){
    autoY += .3048;
    Robot.MoveTo(autoX, autoY, autoAngle);
  }
  public static void TurnRight(){
    autoAngle += 90; 
    Robot.MoveTo(autoX, autoY, autoAngle);
  }
  
  public static void MoveTo(double x, double y, double angle){
    x = -x; 
    angle = -angle; 
    finalAngle = 0; 
    directMag = 0; 
    while((Math.sqrt(Math.pow(swerve.ODOY()-y,2)+Math.pow(swerve.ODOX()-x,2)) > .1 || Math.abs(-angle-Robot.NavAngle()) > 5)){
      SmartDashboard.putNumber("Time: ", myTimer.get());
      if(myTimer.get() > 20){ break; }
      try { Robot.swerve.turning.setYaw(angle + Robot.NavAngle());} catch (Exception e) {}
      finalAngle = Math.toDegrees(Math.atan2(-(swerve.ODOX()-x),-(swerve.ODOY()-y)))-Robot.NavAngle(); 
      directMag = Math.hypot(swerve.ODOX()-x,swerve.ODOY()-y);
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
    if(oi.RSClick(2)){
      if(flag){ c0.setClosedLoopControl(false); flag = false; }
      else{ c0.setClosedLoopControl(true); flag = true; }
    } 
  }
  @Override public void testPeriodic() {}
  public static double NavAngle() {return NavAngle(0);}
  public static double NavAngle(double add){double angle = Robot.nav.getAngle()+add;
    while(angle>180)angle-=360;while(angle<-180)angle+=360;return angle; 
  }
}
