// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class PneumaticsSubsystem extends SubsystemBase {

  // variables for reading current pressure
  double current_pressure;
  double current_voltage;
  AnalogInput pressureSensor = new AnalogInput(Constants.AnalogPressureChannel);

  private static final int PH_CAN_ID = 2;
  PneumaticsControlModule m_ph = new PneumaticsControlModule(PH_CAN_ID);

  // define the solenoid

  private DoubleSolenoid sol = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.kForwardChannel,
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
    // display voltage from the pressure sensor
    current_voltage = pressureSensor.getVoltage();
    SmartDashboard.putNumber("Voltage", current_voltage);

    // According to Rev documentation pressure = 250 (voltageOut/voltageSupply)-25
    // display calculated pressure
    current_pressure = 250 * (current_voltage / 5) - 25;
    SmartDashboard.putNumber("Pessure", current_pressure);

    if (sol.get() == Value.kForward)
      SmartDashboard.putString("SolState", "kForward");
    else if (sol.get() == Value.kReverse)
      SmartDashboard.putString("SolState", "kReverse");
    else if (sol.get() == Value.kOff)
      SmartDashboard.putString("SolState", "kOff");
    else
      SmartDashboard.putString("SolState", "unknown");

  }

}
