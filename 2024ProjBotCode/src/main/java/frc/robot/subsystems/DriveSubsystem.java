package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;
public class DriveSubsystem extends SubsystemBase
{
    private final SwerveModule frontLeft = new SwerveModule(
        DrivetrainConstants.kFrontLeftDrivingCANId, 
        DrivetrainConstants.kFrontLeftTurningCANId, 
        DrivetrainConstants.kFrontLeftChassisAngularOffset);

    private final SwerveModule frontRight = new SwerveModule(
        DrivetrainConstants.kFrontRightDrivingCANId, 
        DrivetrainConstants.kFrontRightTurningCANId, 
        DrivetrainConstants.kFrontRightChassisAngularOffset);

    private final SwerveModule backLeft = new SwerveModule(
        DrivetrainConstants.kBackLeftDrivingCANId, 
        DrivetrainConstants.kBackLeftTurningCANId, 
        DrivetrainConstants.kBackLeftChassisAngularOffset);

    private final SwerveModule backRight = new SwerveModule(
        DrivetrainConstants.kBackRightDrivingCANId, 
        DrivetrainConstants.kBackRightTurningCANId, 
        DrivetrainConstants.kBackRightChassisAngularOffset);

    public DriveSubsystem() { }

    @Override
    public void periodic()
    {
        //smart dashboard
        //front left module
        SmartDashboard.putNumber("Desired angle front left", 
            frontLeft.getDesiredState().angle.getRadians());

        SmartDashboard.putNumber("Current angle front left", 
            frontLeft.getModuleState().angle.getRadians());

        //front right module
        SmartDashboard.putNumber("Desired angle front right", 
            frontRight.getDesiredState().angle.getRadians());

        SmartDashboard.putNumber("Current angle front right", 
            frontRight.getModuleState().angle.getRadians());

        //back left module
        SmartDashboard.putNumber("Desired angle back left", 
            backLeft.getDesiredState().angle.getRadians());

        SmartDashboard.putNumber("Current angle back left", 
            backLeft.getModuleState().angle.getRadians());

        //back right module
        SmartDashboard.putNumber("Desired angle back right", 
            backRight.getDesiredState().angle.getRadians());

        SmartDashboard.putNumber("Current angle back right", 
            backRight.getModuleState().angle.getRadians());
    }

    /**
     * Method to drive drivetrain with joysticks
     * 
     * @param xSpeed speed of robot in x direction (forward)
     * @param ySpeed speed of robot in y direction (sideways)
     * @param rotSpeed angular rate of the robot 
     */
    public void drive(double xSpeed, double ySpeed, double rotSpeed) { }

    /**
     * A temperary method for testing the swerve modules. This will be used to tune PIDs
     * 
     * @param speed velocity of the wheels in meters per second
     * @param angle angle of the wheels in radians
     */
    public void testSwerve(double speed, double angle)
    {
        SwerveModuleState testState = new SwerveModuleState(speed, new Rotation2d(angle));

        frontLeft.setDesiredState(testState);
        frontRight.setDesiredState(testState);
        backLeft.setDesiredState(testState);
        backRight.setDesiredState(testState);
    }
}
