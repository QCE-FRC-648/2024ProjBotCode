package frc.robot.subsystems;
import frc.robot.Constants.ConveyorConstants;
import frc.robot.Constants.ShooterConstants;
import com.ctre.phoenix.motorcontrol.ControlMode;  
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;


public class ConveyorSubsystem 
{

    public VictorSPX conveyorMotor1;
    public VictorSPX conveyorMotor2;
    public VictorSPX intakeMotor;
    private DigitalInput proximitySensor = new DigitalInput(ShooterConstants.kShooterProximitySensorDIOId);
    public DigitalInput beam;

    public ConveyorSubsystem() {
        conveyorMotor1 = new VictorSPX(ConveyorConstants.conveyorCANID19);
        conveyorMotor1.set(ControlMode.PercentOutput, 0);

        conveyorMotor2 = new VictorSPX(ConveyorConstants.conveyorCANID19);
        conveyorMotor2.set(ControlMode.PercentOutput, 0);

        intakeMotor = new VictorSPX(ConveyorConstants.intakeCAN);
        intakeMotor.set(ControlMode.PercentOutput, 0);

        beam = new DigitalInput(ConveyorConstants.beamPort);
    }

    //operator's left joystick controlls the conveyor 
    public void runconveyorMotor1(double leftY) {

        //go up conveyor
        if(leftY > 0.3) {
            conveyorMotor1.set(ControlMode.PercentOutput, leftY);
            conveyorMotor2.set(ControlMode.PercentOutput, leftY);
        }

        //go down conveyor
        else if(leftY < -0.3) {
            conveyorMotor1.set(ControlMode.PercentOutput, leftY);
            conveyorMotor2.set(ControlMode.PercentOutput, leftY);
        }

        //stop conveyor
        else {
            conveyorMotor1.set(ControlMode.PercentOutput, 0);
            conveyorMotor2.set(ControlMode.PercentOutput, 0);
        }
    }

    //runs intake when x is pressed
    public void runIntake(boolean buttonX) {
        if(buttonX) {
            intakeMotor.set(ControlMode.PercentOutput, 0.3);
        }
        else {
            intakeMotor.set(ControlMode.PercentOutput, 0);
        }
    }

    //runs the conveyor once the beam is broken
    public void autoConveyor() {

        //runs the motor if the beam is broken
       if(beam.get() == false) {
        conveyorMotor1.set(ControlMode.PercentOutput, 0.3);
        conveyorMotor2.set(ControlMode.PercentOutput, 0.3);
       } 

       //stops the motor if the beam is not broken
       else {
        conveyorMotor1.set(ControlMode.PercentOutput, 0);
        conveyorMotor2.set(ControlMode.PercentOutput, 0);
       }
    }

    public DigitalInput getProximitySensor()
    {
        return proximitySensor;
    }
    
}
