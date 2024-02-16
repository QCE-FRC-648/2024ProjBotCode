package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

/*
 * Class for the telescoping arm on shooter, shooter motors, and tilt
 */
public class ShooterSubsystem extends SubsystemBase
{
    private VictorSPX flyWheelMotor1 = new VictorSPX(ShooterConstants.kFlywheel1CANId);
    private VictorSPX flyWheelMotor2 = new VictorSPX(ShooterConstants.kFlywheel2CANId);
    private VictorSPX tiltMotor = new VictorSPX(ShooterConstants.kTiltCANId);
    private VictorSPX telescopeMotor = new VictorSPX(ShooterConstants.kTelescopeCANId);

    private DutyCycleEncoder tiltEncoder = new DutyCycleEncoder(ShooterConstants.kTiltDutyCycleEncoderDIOId);
    private DutyCycleEncoder telescopeEncoder = new DutyCycleEncoder(ShooterConstants.kTelescopeDutyCycleEncoderDIOId);
    private Encoder flyWheel1Encoder = new Encoder(ShooterConstants.kFlyWheel1RelativeEncoderDIOChannelA, 
        ShooterConstants.kFlyWheel1RelativeEncoderDIOChannelB);
    
    private Encoder flyWheel2Encoder = new Encoder(ShooterConstants.kFlyWheel2RelativeEncoderDIOChannelA,
        ShooterConstants.kFlyWheel2RelativeEncoderDIOChannelB);

    private DigitalInput proximitySensor = new DigitalInput(ShooterConstants.kShooterProximitySensorDIOId);

    private String tabName = ShooterConstants.kShuffleboardTabName;
    private ShuffleboardTab tab = Shuffleboard.getTab(tabName);

    private String shuffleFlyWheelSpeed = "FlyWheel Speed";
    private String proximitySwitchGet = "Proximity Switch";
    private String shuffleFlyWheelVolt = "FlyWheel voltage";

    private GenericEntry entryFlyWheel = tab.add(shuffleFlyWheelSpeed, 0).getEntry();
    private GenericEntry entryFlyWheelVolt = tab.add(shuffleFlyWheelVolt, 0).getEntry();
    private GenericEntry entryProximity = tab.add(proximitySwitchGet, true).getEntry();

    public ShooterSubsystem() { }

    /**
     * Method to set motors to a speed
     *  
     * @param desiredSpeed desired speed of the motors
     */
    public void setFlyWheelMotors(double desiredSpeed)
    {
        flyWheelMotor1.set(ControlMode.PercentOutput, desiredSpeed);
        flyWheelMotor2.set(ControlMode.PercentOutput, desiredSpeed);
    }

    public DigitalInput getProximitySensor()
    {
        return proximitySensor;
    }

    @Override
    public void periodic() 
    { 
        entryFlyWheel.setDouble(flyWheelMotor1.getMotorOutputPercent());
        entryFlyWheelVolt.setDouble(flyWheelMotor1.getTemperature());
        entryProximity.setBoolean(proximitySensor.get());
    }
}
