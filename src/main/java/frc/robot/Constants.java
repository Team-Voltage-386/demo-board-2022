// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class ControllerConstants {
        // Joysticks
        public static final int kLeftVertical = 1;
        public static final int kLeftHorizontal = 0;
        public static final int kRightVertical = 5;
        public static final int kRightHorizontal = 4;
        public static final int kLeftTrigger = 2;
        public static final int kRightTrigger = 3;
        // Buttons
        public static final int kA = 1;
        public static final int kB = 2;
        public static final int kX = 3;
        public static final int kY = 4;
        public static final int kLeftBumper = 5;
        public static final int kRightBumper = 6;
        public static final int kLeftOptions = 7;
        public static final int kRightOptions = 8;
        public static final int kLeftJoystickPressed = 9;
        public static final int kRightJoystickPressed = 10;
    }

    // Pneumatics constants
    public static final int kForwardChannel = 2; // PCM
    public static final int kReverseChannel = 3; // PCM
    public static final DoubleSolenoid.Value kPistonOut = DoubleSolenoid.Value.kForward;
    public static final DoubleSolenoid.Value kPistonIn = DoubleSolenoid.Value.kReverse;
    public static final int AnalogPressureChannel = 2; // analog input on Rio
}
