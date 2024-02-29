package frc.robot.commands.FlyWheelCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FlyWheelSubsystem;

public class FlyWheelShootCommand extends Command
{
    FlyWheelSubsystem subsystem;

    public FlyWheelShootCommand(FlyWheelSubsystem flyWheelSubsystem)
    {
        subsystem = flyWheelSubsystem;
    }

    @Override
    public void initialize() {}

    @Override 
    public void execute()
    {
        subsystem.setFlyWheelVelocity(0);
    }

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