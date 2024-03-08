package frc.robot.commands.TiltCommands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TiltSubsystem;

public class FullyTiltCommand extends Command
{
    private final TiltSubsystem subsystem;

    public FullyTiltCommand(TiltSubsystem telescopeSubsystem)
    {
        subsystem = telescopeSubsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute()
    {
        subsystem.setTiltPosition(Units.inchesToMeters(5));
    }

    @Override
    public boolean isFinished()
    {
        return subsystem.getPidAtSetpoint();
    }

    @Override 
    public void end(boolean interrupted)
    {
        subsystem.setTiltMotor(0);
    }
}
