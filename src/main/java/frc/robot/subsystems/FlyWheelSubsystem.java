package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FlyWheelConstants;

public class FlyWheelSubsystem extends SubsystemBase
{
    private VictorSPX flyWheelMotor1 = new VictorSPX(FlyWheelConstants.kFlywheel1CANId);
    private VictorSPX flyWheelMotor2 = new VictorSPX(FlyWheelConstants.kFlywheel2CANId);

    /* 
    private Encoder flyWheelEncoder1 = new Encoder(FlyWheelConstants.kFlyWheel1RelativeEncoderDIOChannelA, 
        FlyWheelConstants.kFlyWheel1RelativeEncoderDIOChannelB,
        false,
        EncodingType.k1X);

    private DigitalInput proximitySensor = new DigitalInput(FlyWheelConstants.kFlyWheelProximitySensorDIOId);*/

    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(
        FlyWheelConstants.kFlyWheelkS, 
        FlyWheelConstants.kFlyWheelkV, 
        FlyWheelConstants.kFlyWheelkA);

    public FlyWheelSubsystem()
    {
        flyWheelMotor2.setInverted(InvertType.InvertMotorOutput);

        flyWheelMotor1.setNeutralMode(NeutralMode.Coast);
        flyWheelMotor2.setNeutralMode(NeutralMode.Coast);

        //flyWheelEncoder1.setSamplesToAverage(10);

        //flyWheelEncoder1.setDistancePerPulse(FlyWheelConstants.kFlyWheelEncoderVelocityFactor);
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

    }

}
