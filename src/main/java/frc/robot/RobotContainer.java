package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FlyWheelSubsystem;
import frc.robot.commands.ClimberCommands.ToggleClimbCommand;
import frc.robot.commands.FlyWheelCommands.FlyWheelShootCommand;
import frc.robot.commands.FlyWheelCommands.FlywheelHoldCommand;
import frc.robot.commands.ClimberCommands.ManualClimbCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.IntakeCommands.EjectNoteCommand;
import frc.robot.commands.IntakeCommands.FeedShooterCommand;
import frc.robot.commands.ClimberCommands.ManualClimbCommand;
import frc.robot.commands.IntakeCommands.RunIntakeCommand;
import frc.robot.commands.OperatorCommands.ShootNoteCommand;

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

  private final ConveyorSubsystem conveyor = new ConveyorSubsystem();
  private final FlyWheelSubsystem flyWheel = new FlyWheelSubsystem();
  private final ClimberSubsystem climber = new ClimberSubsystem();

  private final CommandXboxController driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  private final static CommandXboxController operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() 
  {
    // Configure the trigger bindings
    configureBindings();

    
    driveTrain.setDefaultCommand(new RunCommand(
      //left joystick controls translation
      //right joystick controls rotation of the robot
      () -> driveTrain.drive(
        -MathUtil.applyDeadband(driverController.getLeftY(), OIConstants.kDriverDeadband), 
        -MathUtil.applyDeadband(driverController.getLeftX(), OIConstants.kDriverDeadband), 
        -MathUtil.applyDeadband(driverController.getRightX(), OIConstants.kDriverDeadband), 
        true), 
      driveTrain));

    
    climber.setDefaultCommand(new ManualClimbCommand(
      climber, 
      () -> -MathUtil.applyDeadband(operatorController.getLeftY(), OIConstants.kDriverDeadband)));
  }

  private void configureBindings() 
  {
    operatorController.x().toggleOnTrue(new RunIntakeCommand(conveyor)); //intake

    operatorController.y().toggleOnTrue(Commands.parallel(new FeedShooterCommand(conveyor), new FlyWheelShootCommand(flyWheel)));
    
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
