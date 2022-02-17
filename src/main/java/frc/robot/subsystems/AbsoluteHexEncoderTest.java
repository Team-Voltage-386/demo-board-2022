// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DutyCycle;
import java.util.Map;

public class AbsoluteHexEncoderTest extends SubsystemBase {
  DutyCycleEncoder m_AbsoluteEncoder;
  ShuffleboardTab m_tab = Shuffleboard.getTab("Absolute Encoder");
  NetworkTableEntry m_AbsolutEntry_output1 = m_tab.add("AbsoluteEncoder", 0.0).withPosition(1, 1).withSize(1, 1)
      .withWidget(BuiltInWidgets.kNumberBar).withProperties(Map.of("min", 0, "max", 360)).getEntry();
  double offset=0.0;//I assume the mechanical people cannot perfectly mount the encoder so we need to zero it out ourselves

  /** Creates a new AbsoluteHexEncoderTest. */
  public AbsoluteHexEncoderTest() {
    m_AbsoluteEncoder = new DutyCycleEncoder(new DigitalInput(4));
    m_AbsoluteEncoder.setDistancePerRotation(360.0);// degrees per rotation
  }

  @Override
  public void periodic() {
    m_AbsolutEntry_output1.setDouble(m_AbsoluteEncoder.getDistance()+offset);
  }

  public void resetEncoder() {
    m_AbsoluteEncoder.reset();
  }
}
