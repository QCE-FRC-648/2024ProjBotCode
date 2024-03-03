package frc.robot.commands.OperatorCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.FlyWheelCommands.FlyWheelShootCommand;
import frc.robot.commands.IntakeCommands.FeedShooterCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.FlyWheelSubsystem;

public class ShootNoteCommand extends ParallelCommandGroup
{
    public ShootNoteCommand(ConveyorSubsystem conveyor, FlyWheelSubsystem flywheel)
    {
        addCommands(
            new WaitCommand(0.4).andThen(new FeedShooterCommand(conveyor)),
            new FlyWheelShootCommand(flywheel)
        );
    }
}
