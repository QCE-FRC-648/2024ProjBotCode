package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.Commands.FlywheelHoldCommand;
import frc.robot.Commands.IntakeConveyorCommand;
import frc.robot.subsystems.Conveyor;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.ShooterCommands.FlywheelHoldCommand;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer 
{
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem driveTrain = new DriveSubsystem();
  private final ShooterSubsystem shooter = new ShooterSubsystem();

  private final Conveyor conveyor = new Conveyor();

  private final CommandXboxController driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  private final CommandXboxController operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() 
  {
    // Configure the trigger bindings
    configureBindings();

    
    driveTrain.setDefaultCommand(new RunCommand(
      //left joystick controls translation
      //right joystick controls rotation of the robot
      () -> driveTrain.drive(-MathUtil.applyDeadband(driverController.getLeftY(), OIConstants.kDriverDeadband), 
      -MathUtil.applyDeadband(driverController.getLeftX(), OIConstants.kDriverDeadband), 
      -MathUtil.applyDeadband(driverController.getRightX(), OIConstants.kDriverDeadband)), 
      driveTrain));
  }

  private void configureBindings() 
  {
    driverController.x().onTrue(new FlywheelHoldCommand(shooter));
    operatorController.y().toggleOnTrue(new IntakeConveyorCommand(conveyor));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() 
  {
    return new InstantCommand();
  }
}
