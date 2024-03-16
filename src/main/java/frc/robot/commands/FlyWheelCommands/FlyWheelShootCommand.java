package frc.robot.commands.FlyWheelCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FlyWheelSubsystem;

public class FlyWheelShootCommand extends Command
{
    private FlyWheelSubsystem subsystem;
    private int counter;

    public FlyWheelShootCommand(FlyWheelSubsystem flyWheelSubsystem)
    {
        subsystem = flyWheelSubsystem;
        subsystem.setFlyWheelMotorsCoast();
        addRequirements(subsystem);
        System.out.println("new shoot " + counter);
    }

    @Override
    public void initialize() 
    {
        counter = 0;
    }

    @Override 
    public void execute()
    {
        subsystem.setFlyWheelMotors(1);

        if(subsystem.getProximitySensor())
        {
            if(counter == 0)
            {
                counter++;
            }
            else if(counter == 1)
            {
                counter++;
            }
        }

    }

    @Override
    public boolean isFinished()
    {
        if(!subsystem.getProximitySensor() && counter ==2)
        {
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted)
    {
        subsystem.setFlyWheelVelocity(0); //go back to zero when finished
        counter = 0;
    }
}