package frc.robot.commands.ClimberCommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;

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
        
    }

    @Override
    public boolean isFinished()
    {
        if(climber.climber1.getOutputCurrent() > 50)
        {
            return true;
        }
        return false;
    }

    @Override 
    public void end(boolean interrupted)
    {
        climber.climber1.set(ControlMode.PercentOutput, 0);
    }
}
