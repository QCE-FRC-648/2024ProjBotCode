package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
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

    //fly wheel encoders need encoding type k1X to reduce noise
    private Encoder flyWheelEncoder1 = new Encoder(ShooterConstants.kFlyWheel1RelativeEncoderDIOChannelA, 
        ShooterConstants.kFlyWheel1RelativeEncoderDIOChannelB,
        false,
        EncodingType.k1X);
    
    private Encoder flyWheelEncoder2 = new Encoder(ShooterConstants.kFlyWheel2RelativeEncoderDIOChannelA,
        ShooterConstants.kFlyWheel2RelativeEncoderDIOChannelB,
        false,
        EncodingType.k1X);

    private DigitalInput proximitySensor = new DigitalInput(ShooterConstants.kShooterProximitySensorDIOId);

    private PIDController flyWheelPIDController = new PIDController(
        ShooterConstants.kFlyWheelVelocityP, 
        ShooterConstants.kFlyWheelVelocityK, 
        ShooterConstants.kFlyWheelVelocityD);

    private PIDController tiltPIDController = new PIDController(
        ShooterConstants.kTiltPositionP, 
        ShooterConstants.kTiltPositionK, 
        ShooterConstants.kTiltPositionD);
    
    private PIDController telescopePIDController = new PIDController(0,0,0);

    private String tabName = ShooterConstants.kShuffleboardTabName;
    private ShuffleboardTab tab = Shuffleboard.getTab(tabName);

    private String shuffleFlyWheelSpeed = "FlyWheel Speed";
    private String proximitySwitchGet = "Proximity Switch";
    private String shuffleFlyWheelVolt = "FlyWheel voltage";

    private GenericEntry entryFlyWheel = tab.add(shuffleFlyWheelSpeed, 0).getEntry();
    private GenericEntry entryFlyWheelVolt = tab.add(shuffleFlyWheelVolt, 0).getEntry();
    private GenericEntry entryProximity = tab.add(proximitySwitchGet, true).getEntry();

    public ShooterSubsystem() 
    { 
        //neutral mode for motors
        flyWheelMotor1.setNeutralMode(NeutralMode.Brake);
        flyWheelMotor2.setNeutralMode(NeutralMode.Brake);
        telescopeMotor.setNeutralMode(NeutralMode.Brake);
        tiltMotor.setNeutralMode(NeutralMode.Brake);

        //set sample rate to 10 to reduce noise in fly wheel encoders
        flyWheelEncoder1.setSamplesToAverage(10);
        flyWheelEncoder2.setSamplesToAverage(10);

        //setting conversions 
        flyWheelEncoder1.setDistancePerPulse(ShooterConstants.kFlyWheelEncoderVelocityFactor);
        flyWheelEncoder2.setDistancePerPulse(ShooterConstants.kFlyWheelEncoderVelocityFactor);

        tiltEncoder.setDistancePerRotation(ShooterConstants.kTiltEncoderPositionFactor);
        tiltEncoder.setDutyCycleRange(0, 2 * Math.PI);

        telescopeEncoder.setDistancePerRotation(ShooterConstants.kTelescopeEncoderPositionFactor);

        //PID controllers setup
        tiltPIDController.setTolerance(0);
    }

    /**
     * Method to set fly wheel motor power
     *  
     * @param desiredSpeed desired speed of the motors
     */
    public void setFlyWheelMotors(double desiredPower)
    {
        flyWheelMotor1.set(ControlMode.PercentOutput, desiredPower);
        flyWheelMotor2.set(ControlMode.PercentOutput, desiredPower);
    }

    /**
     * Method to set fly wheel motor velocity in meters per second
     * 
     * @param desiredVelocity desired velocity of the motors in meters per second
     */
    public void setFlyWheelVelocity(double desiredVelocity)
    {
        double pidValue = flyWheelPIDController.calculate(flyWheelEncoder1.getRate(), desiredVelocity);

        flyWheelMotor1.set(ControlMode.PercentOutput, pidValue);
        flyWheelMotor1.set(ControlMode.PercentOutput, pidValue);
    }

    public void setFlyWheelNeutralMode(NeutralMode mode)
    {
        flyWheelMotor1.setNeutralMode(mode);
        flyWheelMotor2.setNeutralMode(mode);
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

    public void setTelescopeDistance(double desiredDistance)
    {
        telescopeMotor.set(ControlMode.PercentOutput,
            telescopePIDController.calculate(telescopeEncoder.getDistance(), desiredDistance));
    }

    public DigitalInput getProximitySensor()
    {
        return proximitySensor;
    }

    public PIDController getFlyWheelPID()
    {
        return flyWheelPIDController;
    }

    @Override
    public void periodic() 
    { 
        entryFlyWheel.setDouble(flyWheelMotor1.getMotorOutputPercent());
        entryFlyWheelVolt.setDouble(flyWheelMotor1.getTemperature());
        entryProximity.setBoolean(proximitySensor.get());
    }
}
