// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.sensors.WPI_PigeonIMU;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import edu.wpi.first.math.*;

public class PigeonSubsystem extends SubsystemBase {
  /** Creates a new PigeonSubsystem. */
  // Creates a shuffleboard tab for the drive
  private ShuffleboardTab tab = Shuffleboard.getTab("Pigeon");

  // Create output widgets
  private NetworkTableEntry YawWidget = tab.add("Yaw", 0).withPosition(0, 0).getEntry();
  private NetworkTableEntry RollWidget = tab.add("Roll", 0).withPosition(1, 0).getEntry();
  private NetworkTableEntry PitchWidget = tab.add("Pitch", 0).withPosition(2, 0).getEntry();

  /** pigeon instance, this is instantiated later, leave this alone */
  WPI_PigeonIMU pidgey;
  final int kPigeonID = 12;

  public PigeonSubsystem() {
    pidgey = new WPI_PigeonIMU(kPigeonID);
    pidgey.configFactoryDefault();
    // RemoteSensorSource senSource = RemoteSensorSource.Pigeon_Yaw;
    // org.teamvoltage.Logger.getInstance().addDataSource("Pigeon Yaw",
    // pidgey::getYaw);
  }

  private double truncate(double num, int decimalplace) {
    double n;
    double power;

    power = Math.pow(10, decimalplace);
    n = Math.floor(num * power) / power;
    return n;

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    int digitsPrecision = 2;

    YawWidget.setDouble(truncate(pidgey.getYaw(), digitsPrecision));
    RollWidget.setDouble(truncate(pidgey.getRoll(), digitsPrecision));
    PitchWidget.setDouble(truncate(pidgey.getPitch(), digitsPrecision));

  }
}
