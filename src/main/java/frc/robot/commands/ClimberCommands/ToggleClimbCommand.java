package frc.robot.commands.ClimberCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.ClimberSubsystem;

public class ToggleClimbCommand extends Command
{
    private final ClimberSubsystem climber;
    
    private int num;

    public ToggleClimbCommand(ClimberSubsystem subsystem)
    {
        climber = subsystem;
        num = 0;
    }

    @Override
    public void initialize() {
        num++;
    }

    @Override
    public void execute() {
        if(num%2 == 1) {
            climber.climber1.set(0.1);
            climber.climber2.set(0.1);
        }
        else {
            climber.climber1.set(-0.1);
            climber.climber2.set(-0.1);
        }
    }

    @Override
    public boolean isFinished()
    {
        if(climber.PDH.getCurrent(14) > ClimberConstants.currentMax || climber.PDH.getCurrent(15) > ClimberConstants.currentMax){ 
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
