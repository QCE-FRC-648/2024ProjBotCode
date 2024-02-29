package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TiltConstants;

public class TiltSubsystem extends SubsystemBase
{
    private VictorSPX tiltMotor = new VictorSPX(TiltConstants.kTiltCANId);

    private DutyCycleEncoder tiltEncoder = new DutyCycleEncoder(TiltConstants.kTiltDutyCycleEncoderDIOId);

    private PIDController tiltPIDController = new PIDController(
        TiltConstants.kTiltPositionP, 
        TiltConstants.kTiltPositionI, 
        TiltConstants.kTiltPositionD);

    public TiltSubsystem()
    {
        tiltMotor.setNeutralMode(NeutralMode.Brake);

        tiltEncoder.setDistancePerRotation(TiltConstants.kTiltEncoderPositionFactor);
        tiltEncoder.setDutyCycleRange(0, 2 * Math.PI);

        tiltPIDController.setTolerance(0);
    }

    /**
     * Method that sets the desired position of the tilt subsystem.
     * 
     * @param desiredPosition set desired position in radians
     */
    public void setTiltPosition(double desiredPosition)
    {
        tiltMotor.set(ControlMode.PercentOutput, 
            tiltPIDController.calculate(tiltEncoder.getAbsolutePosition(), desiredPosition));
    }

    @Override
    public void periodic()
    {

    }
}
