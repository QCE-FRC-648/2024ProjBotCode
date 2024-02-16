package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class ClimberSubsystem extends SubsystemBase
{
    public VictorSPX climber1 = new VictorSPX(ClimberConstants.kClimber1CANId);
    public VictorSPX climber2 = new VictorSPX(ClimberConstants.kClimber2CANId);

    public ClimberSubsystem()
    {
        climber1.setNeutralMode(NeutralMode.Brake);
        climber2.setNeutralMode(NeutralMode.Brake);
    }
}
