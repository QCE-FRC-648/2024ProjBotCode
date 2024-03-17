package frc.robot.subsystems.vision;

import org.opencv.aruco.EstimateParameters;
import org.photonvision.EstimatedRobotPose;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PoseEstimatorConstants;
import frc.robot.subsystems.DriveSubsystem;

public class PoseEstimator extends SubsystemBase
{
    private final DriveSubsystem driveTrain;
    private final SwerveDrivePoseEstimator poseEstimator;
    private final Vision camera;
    private final AprilTagFieldLayout aprilTagFieldLayout = AprilTagFields.k2024Crescendo.loadAprilTagLayoutField();
    private final Field2d field = new Field2d();
    private double prevPipelineTimestamp = 0;

    PoseEstimator(Vision _camera, DriveSubsystem _driveTrain)
    {
        driveTrain = _driveTrain;
        camera = _camera;

        poseEstimator = new SwerveDrivePoseEstimator
        (null, 
        driveTrain.getHeading(), 
        driveTrain.getModulePositions(), 
        driveTrain.getPose(),
        VecBuilder.fill(PoseEstimatorConstants.kVisionStdDevX, 
            PoseEstimatorConstants.kPositionStdDevY, 
            PoseEstimatorConstants.kPositionStdDevTheta),
        VecBuilder.fill(PoseEstimatorConstants.kVisionStdDevX,
        PoseEstimatorConstants.kPositionStdDevY,
        PoseEstimatorConstants.kPositionStdDevTheta));
    }

    @Override
    public void periodic()
    {
        poseEstimator.update(driveTrain.getHeading(), driveTrain.getModulePositions());

        try
        {
            EstimatedRobotPose estimatedRobotPose = camera.getEstimatedRobotPose(poseEstimator.getEstimatedPosition()).get();
            poseEstimator.addVisionMeasurement(estimatedRobotPose.estimatedPose.toPose2d(), estimatedRobotPose.timestampSeconds);

        }
        catch(Exception e){}
    }
}
