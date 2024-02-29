package frc.robot.subsystems;
import frc.robot.Constants.ConveyorConstants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;


public class ConveyorSubsystem extends SubsystemBase
{

    private VictorSPX conveyorMotor1 = new VictorSPX(ConveyorConstants.conveyorCANID19);
    private VictorSPX conveyorMotor2 = new VictorSPX(ConveyorConstants.conveyorCANID20);
    private CANSparkMax intakeMotor = new CANSparkMax(ConveyorConstants.intakeCAN, MotorType.kBrushless);
    private AnalogInput proximitySensorAI = new AnalogInput(ConveyorConstants.kConveyorProximitySensorDIOId);

    private final DoublePublisher proximityVoltPublisher;

    public ConveyorSubsystem() 
    { 
        conveyorMotor1.setInverted(InvertType.InvertMotorOutput);

        
        proximityVoltPublisher = NetworkTableInstance.getDefault().
            getDoubleTopic("/ProximitySensor/ConveyorSensor/Volts").publish();
    }

    public void setConveyorMotors(double output)
    {
        conveyorMotor1.set(ControlMode.PercentOutput, output);
        conveyorMotor2.set(ControlMode.PercentOutput, output);
    }

    public void setIntakeMotor(double output)
    {
        intakeMotor.set(output);
    }

    public boolean getProximitySensor()
    {
        return true;
    }
    
    @Override
    public void periodic() 
    { 
        proximityVoltPublisher.set(proximitySensorAI.getVoltage());
    }
}
