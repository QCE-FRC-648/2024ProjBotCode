package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class SetTelescopeCommand extends Command
{
    private ShooterSubsystem shooterSubsystem;

    public SetTelescopeCommand(ShooterSubsystem subsystem)
    {
        addRequirements(subsystem);
        shooterSubsystem = subsystem;
    }
}
