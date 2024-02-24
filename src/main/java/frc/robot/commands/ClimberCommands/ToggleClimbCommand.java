package frc.robot.commands.ClimberCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;
import edu.wpi.first.wpilibj.PowerDistribution;

public class ToggleClimbCommand extends Command
{
    private final ClimberSubsystem climber;
    private final PowerDistribution PDH;
    private int num;

    public ToggleClimbCommand(ClimberSubsystem subsystem)
    {
        climber = subsystem;
        num = 0;
        PDH = new PowerDistribution();
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
        if(PDH.getCurrent(14) > 50 || PDH.getCurrent(15) > 50){ 
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
