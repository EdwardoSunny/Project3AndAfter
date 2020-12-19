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

import team3647.lib.drivers.SparkMaxFactory;
import team3647.lib.drivers.TalonSRXFactory;
import team3647.lib.drivers.VictorSPXFactory;

public final class Constants {

    public static final int leftMasterPin = 1;
    public static final int leftSlavePin = 2;
    public static final int rightMasterPin = 3;
    public static final int rightSlavePin = 4;

    public static final int canifierID = 0;

    public static final int stallCurrent = 35;
    public static final int maxCurrent = 60;
    public static final int driveContinuousCurrent = 35;


    public static final TalonSRXFactory.Configuration leftMasterConfig =
    new TalonSRXFactory.Configuration(rightMasterPin, true)
                .currentLimiting(true, maxCurrent, stallCurrent, driveContinuousCurrent)
                .voltageCompensation(true, 12.0);

    public static final TalonSRXFactory.Configuration rightMasterConfig =
        new TalonSRXFactory.Configuration(rightMasterPin, true)
                .currentLimiting(true, maxCurrent, stallCurrent, driveContinuousCurrent)
                .voltageCompensation(true, 12.0);

    public static final VictorSPXFactory.Configuration leftSlaveConfig =
        new VictorSPXFactory.Configuration(leftSlavePin).configMaxOutput(maxCurrent).configMaxReverseOutput(stallCurrent);

    public static final VictorSPXFactory.Configuration rightSlaveConfig =
    new VictorSPXFactory.Configuration(leftSlavePin).configMaxOutput(maxCurrent).setInverted(true).configMaxReverseOutput(stallCurrent);

}