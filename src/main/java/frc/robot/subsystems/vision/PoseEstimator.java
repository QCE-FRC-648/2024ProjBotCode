package frc.robot.subsystems.vision;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PoseEstimatorConstants;
import frc.robot.subsystems.DriveSubsystem;

public class PoseEstimator extends SubsystemBase
{
    private final DriveSubsystem driveTrain;
    private final SwerveDrivePoseEstimator poseEstimator;
    private final Field2d field = new Field2d();
    private double prevPipelineTimestamp = 0;

    PoseEstimator(Vision camera, DriveSubsystem _driveTrain)
    {
        driveTrain = _driveTrain;

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
        
    }
}
