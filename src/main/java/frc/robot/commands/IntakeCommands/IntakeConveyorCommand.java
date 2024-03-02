package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ConveyorSubsystem;

public class IntakeConveyorCommand extends Command 
{
    private final ConveyorSubsystem conveyor;

    public IntakeConveyorCommand(ConveyorSubsystem subsystem)
    {
        addRequirements(subsystem);
        conveyor = subsystem;
    }

    @Override
    public void initialize() { }

    @Override 
    public void execute() {
        conveyor.setConveyorMotors(0.15);
        conveyor.setIntakeMotor(0.35);
    }

    @Override
    public boolean isFinished()
    {
        if(conveyor.getProximitySensor())
        {
            return true;
        }
        return false;
    }

    @Override 
    public void end(boolean interrupted)
    {
        conveyor.setConveyorMotors(0);
        conveyor.setIntakeMotor(0);
    }

}
