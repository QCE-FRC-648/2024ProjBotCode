package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.commands.PathfindingCommand;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.FlyWheelSubsystem;
import frc.robot.subsystems.TelescopeSubsystem;
import frc.robot.subsystems.TiltSubsystem;
import frc.utils.JoystickUtils;
import frc.robot.commands.FlyWheelCommands.FlywheelHoldCommand;
import frc.robot.commands.ClimberCommands.ManualClimbCommand;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.IntakeCommands.FeedShooterCommand;
import frc.robot.commands.IntakeCommands.RunIntakeCommand;
import frc.robot.commands.OperatorCommands.ShootNoteCommand;
import frc.robot.commands.TelescopeCommands.ExtendTelescope;

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
  private final TelescopeSubsystem telescope = new TelescopeSubsystem();
  private final TiltSubsystem tilt = new TiltSubsystem();

  private final CommandXboxController driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
  private final static CommandXboxController operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() 
  {
    // Configure the trigger bindings
    configureBindings();

    //Pathplanner commands
    NamedCommands.registerCommand("IntakeNote", new RunIntakeCommand(conveyor));
    NamedCommands.registerCommand("ShootNote", new ShootNoteCommand(conveyor, flyWheel));
    
    driveTrain.setDefaultCommand(new RunCommand(
      //left joystick controls translation
      //right joystick controls rotation of the robot
      () -> driveTrain.drive(
        -JoystickUtils.applySensitivity(driverController.getLeftY(), OIConstants.kDriverDeadband, OIConstants.kDriverSensativity), 
        -JoystickUtils.applySensitivity(driverController.getLeftX(), OIConstants.kDriverDeadband, OIConstants.kDriverSensativity), 
        -JoystickUtils.applySensitivity(driverController.getRightX(), OIConstants.kDriverDeadband, OIConstants.kDriverSensativity), 
        true), 
      driveTrain));
    
    telescope.setDefaultCommand(new RunCommand(
      () -> telescope.setTelescopeMotor(-MathUtil.applyDeadband(operatorController.getRightY(), OIConstants.kDriverDeadband)*0.5), 
      telescope));
    
    tilt.setDefaultCommand(new RunCommand(
      ()-> tilt.setTiltMotor(-MathUtil.applyDeadband(operatorController.getLeftY() *0.5, OIConstants.kDriverDeadband)), 
      tilt));
  }

  private void configureBindings() 
  {
    operatorController.x().toggleOnTrue(new RunIntakeCommand(conveyor)); //intake

    operatorController.y().toggleOnTrue(new ShootNoteCommand(conveyor, flyWheel));

    operatorController.a().toggleOnTrue(Commands.deadline(new FlywheelHoldCommand(flyWheel), new FeedShooterCommand(conveyor)));
    
    operatorController.b().toggleOnTrue(new ExtendTelescope(telescope));

    operatorController.leftTrigger().whileTrue(new ManualClimbCommand(
      climber, 
      () -> -operatorController.getLeftTriggerAxis()));

    operatorController.rightTrigger().whileTrue(new ManualClimbCommand(
      climber, 
      () -> operatorController.getRightTriggerAxis()));
  }
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() 
  {
    return new RunCommand(() -> driveTrain.testSwerve(1, 0), driveTrain);
  }
  
}
