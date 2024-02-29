package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class ClimberSubsystem extends SubsystemBase
{
    public WPI_VictorSPX climber1 = new WPI_VictorSPX(ClimberConstants.kClimber1CANId);
    public WPI_VictorSPX climber2 = new WPI_VictorSPX(ClimberConstants.kClimber2CANId);
    public final PowerDistribution PDH;

    public ClimberSubsystem()
    {
        climber1.setNeutralMode(NeutralMode.Brake);
        climber2.setNeutralMode(NeutralMode.Brake);
        PDH = new PowerDistribution();
    }

    public double getCurrent(int num) 
    {
        return PDH.getCurrent(num);
    }

    @Override
    public void periodic()
    {
        SmartDashboard.getNumber("climber1(25) current", getCurrent(15));
        SmartDashboard.getNumber("climber2(26) current", getCurrent(14));
    }
}
