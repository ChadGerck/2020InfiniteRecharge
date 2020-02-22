package org.usfirst.frc.team7327.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import org.usfirst.frc.team7327.robot.ElevatorModule;
import org.usfirst.frc.team7327.robot.Robot;
import org.usfirst.frc.team7327.robot.commands.Drive;
import org.usfirst.frc.team7327.robot.SwerveModule;
import org.usfirst.frc.team7327.robot.TurnModule;

public class Drivetrain extends Subsystem {
  public TurnModule turning;   
  private static final Translation2d m_frontLeftLocation = new Translation2d(0.381, 0.381);
  private static final Translation2d m_frontRightLocation = new Translation2d(-0.381, 0.381);
  private static final Translation2d m_backLeftLocation = new Translation2d(0.381, -0.381);
  private static final Translation2d m_backRightLocation = new Translation2d(-0.381, -0.381);

  public static Potentiometer abeFL = new AnalogPotentiometer(0, 360, 280), abeFR = new AnalogPotentiometer(1, 360, 393.2), 
                              abeBL = new AnalogPotentiometer(2, 360, 430 ), abeBR = new AnalogPotentiometer(3, 360, 443.3); 

  static double kSwerveP = .8, kSwerveD = .1; 
  private static SwerveModule 
  moduleFL = new SwerveModule(1, 2, abeFL, kSwerveP, kSwerveD, false), moduleFR = new SwerveModule(3, 4, abeFR, kSwerveP, kSwerveD, false),
  moduleBL = new SwerveModule(5, 6, abeBL, kSwerveP, kSwerveD, false), moduleBR = new SwerveModule(7, 8, abeBR, kSwerveP, kSwerveD, false);
  
  private static final SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation);
  public static final SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(m_kinematics, Rotation2d.fromDegrees(0));
  
  public static ElevatorModule Elevator;
  public static VictorSPX IntakeMotor, FunnelMotor, ControlPanelMotor;
  public static TalonFX ShooterMotor1, ShooterMotor2;
  public static CANSparkMax BallHandlerMotor;
  public static DoubleSolenoid Extendor; 
  public Drivetrain(){
    ShooterMotor1 = new TalonFX(9);
    ShooterMotor2 = new TalonFX(10);
    IntakeMotor       = new VictorSPX(11);
    FunnelMotor       = new VictorSPX(12);
    ControlPanelMotor = new VictorSPX(13);
    BallHandlerMotor = new CANSparkMax(14, MotorType.kBrushless);
    Elevator  = new ElevatorModule(15,16); 
    turning = new TurnModule(); 
    Extendor = new DoubleSolenoid(0,2, 5);
  }
  @Override public void initDefaultCommand() { setDefaultCommand(new Drive()); }
  public static void setModule(String loc,double degrees,double power){
    switch(loc){case "FL":moduleFL.set(degrees,power);break; case "FR":moduleFR.set(degrees,power);break;
                case "BL":moduleBL.set(degrees,power);break; case "BR":moduleBR.set(degrees,power);break;
    }
  }public SwerveModule getModuleNW(){ return moduleFL;}
  public  SwerveModule getModuleNE(){ return moduleFR; }
	public  SwerveModule getModuleSW(){ return moduleBL;}
  public  SwerveModule getModuleSE(){ return moduleBR; }
  public static Rotation2d getAngle() { return Rotation2d.fromDegrees(Robot.NavAngle()); }
  public void setAllAngle(double degrees){
    moduleFL.setSteeringDegrees(degrees); moduleFR.setSteeringDegrees(degrees);
    moduleBL.setSteeringDegrees(degrees); moduleBR.setSteeringDegrees(degrees);
  }public void setAllPower(double power){
    moduleFL.setDrivePower(power); moduleFR.setDrivePower(power);
    moduleBL.setDrivePower(power); moduleBR.setDrivePower(power);
  }
  public void setALLBrake(boolean brake){
    moduleFL.setBrakeOn(brake); moduleFR.setBrakeOn(brake);
    moduleBL.setBrakeOn(brake); moduleBR.setBrakeOn(brake);
  }
  public static void setIntakeMotors(double intakepower, DoubleSolenoid.Value value){
      IntakeMotor.set(ControlMode.PercentOutput, intakepower);
      Extendor.set(value);
  }
  public static void Shoot(double shooterpower, double handlepower){
    ShooterMotor1.set(ControlMode.PercentOutput, shooterpower); ShooterMotor2.set(ControlMode.PercentOutput, -shooterpower);
    FunnelMotor.set(ControlMode.PercentOutput,handlepower); BallHandlerMotor.set(handlepower);
  }
  public static void ControlPanel(double power){ ControlPanelMotor.set(ControlMode.PercentOutput, power); }
  public void setRawElevator(double speed){ Elevator.setRawElev(speed); }
	public void setElevatorPosition(double position){ Elevator.setPosition(position); }
	public void ElevOn(boolean On) { Elevator.setOn(On); }
	public void ResetElevator() { Elevator.ElevatorReset(); }
	public double getLiftVelocity() { return Elevator.getLiftVelocity(); }
	public double getLiftPosition() { return Elevator.getLiftPosition(); }
  public void updateDashboard(){ SmartDashboard.putNumber("ODOX", ODOX()); SmartDashboard.putNumber("ODOY", ODOY()); }
  public static void updateOdometry() {
    m_odometry.update(
        getAngle(),
        moduleFL.getState(), moduleFR.getState(),
        moduleBL.getState(), moduleBR.getState()
    );
  }
  public double ODOX() { return m_odometry.getPoseMeters().getTranslation().getX(); }
  public double ODOY() { return m_odometry.getPoseMeters().getTranslation().getY(); }
  public void OdoReset(){ m_odometry.resetPosition(new Pose2d(new Translation2d(0.0,0.0), Rotation2d.fromDegrees(0)), Rotation2d.fromDegrees(0));}
}
