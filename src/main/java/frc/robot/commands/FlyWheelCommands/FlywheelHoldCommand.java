package frc.robot.commands.FlyWheelCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FlyWheelSubsystem;

//Command to take in and hold note to score in the amp
public class FlywheelHoldCommand extends Command
{
    private final FlyWheelSubsystem subsystem;

    private enum Stage { 
        feedIntake,
        sensorTriggered,
        end
    }

    private Stage stage;

    private double[] setpointDistance;

    public FlywheelHoldCommand(FlyWheelSubsystem flyWheelSubsystem)
    {
        subsystem = flyWheelSubsystem;
        subsystem.setFlyWheelMotorsBrake();
        addRequirements(subsystem);
    }

    @Override
    public void initialize() 
    { 
        stage = Stage.feedIntake;
    }

    @Override
    public void execute()
    {
        switch(stage)
        {
            case feedIntake:
                subsystem.setFlyWheelMotors(0.2);

                if(subsystem.getProximitySensor())
                {
                    setpointDistance = subsystem.getEncoderDistance();
                    setpointDistance[0] += 0.2;
                    setpointDistance[1] += 0.2;

                    stage = Stage.sensorTriggered;
                }

                break;
            case sensorTriggered:
                double[] currentDist = subsystem.getEncoderDistance();
                if(currentDist[0] >= setpointDistance[0] && currentDist[1] >= setpointDistance[1])
                {
                    stage = Stage.end;
                }
            case end:
                break;
                
        }
    }

    //ends when proximity sensor is active
    @Override
    public boolean isFinished()
    {
        if(stage == Stage.end)
        {
            return true;
        }

        return false;
    }

    @Override 
    public void end(boolean interrupted)
    {
        subsystem.setFlyWheelMotors(0);
    }
}
