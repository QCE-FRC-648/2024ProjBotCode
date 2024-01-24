package frc.robot.subsystems;
import frc.robot.Constants.ElevatorConstants;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.XboxController;

public class Elevator {

    public CANSparkMax elevatorMotor;


    public Elevator() {
        elevatorMotor = new CANSparkMax(ElevatorConstants.elevatorCAN, ElevatorConstants.motorType);
        elevatorMotor.set(0);
    }


    @Override 
    public void periodic() 
    {
        
    }

}
