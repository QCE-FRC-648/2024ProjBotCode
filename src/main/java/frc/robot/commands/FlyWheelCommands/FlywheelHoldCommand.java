package frc.robot.commands.FlyWheelCommands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FlyWheelSubsystem;

//Command to take in and hold note to score in the amp
public class FlywheelHoldCommand extends Command
{
    private final FlyWheelSubsystem subsystem;

    public FlywheelHoldCommand(FlyWheelSubsystem flyWheelSubsystem)
    {
        subsystem = flyWheelSubsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() { }

    @Override
    public void execute()
    {
        subsystem.setFlyWheelMotors(1); //need to change
    }

    //ends when proximity sensor is active
    @Override
    public boolean isFinished()
    {
        /* 
        if(!subsystem.getProximitySensor())
        {
            return true;
        }*/

        return false;
    }

    @Override 
    public void end(boolean interrupted)
    {
        subsystem.setFlyWheelMotors(0);
    }
}
