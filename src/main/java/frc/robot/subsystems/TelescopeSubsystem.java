package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TelescopeConstants;

public class TelescopeSubsystem extends SubsystemBase
{
    private VictorSPX telescopeMotor = new VictorSPX(TelescopeConstants.kTelescopeCANId);

    private DutyCycleEncoder telescopeEncoder = new DutyCycleEncoder(TelescopeConstants.kTelescopeDutyCycleEncoderDIOId);

    private PIDController telescopePIDController = new PIDController(
        TelescopeConstants.kTelescopePositionP,
        TelescopeConstants.kTelescopePositionI,
        TelescopeConstants.kTelescopePositionD);

    public TelescopeSubsystem()
    {
        telescopeMotor.setNeutralMode(NeutralMode.Brake);

        telescopeEncoder.setDistancePerRotation(TelescopeConstants.kTelescopeEncoderPositionFactor);
    }

    public void setTelescopeDistance(double desiredDistance)
    {
        telescopeMotor.set(ControlMode.PercentOutput,
            telescopePIDController.calculate(telescopeEncoder.getDistance(), desiredDistance));
    }
}
