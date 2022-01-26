// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class ButtonSubsystem extends SubsystemBase {
  /** Creates a new ButtonSubsystem. */
  private final ShuffleboardTab tab1 = Shuffleboard.getTab("Button");

  private TalonSRX motorConnector = new TalonSRX(2); 



  private final NetworkTableEntry Pressed = tab1.add("Button Pressed", false)
  .withWidget("Boolean Box")
  .withProperties(Map.of("colorWhenTrue", "Green", "colorWhenFalse", "Red"))
  .withPosition(0,0)
  .getEntry();

  private boolean buttonPress = false;

  public ButtonSubsystem() {}

  public void Flash(boolean pressed){
    if(pressed){
      buttonPress=true;
    }
    else{
      buttonPress=false;
    }
    if(buttonPress){
      Pressed.setBoolean(true);
    }
    else{
      Pressed.setBoolean(false);
    }
  }

  @Override
  public void periodic() {
    if(RobotContainer.button.get()== true){
      motorConnector.set(TalonSRXControlMode.PercentOutput, 0.3);
    }

    else {
      motorConnector.set(TalonSRXControlMode.PercentOutput, 0.0); 
    }

    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}