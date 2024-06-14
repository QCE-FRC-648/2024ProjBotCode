package frc.robot.commands.ClimberCommands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;

public class ManualClimbCommand extends Command
{

    private final ClimberSubsystem climber;
    private final double speed;

    public ManualClimbCommand(ClimberSubsystem subsystem, Double _speed)
    {
        addRequirements(subsystem);
        climber = subsystem;
        speed = _speed;
    }
    
    @Override
    public void initialize() { }

    @Override
    public void execute() 
    {
        SmartDashboard.putNumber("climber speed", speed);
        climber.setClimberSpeeds(speed);
    }

    @Override
    public boolean isFinished()
    {
        if(climber.getLimitSwitchRight() || climber.getLimitSwitchLeft())
        {
            return false;
        }
        /*if(climber.getCurrent(ClimberConstants.kClimber1PDH) > ClimberConstants.currentMax || climber.getCurrent(ClimberConstants.kClimber2PDH) > ClimberConstants.currentMax) 
        {
            return true;
        }*/
        return false;
    }

    @Override 
    public void end(boolean interrupted)
    {
        climber.setClimberSpeeds(0);
    }

}
