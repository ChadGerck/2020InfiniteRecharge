package org.usfirst.frc.team7327.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
// import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;

// import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.can.VictorSPX;
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


  public static Potentiometer abeFL = new AnalogPotentiometer(0, 360, 100), abeFR = new AnalogPotentiometer(1, 360, 203.2), 
                              abeBL = new AnalogPotentiometer(2, 360, 264 ), abeBR = new AnalogPotentiometer(3, 360, 38.3); 

  static double kSwerveP = .8, kSwerveD = .1; 
  private static SwerveModule 
  moduleFL = new SwerveModule(1, 2, abeFL, kSwerveP, kSwerveD, false), moduleFR = new SwerveModule(3, 4, abeFR, kSwerveP, kSwerveD, false),
  moduleBL = new SwerveModule(5, 6, abeBL, kSwerveP, kSwerveD, false), moduleBR = new SwerveModule(7, 8, abeBR, kSwerveP, kSwerveD, false);
  
  private static final SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation);
  public static final SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(m_kinematics, Rotation2d.fromDegrees(0));
  
  public static ElevatorModule Elevator;
  //public static VictorSPX BallVictor, Intake;
  //public static DoubleSolenoid Pincher, Extendor, pullout; 
  public Drivetrain(){
    // Elevator  = new ElevatorModule(8); Intake = new VictorSPX(9); 
    // BallVictor= new VictorSPX(10);    
    turning = new TurnModule(); 
    // Pincher = new DoubleSolenoid(0,3, 4); Extendor = new DoubleSolenoid(0,2, 5);
    // pullout = new DoubleSolenoid(1,0,7); 
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
  // public void setPincher(DoubleSolenoid.Value value){ Pincher.set(value); }
  // public void setExtendor(DoubleSolenoid.Value value){ Extendor.set(value); }
  // public void setPullout(DoubleSolenoid.Value value){ pullout.set(value); }
  
  public void setAllAngle(double degrees){
    moduleFL.setSteeringDegrees(degrees); moduleFR.setSteeringDegrees(degrees);
    moduleBL.setSteeringDegrees(degrees); moduleBR.setSteeringDegrees(degrees);
  }public void setAllPower(double power){
    moduleFL.setDrivePower(power); moduleFR.setDrivePower(power);
    moduleBL.setDrivePower(power); moduleBR.setDrivePower(power);
  }
  //  }public void setRawElevator(double speed){ Elevator.setRawElev(speed); }
	// public void setElevatorPosition(double position){ Elevator.setPosition(position); }
	// public void ElevOn(boolean On) { Elevator.setOn(On); }
	// public void ResetElevator() { Elevator.ElevatorReset(); }
	// public void ConfigElevator() { Elevator.configFeedbackSensor(); }
	// public void SetElevatorStatus() { Elevator.setTalonStatus(); }
	// public double getLiftVelocity() { return Elevator.getLiftVelocity(); }
	// public double getLiftPosition() { return Elevator.getLiftPosition(); }
	// public void setRawBallIn(double speed){ BallVictor.set(ControlMode.PercentOutput, speed); }
	// public void setRawIntake(double intakevalue) { Intake.set(ControlMode.PercentOutput, intakevalue);	} 
  public void updateDashboard(){}
  public static void updateOdometry() {
    m_odometry.update(
        getAngle(),
        moduleFL.getState(),
        moduleFR.getState(),
        moduleBL.getState(),
        moduleBR.getState()
    );
  }
}
