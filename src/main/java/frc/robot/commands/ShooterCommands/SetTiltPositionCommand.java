package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

//Command to set position of the tilt mechanism
public class SetTiltPositionCommand extends Command
{
    private ShooterSubsystem shooterSubsystem; 
    private double setpoint;

    public SetTiltPositionCommand(ShooterSubsystem subsystem, double desiredSetpoint)
    {
        addRequirements(subsystem);
        shooterSubsystem = subsystem;
        setpoint = desiredSetpoint;
    }

    @Override
    public void execute()
    {
        shooterSubsystem.setTiltPosition(setpoint); //reset
    }
}
