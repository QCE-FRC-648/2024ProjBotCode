package frc.robot.Commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Conveyor;

public class IntakeConveyorCommand extends Command {
    private final Conveyor conveyor;
    private final DigitalInput proximitySensor;


    public IntakeConveyorCommand(Conveyor subsystem)
    {
        conveyor = subsystem;

        proximitySensor = conveyor.getProximitySensor();
    }

    @Override
    public void initialize() { }

    @Override 
    public void execute() {
        conveyor.intakeMotor.set(ControlMode.PercentOutput, 0.1);
        conveyor.conveyorMotor.set(ControlMode.PercentOutput, 0.1);
    }

    @Override
    public boolean isFinished()
    {
        if(!proximitySensor.get())
        {
            return true;
        }
        return false;
    }

    @Override 
    public void end(boolean interrupted)
    {
        conveyor.intakeMotor.set(ControlMode.PercentOutput, 0);
        conveyor.conveyorMotor.set(ControlMode.PercentOutput, 0);
    }

}
