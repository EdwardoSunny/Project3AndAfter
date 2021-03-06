package team3647.frc2020.robot;

/*----------------------------------------------------------------------------*/
import com.revrobotics.CANSparkMax.IdleMode;

import team3647.lib.drivers.ClosedLoopFactory;
import team3647.lib.drivers.SparkMaxFactory;

/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import team3647.lib.drivers.TalonSRXFactory;
import team3647.lib.drivers.VictorSPXFactory;
import team3647.lib.drivers.ClosedLoopFactory.ClosedLoopConfig;

public final class Constants {

    //PID
    public static final double kP = 0.5;
    public static final double kI = 0.5;
    public static final double kD = 0.1;

    public static final int leftMasterPin = 1;
    public static final int leftSlavePin = 2;
    public static final int rightMasterPin = 3;
    public static final int rightSlavePin = 4;
    public static final int stallCurrent = 35;
    public static final int maxCurrent = 60;

    public static final int ElevatorGearboxSRXPin = 8;
    public static final int ElevatorGearboxSPX1Pin = 10;
    public static final int ElevatorGearboxSPX2Pin = 11; 

    public static final int hoodPWMPortChannel = 2;
    public static final int canifierID = 0;

    public static final int driveContinuousCurrent = 35;

    public static int kElevatorContinuousCurrent = 25;
    public static final int elevatorBeamBreakPin = 8;
    
    public static final int kTimeoutMs = 10;


    //drivetrain configs
    public static final double driveWheelDiameter = 0.152;


    public static final double kMaxSpeedMeterPerSecond = 2;
    public static final double kMaxAccelerationMeterPerSecondSquared = 2;

    public static final double maxVoltage = 11.0;
    public static final double gearboxReduction = 9.0 / 42.0 * 24.0 / 50.0;

    public static final double neoRotationsToMeter =
            gearboxReduction * driveWheelDiameter * Math.PI;

    public static final SparkMaxFactory.Configuration leftMasterConfig =
        new SparkMaxFactory.Configuration(leftMasterPin, false)
            .currentLimiting(true, maxCurrent, stallCurrent).idleMode(IdleMode.kBrake)
            .voltageCompensation(true, 12.0);

    public static final SparkMaxFactory.Configuration rightMasterConfig =
        new SparkMaxFactory.Configuration(rightMasterPin, true)
            .currentLimiting(true, maxCurrent, stallCurrent).idleMode(IdleMode.kBrake)
            .voltageCompensation(true, 12.0);

    public static final SparkMaxFactory.Configuration leftSlaveConfig =
        SparkMaxFactory.Configuration.mirrorWithCANID(leftMasterConfig, leftSlavePin);

    public static final SparkMaxFactory.Configuration rightSlaveConfig =
        SparkMaxFactory.Configuration.mirrorWithCANID(rightMasterConfig, rightSlavePin);

    
    public static final ClosedLoopConfig leftMasterPIDConfig = new ClosedLoopConfig()
        .encoderVelocityToRPM(gearboxReduction).encoderTicksToUnits(neoRotationsToMeter)
        .maxVelocity(kMaxSpeedMeterPerSecond).configPID(kP, kI, kD);
    public static final ClosedLoopConfig rightMasterPIDConfig = new ClosedLoopConfig()
        .encoderVelocityToRPM(gearboxReduction).encoderTicksToUnits(neoRotationsToMeter)
        .maxVelocity(kMaxSpeedMeterPerSecond).configPID(kP, kI, kD);

    //Hot dog indexer configs
    public static final int leftVerticalRollersPin = 22;
    public static final int rightVerticalRollersPin = 21;
    public static final int tunnelPin = 23;
    public static final int horizontalRollersPin = 24;
    public static final int bannerSensorPin = 1;

    public static final int PP_VerticalPDPSlot = 10;
    public static final int tunnelPDPSlot = 8;

    public static boolean rightVerticalRollersInverted = false;
    public static boolean leftVerticalRollersInverted = true;
    public static boolean tunnelInverted = false;
    public static boolean horizontalRollersInverted = false;
    public static VictorSPXFactory.Configuration leftRollersConfig =
                new VictorSPXFactory.Configuration(leftVerticalRollersPin)
                        .setInverted(leftVerticalRollersInverted).configOpenLoopRampRate(.3)
                        .setPDPSlot(10);
    public static TalonSRXFactory.Configuration rightRollersConfig =
            new TalonSRXFactory.Configuration(rightVerticalRollersPin,
                    rightVerticalRollersInverted);
    public static VictorSPXFactory.Configuration tunnelConfig =
            new VictorSPXFactory.Configuration(tunnelPin).setInverted(tunnelInverted)
                    .setPDPSlot(8);
    public static VictorSPXFactory.Configuration horizontalRollersConfig =
            new VictorSPXFactory.Configuration(horizontalRollersPin)
                    .setInverted(horizontalRollersInverted).configOpenLoopRampRate(.3);

    //intake configs
    public static final int innerSolenoidPin = 3;
    public static final int outerSolenoidPin = 4;
    public static final int intakeMotorPin = 8;
    public static TalonSRXFactory.Configuration intakeMotorConfig = 
            new TalonSRXFactory.Configuration(intakeMotorPin, true).configOpenLoopRampRate(0.3);
    
    public static final int solenoidPin = 6;

}

