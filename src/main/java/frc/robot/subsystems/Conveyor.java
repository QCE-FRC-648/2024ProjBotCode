package frc.robot.subsystems;
import frc.robot.Constants.ConveyorConstants;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DigitalInput;


public class Conveyor {

    public CANSparkMax conveyorMotor;
    public XboxController operator;
    public double leftY;
    public DigitalInput beam;

    public Conveyor() {
        conveyorMotor = new CANSparkMax(ConveyorConstants.conveyorCAN, ConveyorConstants.motorType);
        conveyorMotor.set(0);

        operator = new XboxController(1);

        leftY = operator.getLeftY();

        beam = new DigitalInput(0);
    }

    //operator's left joystick controlls the conveyor 
    public void runConveyorMotor() {

        //go up conveyor
        if(leftY > 0.3) {
            conveyorMotor.set(leftY);
        }

        //go down conveyor
        else if(leftY < -0.3) {
            conveyorMotor.set(leftY);
        }

        //stop conveyor
        else {
            conveyorMotor.set(0);
        }
    }

    //runs the conveyor once the beam is broken
    public void autoConveyor() {

        //runs the motor if the beam is broken
       if(beam.get() == false) {
        conveyorMotor.set(0.3);
       } 

       //stops the motor if the beam is not broken
       else {
        conveyorMotor.set(0);
       }
    }
    
}
