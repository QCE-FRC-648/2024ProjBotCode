package frc.robot.Commands.auto;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class auto1 extends ParallelCommandGroup
{
    public auto1()
    {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());
        addCommands(new PathPlannerAuto("Auto 1")); // This is where you'll add Path Planner Auto Names
    }
}
