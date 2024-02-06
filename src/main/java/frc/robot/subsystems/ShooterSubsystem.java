package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.shuffleboard.SuppliedValueWidget;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.ShooterConstants;

/*
 * Class for the telescoping arm on shooter, shooter motors, and tilt
 */
public class ShooterSubsystem extends SubsystemBase
{
    private DigitalInput proximitySensor = new DigitalInput(ShooterConstants.kShooterProximitySensorDIOId);
    private VictorSPX flyWheelMotor1 = new VictorSPX(ShooterConstants.kFlywheel2CANId);

    private String tabName = ShooterConstants.kShuffleboardTabName;
    private ShuffleboardTab tab = Shuffleboard.getTab(tabName);

    private String shuffleFlyWheelSpeed = "FlyWheel Speed";
    private String proximitySwitchGet = "Proximity Switch";
    private String shuffleFlyWheelVolt = "FlyWheel volt";

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
