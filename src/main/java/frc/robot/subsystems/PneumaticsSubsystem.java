// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class PneumaticsSubsystem extends SubsystemBase {

  // Create ball pickup solenoid widgets
  // private NetworkTableEntry ballPickupWidget = Shuffleboard.("Intake Deployed",
  // false).withSize(2, 1).withPosition(2, 4)
  // .getEntry();

  private final DoubleSolenoid sol = new DoubleSolenoid(
      PneumaticsModuleType.REVPH,
      Constants.kForwardChannel,
      Constants.kReverseChannel);

  /** Creates a new PneumaticsSubsystem. */
  public PneumaticsSubsystem() {
  }

  public void extendPiston() {
    sol.set(Constants.kPistonOut);
  }

  public void retractPiston() {
    sol.set(Constants.kPistonIn);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
