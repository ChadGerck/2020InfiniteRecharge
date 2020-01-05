package org.usfirst.frc.team7327.robot.subsystems;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
// import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
// import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import org.usfirst.frc.team7327.robot.ElevatorModule;
import org.usfirst.frc.team7327.robot.commands.Drive;
import org.usfirst.frc.team7327.robot.SwerveModule;
import org.usfirst.frc.team7327.robot.TurnModule;

public class Drivetrain extends Subsystem {
  public TurnModule turning; 
  public static Potentiometer abeNW = new AnalogPotentiometer(0, 360, 262.8), abeNE = new AnalogPotentiometer(1, 360, 131.2), 
                              abeSW = new AnalogPotentiometer(2, 360, 345.2), abeSE = new AnalogPotentiometer(3, 360, 220.3); 
  double kSwerveP = .8, kSwerveD = .1; 
  private SwerveModule moduleNW= new SwerveModule(1,2, abeNW, kSwerveP, kSwerveD, false), moduleNE = new SwerveModule(3,4, abeNE, kSwerveP, kSwerveD, false),
                       moduleSW= new SwerveModule(5,6, abeSW, kSwerveP, kSwerveD, false), moduleSE= new SwerveModule(7,8, abeSE, kSwerveP, kSwerveD,  false);
  
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
  public void setModule(String loc,double degrees,double power){
    switch(loc){case "NW":moduleNW.set(degrees,power);break; case "NE":moduleNE.set(degrees,power);break;
                case "SW":moduleSW.set(degrees,power);break; case "SE":moduleSE.set(degrees,power);break;
    }
  }public SwerveModule getModuleNW(){ return moduleNW;}
  public  SwerveModule getModuleNE(){ return moduleNE; }
	public  SwerveModule getModuleSW(){ return moduleSW;}
  public  SwerveModule getModuleSE(){ return moduleSE; }
  // public void setPincher(DoubleSolenoid.Value value){ Pincher.set(value); }
  // public void setExtendor(DoubleSolenoid.Value value){ Extendor.set(value); }
  // public void setPullout(DoubleSolenoid.Value value){ pullout.set(value); }
  
  public void setAllAngle(double degrees){
    moduleNW.setSteeringDegrees(degrees); moduleNE.setSteeringDegrees(degrees);
    moduleSW.setSteeringDegrees(degrees); moduleSE.setSteeringDegrees(degrees);
  }public void setAllPower(double power){
    moduleNW.setDrivePower(power); moduleNE.setDrivePower(power);
    moduleSW.setDrivePower(power); moduleSE.setDrivePower(power);
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
}
