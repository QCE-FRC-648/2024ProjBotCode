package frc.robot.commands.ClimberCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.RobotContainer;

public class MannualClimbCommand extends Command{

    private final ClimberSubsystem climber;
    private double leftY;

    public MannualClimbCommand(ClimberSubsystem subsystem)
    {
        climber = subsystem;
    }
    
    @Override
    public void initialize() { }

    @Override
    public void execute() {
        leftY = RobotContainer.operatorController.getLeftY();
        climber.climber1.set(leftY);
        climber.climber2.set(leftY);
    }

    @Override
    public boolean isFinished()
    {
        if(RobotContainer.operatorController.getLeftY() < 0.2 && RobotContainer.operatorController.getLeftY() > 0.2) {
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
