package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.TelescopeConstants;

public class TelescopeSubsystem extends SubsystemBase
{
    private VictorSPX telescopeMotor = new VictorSPX(TelescopeConstants.kTelescopeCANId);

    private DutyCycleEncoder telescopeEncoder = new DutyCycleEncoder(TelescopeConstants.kTelescopeDutyCycleEncoderDIOId);

}
