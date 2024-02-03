package frc.robot.subsystems;
import frc.robot.Constants.ConveyorConstants;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.XboxController;

public class Conveyor {

    public CANSparkMax conveyorMotor;
    public XboxController operator;
    public double leftY;

    public Conveyor() {
        conveyorMotor = new CANSparkMax(ConveyorConstants.conveyorCAN, ConveyorConstants.motorType);
        conveyorMotor.set(0);

        operator = new XboxController(1);
    }

    //the left joystick controlls the conveyor 
    public void runConveyorMotor() {

        //gets the value of the operator's left joystick
        leftY = operator.getLeftY();

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

    
}
