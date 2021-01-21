package team3647.frc2020.robot;
/*----------------------------------------------------------------------------*/

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import team3647.lib.drivers.TalonSRXFactory;
import team3647.lib.drivers.VictorSPXFactory;

public final class Constants {

    public static final int leftMasterPin = 1;
    public static final int leftSlavePin = 2;
    public static final int rightMasterPin = 3;
    public static final int rightSlavePin = 4;

    public static final int ElevatorGearboxSRXPin = 8;
    public static final int ElevatorGearboxSPX1Pin = 10;
    public static final int ElevatorGearboxSPX2Pin = 11; 

    public static final int hoodPWMPortChannel = 2;
    public static final int canifierID = 0;

    public static final int stallCurrent = 35;
    public static final int maxCurrent = 60;
    public static final int driveContinuousCurrent = 35;

    public static int kElevatorContinuousCurrent = 25;
    public static final int elevatorBeamBreakPin = 8;
    
    public static final int kTimeoutMs = 10;


    //drivetrain configs

    public static final TalonSRXFactory.Configuration leftMasterConfig =
    new TalonSRXFactory.Configuration(leftMasterPin, true)
                .currentLimiting(true, maxCurrent, stallCurrent, driveContinuousCurrent)
                .voltageCompensation(true, 12.0);

    public static final TalonSRXFactory.Configuration rightMasterConfig =
        new TalonSRXFactory.Configuration(rightMasterPin, true)
                .currentLimiting(true, maxCurrent, stallCurrent, driveContinuousCurrent)
                .voltageCompensation(true, 12.0);

    public static final VictorSPXFactory.Configuration leftSlaveConfig =
        new VictorSPXFactory.Configuration(leftSlavePin).configMaxOutput(maxCurrent).configMaxReverseOutput(stallCurrent);

    public static final VictorSPXFactory.Configuration rightSlaveConfig =
    new VictorSPXFactory.Configuration(rightSlavePin).configMaxOutput(maxCurrent).setInverted(true).configMaxReverseOutput(stallCurrent);

    //indexer constants

    public static final int frontRollerPin = 24;
    public static final int LorganizingRollerPin = 22;
    public static final int RorganizingRollerPin = 21;
    public static final int tunnelRollerPin = 23;
    public static final int ballDetectionPin = 1;
    public static final boolean rightRollerInvert = true;


    public static final VictorSPXFactory.Configuration frontRollerConfig = 
        new VictorSPXFactory.Configuration(frontRollerPin).configOpenLoopRampRate(0.3).setPDPSlot(10);
    public static final VictorSPXFactory.Configuration tunnelRollerConfig = 
        new VictorSPXFactory.Configuration(tunnelRollerPin).configOpenRampRate(0.3);

    public static final VictorSPXFactory.Configuration LorganizingRollerConfig = 
        new VictorSPXFactory.Configuration(frontRollerPin).configOpenLoopRampRate(0.3).setPDPSlot(10);
    public static final VictorSPXFactory.Configuration RorganizingRollerConfig = 
        new VictorSPXFactory.Configuration(frontRollerPin).setInverted(rightRollerInvert);
        

}

