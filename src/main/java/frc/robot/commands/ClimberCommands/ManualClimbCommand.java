package frc.robot.commands.ClimberCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.RobotContainer;

public class ManualClimbCommand extends Command{

    private final ClimberSubsystem climber;
    private double rightY;

    public ManualClimbCommand(ClimberSubsystem subsystem)
    {
        climber = subsystem;
    }
    
    @Override
    public void initialize() { }

    @Override
    public void execute() {
        rightY = RobotContainer.operatorController.getRightY();
        climber.climber1.set(rightY);
        climber.climber2.set(rightY);
    }

    @Override
    public boolean isFinished()
    {
        if(RobotContainer.operatorController.getRightY() < 0.2 && RobotContainer.operatorController.getRightY() > -0.2) {
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
