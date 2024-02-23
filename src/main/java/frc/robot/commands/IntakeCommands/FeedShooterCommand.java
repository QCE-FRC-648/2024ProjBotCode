package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ConveyorSubsystem;

//command that will feed a note to the shooter subsystem
public class FeedShooterCommand extends Command
{
    private ConveyorSubsystem subsystem;

    public FeedShooterCommand(ConveyorSubsystem conveyorSubsystem)
    {
        addRequirements(conveyorSubsystem);
        subsystem = conveyorSubsystem;
    }

    @Override
    public void execute()
    {
        subsystem.setConveyorMotors(0); //run converyor motors at low speed
    }
}
