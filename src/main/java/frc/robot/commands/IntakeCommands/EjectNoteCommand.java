package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ConveyorSubsystem;

public class EjectNoteCommand extends Command 
{
    private final ConveyorSubsystem subsystem;
    private double leftTrig;

    public EjectNoteCommand(ConveyorSubsystem conveyorSubsystem, double leftTrigger)
    {
        addRequirements(conveyorSubsystem);
        subsystem = conveyorSubsystem;
        leftTrig = leftTrigger;
    }

    @Override
    public void initialize() { }

    @Override 
    public void execute()
    {
        subsystem.setConveyorMotors(-0.2);
        subsystem.setIntakeMotor(-0.5);
    }

    @Override
    public boolean isFinished()
    {
        if(leftTrig < 0.1) {
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