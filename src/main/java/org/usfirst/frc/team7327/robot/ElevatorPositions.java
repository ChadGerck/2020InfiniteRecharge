package org.usfirst.frc.team7327.robot;
//import edu.wpi.first.wpilibj.XboxController;
//import static org.usfirst.frc.team7327.robot.Robot.oi;

public class ElevatorPositions {
  //public static XboxController P1 = oi.Controller0;//, P2 = oi.Controller1; 
  static int heightB0 = 0, heightB1 = 15000, heightB2 = 29000, heightB3 = 37000, heightH2 = 18033, heightH3 = 30973; 
  static double throttle = .3; 
}

//   public static void MoveElevators() {
// 		if(oi.Dpad(P2) >= 0) { 
//       Robot.kDrivetrain.ElevOn(true);
//       if     (oi.DpadDown(P2))   {Robot.kDrivetrain.setElevatorPosition(heightB0);}
//       else if(oi.DpadRight(P2))  {Robot.kDrivetrain.setElevatorPosition(heightB1);}
//       else if(oi.DpadUp(P2))     {Robot.kDrivetrain.setElevatorPosition(heightB2);}
//       else if(oi.DpadLeft(P2))   {Robot.kDrivetrain.setElevatorPosition(heightB3);} 
//     } else{ Robot.kDrivetrain.setRawElevator(throttle*(-oi.LeftTrigger(P2) + oi.RightTrigger(P2))); Robot.kDrivetrain.ElevOn(false); }
//   }
// }