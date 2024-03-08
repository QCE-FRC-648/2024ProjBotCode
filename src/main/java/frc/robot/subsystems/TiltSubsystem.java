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

    private PIDController pidController = new PIDController(
        TiltConstants.kTiltPositionP, 
        TiltConstants.kTiltPositionI, 
        TiltConstants.kTiltPositionD);

    private final NetworkTableInstance instance = NetworkTableInstance.getDefault();
    private final DoublePublisher encoderRotationsPublisher;
    private final DoublePublisher encoderAbsolutePositionPublisher;
    private final DoublePublisher encoderDistancePublisher;
    private final DoublePublisher encoderDistancePerRotationPublisher;
    private final DoublePublisher encoderFrequencyPublisher;

    private final DoublePublisher pidErrorPublisher;
    private final DoublePublisher pidSetpointPublisher;

    public TiltSubsystem()
    {
        tiltMotor.setNeutralMode(NeutralMode.Brake);

        encoder.setDistancePerRotation(TiltConstants.kTiltEncoderPositionFactor);
        encoder.setDutyCycleRange(1, 1024);

        pidController.setTolerance(TiltConstants.kPIDTolerance);

        encoderRotationsPublisher = instance.getDoubleTopic("TiltSubsystem/Encoder/RelativeRotations").publish();
        encoderAbsolutePositionPublisher = instance.getDoubleTopic("TiltSubsystem/Encoder/AbsolutePosition").publish();
        encoderDistancePublisher = instance.getDoubleTopic("TiltSubsystem/Encoder/Distance").publish();
        encoderDistancePerRotationPublisher = instance.getDoubleTopic("TiltSubsystem/Encoder/DistancePerRotations").publish();
        encoderFrequencyPublisher = instance.getDoubleTopic("TiltSubsystem/Encoder/Frequency").publish();

        pidErrorPublisher = instance.getDoubleTopic("TiltSubsystem/PID/Error").publish();
        pidSetpointPublisher = instance.getDoubleTopic("TiltSubsystem/PID/Setpoint").publish();
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
            pidController.calculate(encoder.getAbsolutePosition(), desiredPosition));
    }

    public boolean getPidAtSetpoint()
    {
        return pidController.atSetpoint();
    }

    @Override
    public void periodic()
    {
        encoderRotationsPublisher.set(encoder.get());
        encoderAbsolutePositionPublisher.set(encoder.getAbsolutePosition());
        encoderDistancePublisher.set(encoder.getDistance());
        encoderDistancePerRotationPublisher.set(encoder.getDistancePerRotation());
        encoderFrequencyPublisher.set(encoder.getFrequency());

        pidErrorPublisher.set(pidController.getPositionError());
        pidSetpointPublisher.set(pidController.getSetpoint());
    }
}
