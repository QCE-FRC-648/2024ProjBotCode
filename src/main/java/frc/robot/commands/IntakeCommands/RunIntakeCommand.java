package frc.robot.commands.IntakeCommands;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ConveyorSubsystem;

public class RunIntakeCommand extends Command 
{
    private final ConveyorSubsystem conveyorSubsystem;
    private final DigitalInput proximitySensor;


    public RunIntakeCommand(ConveyorSubsystem subsystem)
    {
        conveyorSubsystem = subsystem;

        proximitySensor = conveyorSubsystem.getProximitySensor();
    }

    @Override
    public void initialize() { }

    @Override 
    public void execute()
    {
        conveyorSubsystem.setConveyorMotors(0);
        conveyorSubsystem.setIntakeMotor(0);
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
        conveyorSubsystem.setConveyorMotors(0);
        conveyorSubsystem.setIntakeMotor(0);
    }

}
