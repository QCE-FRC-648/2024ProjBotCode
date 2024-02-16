package frc.robot.commands.ShooterCommands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

//Command to take in and hold note to score in the amp
public class FlywheelHoldCommand extends Command
{
    private final ShooterSubsystem shooterSubsystem;
    private final DigitalInput proximitySensor;

    public FlywheelHoldCommand(ShooterSubsystem subsystem)
    {
        shooterSubsystem = subsystem;
        addRequirements(shooterSubsystem);

        proximitySensor = shooterSubsystem.getProximitySensor();
    }

    @Override
    public void initialize() { }

    @Override
    public void execute()
    {
        shooterSubsystem.setFlyWheelMotors(0.05);
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
        shooterSubsystem.setFlyWheelMotors(0);
    }
}
