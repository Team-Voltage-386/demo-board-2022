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

  // Create ball pickup solenoid widgets
  // private NetworkTableEntry ballPickupWidget = Shuffleboard.("Intake Deployed",
  // false).withSize(2, 1).withPosition(2, 4)
  // .getEntry();

  // Creates a Shuffleboard tab for the pneumatics subsystem
  private ShuffleboardTab tab = Shuffleboard.getTab("Pneumatics");

  // Create sensor widgets
  private NetworkTableEntry PressureWidget = tab.add("Pressure", false).withPosition(7, 3).withSize(2, 1).getEntry();

  // create an object so we can read the pressure from the pneumatics hub
  double current_pressure;
  private static final int PH_CAN_ID = 1;
  PneumaticHub m_ph = new PneumaticHub(PH_CAN_ID);

  private final DoubleSolenoid sol = new DoubleSolenoid(
      PneumaticsModuleType.REVPH,
      Constants.kForwardChannel,
      Constants.kReverseChannel);

  /** Creates a new PneumaticsSubsystem. */
  public PneumaticsSubsystem() {

    SmartDashboard.setDefaultBoolean("Enable Compressor Analog", false);
    SmartDashboard.setDefaultBoolean("Disable Compressor", false);

    // Add number inputs for minimum and maximum pressure
    SmartDashboard.setDefaultNumber("Minimum Pressure (PSI)", 100.0);
    SmartDashboard.setDefaultNumber("Maximum Pressure (PSI)", 120.0);
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
    /**
     * Get pressure from analog channel 0 and display on Shuffleboard.
     */
    SmartDashboard.putNumber("Pressure", m_ph.getPressure(0));

    /**
     * Get compressor running status and display on Shuffleboard.
     */
    SmartDashboard.putBoolean("Compressor Running", m_ph.getCompressor());

    // Enable Compressor Analog button
    if (SmartDashboard.getBoolean("Enable Compressor Analog", false)) {
      SmartDashboard.putBoolean("Enable Compressor Analog", false);

      // Get values from Shuffleboard
      double minPressure = SmartDashboard.getNumber("Minimum Pressure (PSI)", 0.0);
      double maxPressure = SmartDashboard.getNumber("Maximum Pressure (PSI)", 0.0);

      /**
       * Enable the compressor with analog pressure sensor control.
       *
       * This uses hysteresis between a minimum and maximum pressure value,
       * the compressor will run when the sensor reads below the minimum pressure
       * value, and the compressor will shut off once it reaches the maximum.
       *
       *
       */
      m_ph.enableCompressorAnalog(minPressure, maxPressure);
    }

    // Disable Compressor button
    if (SmartDashboard.getBoolean("Disable Compressor", false)) {
      SmartDashboard.putBoolean("Disable Compressor", false);

      /**
       * Disable the compressor.
       */
      m_ph.disableCompressor();
    }
  }

}
