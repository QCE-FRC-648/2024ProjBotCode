package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ConveyorSubsystem;

public class RunIntakeCommand extends Command 
{
    private final ConveyorSubsystem subsystem;
    private final DigitalInput proximitySensor;


    public RunIntakeCommand(ConveyorSubsystem conveyorSubsystem)
    {
        addRequirements(conveyorSubsystem);
        subsystem = conveyorSubsystem;

        proximitySensor = conveyorSubsystem.getProximitySensor();
    }

    @Override
    public void initialize() { }

    @Override 
    public void execute()
    {
        subsystem.setConveyorMotors(0.2);
        subsystem.setIntakeMotor(0.5);
    }

    @Override
    public boolean isFinished()
    {
        if(!proximitySensor.get())
        {
            return true;
        }
        return false;
    }

    @Override 
    public void end(boolean interrupted)
    {
        subsystem.setConveyorMotors(0);
        subsystem.setIntakeMotor(0);
    }

}
