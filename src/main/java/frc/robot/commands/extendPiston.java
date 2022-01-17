// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PneumaticsSubsystem;

public class extendPiston extends CommandBase {

  private final PneumaticsSubsystem m_subsystem;

  /** Creates a new extendPiston. */
  public extendPiston(PneumaticsSubsystem subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_subsystem = subsystem;
    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_subsystem.extendPiston();
  }

  // Called every time the scheduler runs while the command is scheduled.
  // since there is nothing that needs to execute on a continuous basis, no code
  // is needed here
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // this always returns true because moving the piston only needs to execute once
    return true;
  }
}
