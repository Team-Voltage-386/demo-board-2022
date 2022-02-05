package frc.robot.commands;

import frc.robot.subsystems.ButtonSubsystem;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** A button command that uses a button subsystem. */
public class ButtonCommand extends CommandBase {
  private final ButtonSubsystem aSubsystem;

  /**
   * Creates a new ButtonCommand.
   */
  public ButtonCommand(ButtonSubsystem subsystem) {
    aSubsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    aSubsystem.Flash(RobotContainer.button.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
