package frc.robot.subsystems;
import frc.robot.Constants.ConveyorConstants;
import frc.robot.Constants.ConveyorConstants;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.XboxController;

public class Conveyor {

    public CANSparkMax conveyorMotor;


    public Conveyor() {
        conveyorMotor = new CANSparkMax(ConveyorConstants.conveyorCAN, ConveyorConstants.motorType);
        conveyorMotor.set(0);
    }

/* 
    @Override 
    public void periodic() 
    {
        
    }
*/
}
