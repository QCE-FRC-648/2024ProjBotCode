package frc.robot.commands.ClimberCommands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;
import edu.wpi.first.wpilibj.Timer;

public class ToggleClimbCommand extends Command
{
    private final ClimberSubsystem climber;

    public ToggleClimbCommand(ClimberSubsystem subsystem)
    {
        climber = subsystem;
    }

    @Override
    public void initialize() { }

    @Override
    public void execute() {
        climber.climber1.set(ControlMode.PercentOutput, 0.1);
        climber.climber2.set(ControlMode.PercentOutput, 0.1);
    }

    @Override
    public boolean isFinished()
    {
        Timer.delay(1);
        return true;
    }

    @Override 
    public void end(boolean interrupted)
    {
        climber.climber1.set(ControlMode.PercentOutput, 0);
        climber.climber2.set(ControlMode.PercentOutput, 0);
    }
}
