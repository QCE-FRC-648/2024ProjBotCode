package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TelescopeConstants;

public class TelescopeSubsystem extends SubsystemBase
{
    private VictorSPX telescopeMotor = new VictorSPX(TelescopeConstants.kTelescopeCANId);

    private DutyCycleEncoder encoder = new DutyCycleEncoder(TelescopeConstants.kTelescopeDutyCycleEncoderDIOId);

    private PIDController telescopePIDController = new PIDController(
        TelescopeConstants.kTelescopePositionP,
        TelescopeConstants.kTelescopePositionI,
        TelescopeConstants.kTelescopePositionD);

    //Network tables
    private final NetworkTableInstance instance = NetworkTableInstance.getDefault();
    //relative rotations, absolute position, distance, distance per rotation, frequency
    private final DoublePublisher encoderRotationsPublisher;
    private final DoublePublisher encoderAbsolutePositionPublisher;
    private final DoublePublisher encoderDistancePublisher;
    private final DoublePublisher encoderDistancePerRotationPublisher;
    private final DoublePublisher encoderFrequencyPublisher;

    public TelescopeSubsystem()
    {
        telescopeMotor.setNeutralMode(NeutralMode.Brake);

        encoder.setDistancePerRotation(TelescopeConstants.kTelescopeEncoderPositionFactor);

        encoderRotationsPublisher = instance.getDoubleTopic("TelescopeSubsystem/Encoder/RelativeRotations").publish();
        encoderAbsolutePositionPublisher = instance.getDoubleTopic("TelescopeSubsystem/Encoder/AbsolutePosition").publish();
        encoderDistancePublisher = instance.getDoubleTopic("TelescopeSubsystem/Encoder/Distance").publish();
        encoderDistancePerRotationPublisher = instance.getDoubleTopic("TelescopeSubsystem/Encoder/DistancePerRotations").publish();
        encoderFrequencyPublisher = instance.getDoubleTopic("TelescopeSubsystem/Encoder/Frequency").publish();


    }

    public void setTelescopeMotor(double desiredPower)
    {
        telescopeMotor.set(ControlMode.PercentOutput, desiredPower);
    }

    public void setTelescopeDistance(double desiredDistance)
    {
        telescopeMotor.set(ControlMode.PercentOutput,
            telescopePIDController.calculate(encoder.getDistance(), desiredDistance));
    }

    @Override
    public void periodic()
    {
        encoderRotationsPublisher.set(encoder.get());
        encoderAbsolutePositionPublisher.set(encoder.getAbsolutePosition());
        encoderDistancePublisher.set(encoder.getDistance());
        encoderDistancePerRotationPublisher.set(encoder.getDistancePerRotation());
        encoderFrequencyPublisher.set(encoder.getFrequency());
    }
}
