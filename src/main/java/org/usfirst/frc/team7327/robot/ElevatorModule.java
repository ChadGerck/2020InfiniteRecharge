package org.usfirst.frc.team7327.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Notifier;

public class ElevatorModule {
    private CANSparkMax mLift1, mLift2;
    private Notifier SteeringPID;
    private double setPoint;
    private double PIDOutput;
    private CANEncoder m_ElevatorEncoder1, m_ElevatorEncoder2; 
    private boolean on; 
    static final double kP = .0008; }
//     public ElevatorModule(int kDriveID1, int kDriveID2) {
//         // mLift1 = new CANSparkMax(kDriveID1, MotorType.kBrushless);
//         // mLift2 = new CANSparkMax(kDriveID2, MotorType.kBrushless);
//         m_ElevatorEncoder1 = new CANEncoder(mLift1);
//         m_ElevatorEncoder2 = new CANEncoder(mLift2);
//         SteeringPID = new Notifier(() -> {
//             PIDOutput = kP * getError();
//             PIDOutput = Math.min(1, PIDOutput);
//             PIDOutput = Math.max(-.8, PIDOutput); 
//             if(on) { mLift1.set(PIDOutput); mLift2.set(PIDOutput); }
//         });
//         SteeringPID.startPeriodic(0.0001);
// 		mLift1.setIdleMode(IdleMode.kBrake);
// 		mLift2.setIdleMode(IdleMode.kBrake);
//     }
//     public double getError(){ return setPoint - getLiftPosition(); }
//     public void setPosition(double position){ setPoint = position; }
//     public void setRawElev(double speed){ mLift1.set(speed); mLift2.set(speed);}
// 	public double getLiftVelocity() { return (-m_ElevatorEncoder1.getVelocity()-m_ElevatorEncoder2.getVelocity())/2; }
// 	public double getLiftPosition() { return (-m_ElevatorEncoder1.getPosition()-m_ElevatorEncoder2.getPosition())/2; }
//     public void ElevatorReset()        { m_ElevatorEncoder1.setPosition(0); m_ElevatorEncoder2.setPosition(0); }
//     public void setOn(boolean flipOn) { on = flipOn; }
// }