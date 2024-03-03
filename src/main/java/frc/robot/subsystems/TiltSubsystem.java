package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TiltConstants;

public class TiltSubsystem extends SubsystemBase
{
    private VictorSPX tiltMotor = new VictorSPX(TiltConstants.kTiltCANId);

    private DutyCycleEncoder encoder = new DutyCycleEncoder(TiltConstants.kTiltDutyCycleEncoderDIOId);

    private PIDController tiltPIDController = new PIDController(
        TiltConstants.kTiltPositionP, 
        TiltConstants.kTiltPositionI, 
        TiltConstants.kTiltPositionD);

    private final NetworkTableInstance instance = NetworkTableInstance.getDefault();
    private final DoublePublisher encoderRotationsPublisher;
    private final DoublePublisher encoderAbsolutePositionPublisher;
    private final DoublePublisher encoderDistancePublisher;
    private final DoublePublisher encoderDistancePerRotationPublisher;
    private final DoublePublisher encoderFrequencyPublisher;

    public TiltSubsystem()
    {
        tiltMotor.setNeutralMode(NeutralMode.Brake);

        encoder.setDistancePerRotation(TiltConstants.kTiltEncoderPositionFactor);
        encoder.setDutyCycleRange(0, 2 * Math.PI);

        tiltPIDController.setTolerance(0);

        encoderRotationsPublisher = instance.getDoubleTopic("TiltSubsystem/Encoder/RelativeRotations").publish();
        encoderAbsolutePositionPublisher = instance.getDoubleTopic("TiltSubsystem/Encoder/AbsolutePosition").publish();
        encoderDistancePublisher = instance.getDoubleTopic("TiltSubsystem/Encoder/Distance").publish();
        encoderDistancePerRotationPublisher = instance.getDoubleTopic("TiltSubsystem/Encoder/DistancePerRotations").publish();
        encoderFrequencyPublisher = instance.getDoubleTopic("TiltSubsystem/Encoder/Frequency").publish();
    }

    public void setTiltMotor(double desiredPower)
    {
        tiltMotor.set(ControlMode.PercentOutput, desiredPower);
    }

    /**
     * Method that sets the desired position of the tilt subsystem.
     * 
     * @param desiredPosition set desired position in radians
     */
    public void setTiltPosition(double desiredPosition)
    {
        tiltMotor.set(ControlMode.PercentOutput, 
            tiltPIDController.calculate(encoder.getAbsolutePosition(), desiredPosition));
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
