package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class ClimberSubsystem extends SubsystemBase
{
    private VictorSPX climber1 = new VictorSPX(ClimberConstants.kClimber1CANId);
    private VictorSPX climber2 = new VictorSPX(ClimberConstants.kClimber2CANId);

    public ClimberSubsystem()
    {
        climber1.setNeutralMode(NeutralMode.Brake);
        climber2.setNeutralMode(NeutralMode.Brake);
    }
}
