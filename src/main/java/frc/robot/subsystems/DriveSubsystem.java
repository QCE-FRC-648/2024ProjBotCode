package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class DriveSubsystem extends SubsystemBase
{
    
    private final SwerveModule frontLeft = new SwerveModule(
        DrivetrainConstants.kFrontLeftDrivingCANId, 
        DrivetrainConstants.kFrontLeftTurningCANId, 
        DrivetrainConstants.kFrontLeftChassisAngularOffset,
        "front left");

    private final SwerveModule frontRight = new SwerveModule(
        DrivetrainConstants.kFrontRightDrivingCANId, 
        DrivetrainConstants.kFrontRightTurningCANId, 
        DrivetrainConstants.kFrontRightChassisAngularOffset,
        "front right");

    private final SwerveModule backLeft = new SwerveModule(
        DrivetrainConstants.kBackLeftDrivingCANId, 
        DrivetrainConstants.kBackLeftTurningCANId, 
        DrivetrainConstants.kBackLeftChassisAngularOffset,
        "back left");

    private final SwerveModule backRight = new SwerveModule(
        DrivetrainConstants.kBackRightDrivingCANId, 
        DrivetrainConstants.kBackRightTurningCANId, 
        DrivetrainConstants.kBackRightChassisAngularOffset,
        "back right");

    private AHRS imu = new AHRS(SPI.Port.kMXP);

    private SwerveDriveOdometry odometry = new SwerveDriveOdometry(
        DrivetrainConstants.kDriveKinematics, 
        Rotation2d.fromDegrees(imu.getAngle()), 
        new SwerveModulePosition[]{
            frontLeft.getPosition(),
            frontRight.getPosition(),
            backLeft.getPosition(),
            backRight.getPosition()
        });

    private final StructArrayPublisher<SwerveModuleState> measuredSwerveStatePublisher;
    private final StructArrayPublisher<SwerveModuleState> setpointSwerveStatePublisher;

    public DriveSubsystem() 
    { 
        measuredSwerveStatePublisher = NetworkTableInstance.getDefault()
            .getStructArrayTopic("/SwerveModuleState/Measured", SwerveModuleState.struct).publish();
        
        setpointSwerveStatePublisher = NetworkTableInstance.getDefault()
            .getStructArrayTopic("/SwerveModuleState/Setpoint", SwerveModuleState.struct).publish();
    }

    /**
     * Method to drive drivetrain with joysticks
     * 
     * @param xSpeed speed of robot in x direction (forward)
     * @param ySpeed speed of robot in y direction (sideways)
     * @param rotSpeed angular rate of the robot 
     * @param fieldRelative set if x and y speeds are relative to the field
     */
    public void drive(double xSpeed, double ySpeed, double rotSpeed, boolean fieldRelative) 
    { 
        xSpeed *= DrivetrainConstants.kMaxSpeedMetersPerSecond;
        ySpeed *= DrivetrainConstants.kMaxSpeedMetersPerSecond;
        rotSpeed *= DrivetrainConstants.kMaxAngularSpeed;

        var swerveModuleStates = DrivetrainConstants.kDriveKinematics.toSwerveModuleStates(
            fieldRelative ? //if fieldRelative
                ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotSpeed, Rotation2d.fromDegrees(-imu.getAngle()))
                :new ChassisSpeeds(xSpeed, ySpeed, rotSpeed));


        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, DrivetrainConstants.kMaxSpeedMetersPerSecond);

        frontLeft.setDesiredState(swerveModuleStates[0]);
        frontRight.setDesiredState(swerveModuleStates[1]);
        backLeft.setDesiredState(swerveModuleStates[2]);
        backRight.setDesiredState(swerveModuleStates[3]);
    }

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

    public Pose2d getPose()
    {
        return odometry.getPoseMeters();
    }

    public void resetPose(Pose2d pose)
    {
        odometry.resetPosition(Rotation2d.fromDegrees(imu.getAngle()), 
            new SwerveModulePosition[]{
                    frontLeft.getPosition(), frontRight.getPosition(),
                    backLeft.getPosition(), backRight.getPosition()
                }, 
            pose);
    }

    @Override
    public void periodic()
    {
        //update odometry
        odometry.update(Rotation2d.fromDegrees(imu.getAngle()), 
            new SwerveModulePosition[]{
                frontLeft.getPosition(), frontRight.getPosition(),
                backLeft.getPosition(), backRight.getPosition()
            });


        /*update current values of angle and velocity
        frontLeft.updateCurrentValues();
        frontRight.updateCurrentValues();
        backLeft.updateCurrentValues();
        backRight.updateCurrentValues();*/

        //publish to networktable for advantagescope
        measuredSwerveStatePublisher.set(new SwerveModuleState[]{
            frontLeft.getModuleState(),
            frontRight.getModuleState(),
            backLeft.getModuleState(),
            backRight.getModuleState()
        });

        setpointSwerveStatePublisher.set(new SwerveModuleState[]{
            frontLeft.getDesiredState(),
            frontRight.getDesiredState(),
            backLeft.getDesiredState(),
            backRight.getDesiredState()
        });

        //smart dashboard
        SmartDashboard.putNumber("gyro angle", imu.getAngle());

        //front left module
        SmartDashboard.putNumber("Desired angle front left", 
            frontLeft.getDesiredState().angle.getRadians());

        SmartDashboard.putNumber("Desired speed front left", frontLeft.getDesiredState().speedMetersPerSecond);

        SmartDashboard.putNumber("Current angle front left", 
            frontLeft.getModuleState().angle.getRadians());

        SmartDashboard.putNumber("Current velocity front left", frontLeft.getModuleState().speedMetersPerSecond);


        //front right module
        SmartDashboard.putNumber("Desired angle front right", 
            frontRight.getDesiredState().angle.getRadians());

        SmartDashboard.putNumber("Current angle front right", 
            frontRight.getModuleState().angle.getRadians());

        SmartDashboard.putNumber("Current velocity front right", frontRight.getModuleState().speedMetersPerSecond);

        //back left module
        SmartDashboard.putNumber("Desired angle back left", 
            backLeft.getDesiredState().angle.getRadians());

        SmartDashboard.putNumber("Current angle back left", 
            backLeft.getModuleState().angle.getRadians());

        SmartDashboard.putNumber("Current velocity back left", backLeft.getModuleState().speedMetersPerSecond);

        //back right module
        SmartDashboard.putNumber("Desired angle back right", 
            backRight.getDesiredState().angle.getRadians());

        SmartDashboard.putNumber("Current angle back right", 
            backRight.getModuleState().angle.getRadians());

        SmartDashboard.putNumber("Current velocity back right", backRight.getModuleState().speedMetersPerSecond);
    }
}
