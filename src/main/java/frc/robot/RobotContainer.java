// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ButtonCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ButtonSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final ButtonSubsystem buttonSubsystem = new ButtonSubsystem();
  private final LEDSubsystem ledSubsystem = new LEDSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final ButtonCommand buttonCommand = new ButtonCommand(buttonSubsystem);

  public static final int BUTTON_PORT = 0;

  public static DigitalInput button = new DigitalInput(RobotContainer.BUTTON_PORT);
  public static final Joystick controller = new Joystick(0);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // configure default commands
    buttonSubsystem.setDefaultCommand(buttonCommand);
    // ledSubsystem.setDefaultCommand(new Command());
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() { // Create the controller button objects

    // Connect the buttons to commands
    // Instant commands to call methods in the LED subsystem

    new JoystickButton(controller,
        Constants.ControllerConstants.kRightBumper).whenPressed(
            new InstantCommand(ledSubsystem::MoveLights,
                ledSubsystem));

    new JoystickButton(controller,
        Constants.ControllerConstants.kLeftBumper).whenPressed(
            new InstantCommand(ledSubsystem::HalfBlueRed,
                ledSubsystem));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
