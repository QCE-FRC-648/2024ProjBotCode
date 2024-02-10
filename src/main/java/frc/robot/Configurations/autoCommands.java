package frc.robot.Configurations;

// This is a new vendor library that needs to be added in https://3015rangerrobotics.github.io/pathplannerlib/PathplannerLib.json
import com.pathplanner.lib.auto.NamedCommands;

// Include imports for each subsystem commands here
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.Commands.flywheelCommands.FlywheelHoldCommand;

public class autoCommands 
{
    private static ShooterSubsystem shooterSubsystem;

    public static void Register()
    {
        // This is where you'll put comands that you want to add to the auto paths
        NamedCommands.registerCommand("flywheel hold", new FlywheelHoldCommand(shooterSubsystem));
    }
}
