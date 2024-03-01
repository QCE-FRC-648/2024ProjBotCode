package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.AnalogInput;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FlyWheelConstants;

public class FlyWheelSubsystem extends SubsystemBase
{
    private WPI_VictorSPX flyWheelMotor1 = new WPI_VictorSPX(FlyWheelConstants.kFlywheel1CANId);
    private WPI_VictorSPX flyWheelMotor2 = new WPI_VictorSPX(FlyWheelConstants.kFlywheel2CANId);

     
    private Encoder flyWheelEncoder1 = new Encoder(FlyWheelConstants.kFlyWheel1RelativeEncoderDIOChannelA, 
        FlyWheelConstants.kFlyWheel1RelativeEncoderDIOChannelB,
        false,
        EncodingType.k1X);

    private AnalogInput proximitySensor = new AnalogInput(FlyWheelConstants.kFlyWheelProximitySensorDIOId);
    

    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(
        FlyWheelConstants.kFlyWheelkS, 
        FlyWheelConstants.kFlyWheelkV, 
        FlyWheelConstants.kFlyWheelkA);

    private final NetworkTableInstance instance;
    private final DoublePublisher flyWheel1PrecentPublisher;
    private final DoublePublisher flyWheel2PrecentPublisher;

    private final DoublePublisher proximityBitPublisher;
    private final DoublePublisher proximityAverageBitPublisher;
    private final DoublePublisher proximityVoltPublisher;

    public FlyWheelSubsystem()
    {
        flyWheelMotor1.setInverted(InvertType.InvertMotorOutput);

        flyWheelMotor1.setNeutralMode(NeutralMode.Coast);
        flyWheelMotor2.setNeutralMode(NeutralMode.Coast);

        flyWheelEncoder1.setSamplesToAverage(10);

        flyWheelEncoder1.setDistancePerPulse(FlyWheelConstants.kFlyWheelEncoderVelocityFactor);

        instance = NetworkTableInstance.getDefault();
        flyWheel1PrecentPublisher = instance.getDoubleTopic("/FlyWheelSubsystem/MotorsInfo/FlyWheel1/Precent").publish();
        flyWheel2PrecentPublisher = instance.getDoubleTopic("/FlyWheelSubsystem/MotorsInfo/FlyWheel2/Precent").publish();

        proximityBitPublisher = instance.getDoubleTopic("/FlyWheelSubsystem/Sensors/ProximitySensor/Bits").publish();
        proximityAverageBitPublisher = instance.getDoubleTopic("/FlyWheelSubsystem/Sensors/ProximitySensor/Bits").publish();
        proximityVoltPublisher = instance.getDoubleTopic("/FlyWheelSubsystem/Sensors/ProximitySensor/Volts").publish();
    }

    /**
     * Method to set fly wheel motor power
     *  
     * @param desiredSpeed desired speed of the motors
     */
    public void setFlyWheelMotors(double desiredPower)
    {
        flyWheelMotor1.set(ControlMode.PercentOutput, desiredPower);
        flyWheelMotor2.set(ControlMode.PercentOutput, desiredPower);
    }

    /**
     * Method to set fly wheel motor velocity in meters per second
     * 
     * @param desiredVelocity desired velocity of the motors in meters per second
     */
    public void setFlyWheelVelocity(double desiredVelocity)
    {
        double feedForwardVal = feedforward.calculate(desiredVelocity);

        flyWheelMotor1.setVoltage(feedForwardVal);
        flyWheelMotor2.setVoltage(feedForwardVal);
    }

    /* 
    public boolean getProximitySensor()
    {
        return proximitySensor.get();
    }*/

    public void setFlyWheelMotorsCoast()
    {
        flyWheelMotor1.setNeutralMode(NeutralMode.Coast);
        flyWheelMotor2.setNeutralMode(NeutralMode.Coast);
    }

    public void setFlyWheelMotorsBrake()
    {
        flyWheelMotor1.setNeutralMode(NeutralMode.Brake);
        flyWheelMotor2.setNeutralMode(NeutralMode.Brake);
    }


    @Override
    public void periodic()
    {
        proximityBitPublisher.set(proximitySensor.getValue());
        proximityAverageBitPublisher.set(proximitySensor.getAverageBits());
        proximityVoltPublisher.set(proximitySensor.getVoltage());
    }
}
