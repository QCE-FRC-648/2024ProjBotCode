package frc.robot.commands.OperatorCommands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.FlyWheelCommands.FlywheelHoldCommand;
import frc.robot.commands.IntakeCommands.FeedShooterCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.FlyWheelSubsystem;

public class PassNoteCommand extends ParallelDeadlineGroup
{
    public PassNoteCommand(ConveyorSubsystem conveyor, FlyWheelSubsystem flyWheel)
    {
        super(new FlywheelHoldCommand(flyWheel), new FeedShooterCommand(conveyor));
    }
}
