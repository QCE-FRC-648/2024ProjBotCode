package frc.robot.commands.TelescopeCommands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TelescopeSubsystem;

public class ExtendTelescope extends Command
{
    private final TelescopeSubsystem subsystem;

    public ExtendTelescope(TelescopeSubsystem telescopeSubsystem)
    {
        subsystem = telescopeSubsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute()
    {
        subsystem.setTelescopeDistance(Units.inchesToMeters(0));
    }

    @Override
    public boolean isFinished()
    {
        return subsystem.getPidAtSetpoint();
    }

    @Override 
    public void end(boolean interrupted)
    {
        subsystem.setTelescopeMotor(0);
    }
}
