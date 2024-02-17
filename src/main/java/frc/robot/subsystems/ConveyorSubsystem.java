package frc.robot.subsystems;
import frc.robot.Constants.ConveyorConstants;
import frc.robot.Constants.ShooterConstants;
import com.ctre.phoenix.motorcontrol.ControlMode;  
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;


public class ConveyorSubsystem extends SubsystemBase
{

    private VictorSPX conveyorMotor1 = new VictorSPX(ConveyorConstants.conveyorCANID19);
    private VictorSPX conveyorMotor2 = new VictorSPX(ConveyorConstants.conveyorCANID19);
    private VictorSPX intakeMotor = new VictorSPX(ConveyorConstants.intakeCAN);
    private DigitalInput proximitySensor = new DigitalInput(ShooterConstants.kShooterProximitySensorDIOId);

    public ConveyorSubsystem() { }

    public void setConveyorMotors(double output)
    {
        conveyorMotor1.set(ControlMode.PercentOutput, output);
        conveyorMotor2.set(ControlMode.PercentOutput, output);
    }

    public void setIntakeMotor(double output)
    {
        intakeMotor.set(ControlMode.PercentOutput, output);
    }

    public DigitalInput getProximitySensor()
    {
        return proximitySensor;
    }
    
    @Override
    public void periodic() { }
}
