package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class FlywheelShootCommand extends Command
{
    private ShooterSubsystem shooterSubsystem;
    private int switchCount = 0; //counter for proximity switch

    public FlywheelShootCommand(ShooterSubsystem subsystem)
    {
        addRequirements(subsystem);
        shooterSubsystem = subsystem;
    }

    @Override
    public void initialize() { }

    @Override
    public void execute() 
    {
        shooterSubsystem.setFlyWheelVelocity(0); //velocity in meters per second
    }

    //returns true when shooter is at setpoint
    @Override
    public boolean isFinished()
    {
        
        return false;
    }

    @Override
    public void end(boolean interrupted)
    { 

    }
}
