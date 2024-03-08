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
        counter = 0;
        System.out.println("new shoot " + counter);
    }

    @Override
    public void initialize() {}

    @Override 
    public void execute()
    {
        subsystem.setFlyWheelMotors(1);

        if(subsystem.getProximitySensor())
        {
            if(counter == 0) //triggered for the first time. top of note is triggering it
            {
                counter++;
            }
            else if(counter == 2) //tiggered for the second time bottom of note is triggering it
            {
                counter++;
            }
        }
        else if(counter == 1) //inside the hoop
        {
            counter++;
        }
        else if(counter == 3) //note is past the sensor
        {
            counter++;
        }
    }

    @Override
    public boolean isFinished()
    {
        if(counter == 4)
        {
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted)
    {
        System.out.println("shoot end " + counter);
        subsystem.setFlyWheelVelocity(0); //go back to zero when finished
    }
}