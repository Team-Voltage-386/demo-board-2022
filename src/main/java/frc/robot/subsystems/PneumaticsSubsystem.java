// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class PneumaticsSubsystem extends SubsystemBase {

  // create an object so we can read the pressure from the pneumatics hub
  double current_pressure;
  private static final int PH_CAN_ID = 2;
  PneumaticHub m_ph = new PneumaticHub(PH_CAN_ID);

  private final DoubleSolenoid sol = new DoubleSolenoid(
      PneumaticsModuleType.REVPH,
      Constants.kForwardChannel,
      Constants.kReverseChannel);

  /** Creates a new PneumaticsSubsystem. */
  public PneumaticsSubsystem() {

    // Add number inputs for minimum and maximum pressure
    SmartDashboard.setDefaultNumber("MinPress", 0.0);
    SmartDashboard.setDefaultNumber("MaxPress", 120.0);
  }

  public void extendPiston() {
    sol.set(Constants.kPistonOut);
  }

  public void retractPiston() {
    sol.set(Constants.kPistonIn);
  }

  @Override
  public void periodic() {
    /**
     * Get pressure from analog channel 0 and display on Shuffleboard.
     */
    SmartDashboard.putNumber("Pressure", m_ph.getPressure(0));
    /**
     * Get compressor running status and display on Shuffleboard.
     */
    SmartDashboard.putBoolean("CompRunning", m_ph.getCompressor());

    /**
     * Enable the compressor with analog pressure sensor control.
     *
     * This uses hysteresis between a minimum and maximum pressure value,
     * the compressor will run when the sensor reads below the minimum pressure
     * value, and the compressor will shut off once it reaches the maximum.
     */
    // Get min and max pressure values from Shuffleboard
    double minPressure = SmartDashboard.getNumber("MinPress", 0.0);
    double maxPressure = SmartDashboard.getNumber("MaxPress", 0.0);
    m_ph.enableCompressorAnalog(minPressure, maxPressure);
    // m_ph.enableCompressorAnalog(115, 120);

  }

}
