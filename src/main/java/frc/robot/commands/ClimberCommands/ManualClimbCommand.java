package frc.robot.commands.ClimberCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.Constants.ClimberConstants;

public class ManualClimbCommand extends Command{

    private final ClimberSubsystem climber;
    private double rightY;

    public ManualClimbCommand(ClimberSubsystem subsystem, double Y)
    {
        addRequirements(subsystem);
        climber = subsystem;
        rightY = Y;
    }
    
    @Override
    public void initialize() { }

    @Override
    public void execute() {
        
        climber.climber1.set(rightY);
        climber.climber2.set(rightY);
    }

    @Override
    public boolean isFinished()
    {
        if(climber.PDH.getCurrent(14) > ClimberConstants.currentMax || climber.PDH.getCurrent(15) > ClimberConstants.currentMax) {
            return true;
        }
        return false;
    }

    @Override 
    public void end(boolean interrupted)
    {
        climber.climber1.set(0);
        climber.climber2.set(0);
    }

}