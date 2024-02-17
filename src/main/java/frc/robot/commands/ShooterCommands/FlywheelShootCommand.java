package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootNoteCommand extends Command
{
    private ShooterSubsystem shooterSubsystem;

    public ShootNoteCommand(ShooterSubsystem subsystem)
    {
        addRequirements(subsystem);
        shooterSubsystem = subsystem;
    }

    @Override
    public void initialize() { }

    @Override
    public void execute() 
    {
        
    }

    //returns true when shooter is at setpoint
    @Override
    public boolean isFinished()
    {
        if(shooterSubsystem.getFlyWheelPID().atSetpoint())
        {
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted)
    {

    }
}
