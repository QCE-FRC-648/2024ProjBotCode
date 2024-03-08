package frc.robot.commands.OperatorCommands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.FlyWheelCommands.FlyWheelShootCommand;
import frc.robot.commands.IntakeCommands.FeedShooterCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.FlyWheelSubsystem;

public class ShootNoteCommand extends ParallelDeadlineGroup
{
    public ShootNoteCommand(ConveyorSubsystem conveyor, FlyWheelSubsystem flywheel)
    {
        super(new FlyWheelShootCommand(flywheel),
            new WaitCommand(0.4).andThen(new FeedShooterCommand(conveyor)));
        
        /* 
        setDeadline(new FlyWheelShootCommand(flywheel));
        addCommands(
            new WaitCommand(0.4).andThen(new FeedShooterCommand(conveyor)));*/
    }
}
