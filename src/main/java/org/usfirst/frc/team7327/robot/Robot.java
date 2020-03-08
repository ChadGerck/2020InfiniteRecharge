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
  FarL = "Far L", Left = "Left", Mid = "Mid", Front = "Front", FarR = "Far R",
  Default = "Default", P2 = "A1.2", P3 = "A1.3", HailMary = "HailMary", Defense = "Defense"; 
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final SendableChooser<String> m_chosen = new SendableChooser<>();
  public static AHRS nav; 
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
    
    m_chosen.setDefaultOption("Default", Default); m_chosen.addOption("P2", P2);
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
    switch(m_chooser.getSelected()){
      case "FarL": 
      switch(m_chosen.getSelected()){
        case "Default": Autonomous.Auto(); break; case "Trench": Autonomous.Auto2(); break; 
        case "P3": Autonomous.Auto3(); break; case "HailMary": Autonomous.Auto4(); break;   
        case "Defense": Autonomous.Auto21(); break;   
      } break; 
      case "Left": 
      switch(m_chosen.getSelected()){
        case "Default": Autonomous.LeftRealign(); break; case "Trench": Autonomous.Auto6(); break; 
        case "P3": Autonomous.Auto7(); break; case "HailMary": Autonomous.Auto8(); break; 
        case "Defense": Autonomous.Auto22(); break;   
      } break; 
      case "Mid":
      switch(m_chosen.getSelected()){
        case "Default": Autonomous.MidDefault(); break; case "Trench": Autonomous.Auto10(); break; 
        case "P3": Autonomous.MidRight2Balls(); break; case "HailMary": Autonomous.Auto12(); break;  
        case "Defense": Autonomous.Auto23(); break;  
      } break; 
      case "Front":
      switch(m_chosen.getSelected()){
        case "Default": Autonomous.Auto13(); break; case "Trench": Autonomous.Auto14(); break; 
        case "P3": Autonomous.Auto15(); break; case "HailMary": Autonomous.Auto16(); break;   
        case "Defense": Autonomous.Auto24(); break; 
      } break; 
      case "FarR":
      switch(m_chosen.getSelected()){
        case "Default": Autonomous.Auto17(); break; case "Trench": Autonomous.Auto18(); break; 
        case "P3": Autonomous.Auto19(); break; case "HailMary": Autonomous.Auto20(); break;   
        case "Defense": Autonomous.AutoTest(); break; 
      } break; 
    }

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
  
  public static void MoveTo(double y, double x, double angle){
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
