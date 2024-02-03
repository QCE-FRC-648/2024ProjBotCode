// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants 
{
  public static class SwerveModuleConstants
  {
    //may need to change to 12T or 14T
    public static final double kDrivingMotorPinionTeeth = 14;

    //turn motor is inverted 
    public static final boolean kTurningEncoderInverted = true;

    public static final double kWheelDiameterMeters = Units.inchesToMeters(3);
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;

    //RPM of the NEO brushless motors
    public static final double kFreeSpeedRPM = 5676;
    public static final double kDrivingMotorFreeSpeedRPS = kFreeSpeedRPM / 60;
    //gear ratios of driving motor
    // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15 teeth on the bevel pinion
    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRPS * kWheelCircumferenceMeters) / kDrivingMotorReduction;

    //conversion factors that may be needed
    public static final double kDrivingEncoderPositionFactor = (kWheelDiameterMeters * Math.PI) / kDrivingMotorReduction; //meters
    public static final double kDrivingEncoderVelocityFactor = kDrivingEncoderPositionFactor / 60.0; //meters per second

    public static final double kTurningEncoderPositionFactor = (2 * Math.PI); //radians
    public static final double kTurningEncoderVelocityFactor = kTurningEncoderPositionFactor / 60.0; //radians per second

    //constants for the PIDs
    public static final double kDrivingP = 0;
    public static final double kDrivingI = 0;
    public static final double kDrivingD = 0;
    public static final double kDrivingMinOutput = -1;
    public static final double kDrivingMaxOutput = 1;

    public static final double kTurningP = 0;
    public static final double kTurningI = 0;
    public static final double kTurningD = 0;
    public static final double kTurningMinOutput = -1;
    public static final double kTurningMaxOutput = 1;

    public static final IdleMode kDrivingMotorIdleMode = IdleMode.kBrake;
    public static final IdleMode kTurningMotorIdleMode = IdleMode.kBrake;

    public static final int kDrivingMotorCurrentLimit = 50; // amps
    public static final int kTurningMotorCurrentLimit = 20; // amps

  }

  public static class ConveyorConstants
  {
    //motor CAN IDs on elevator
    public static final int conveyorCAN = 20;
    public static final MotorType motorType = MotorType.kBrushless;
  }
  
  public static final class IOConstants
  {
    //controller constants
    public static final int driverJoystickPort = 0;
    public static final double driverControllerDeadzones = 0.06;
  }

}
