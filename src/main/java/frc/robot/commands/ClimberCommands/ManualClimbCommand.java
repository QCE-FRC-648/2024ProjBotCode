package frc.robot.commands.ClimberCommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.Constants.ClimberConstants;

public class ManualClimbCommand extends Command{

    private final ClimberSubsystem climber;
    private final Supplier<Double> rightJoystickY;

    public ManualClimbCommand(ClimberSubsystem subsystem, Supplier<Double> _rightJoystickY)
    {
        addRequirements(subsystem);
        climber = subsystem;
        rightJoystickY = _rightJoystickY;
    }
    
    @Override
    public void initialize() { }

    @Override
    public void execute() 
    {
        double rightY = rightJoystickY.get();

        climber.setClimberSpeeds(rightY);
    }

    @Override
    public boolean isFinished()
    {
        if(climber.getCurrent(ClimberConstants.kClimber1PDH) > ClimberConstants.currentMax || climber.getCurrent(ClimberConstants.kClimber2PDH) > ClimberConstants.currentMax) 
        {
            return true;
        }
        return false;
    }

    @Override 
    public void end(boolean interrupted)
    {
        climber.setClimberSpeeds(0);
    }

}
