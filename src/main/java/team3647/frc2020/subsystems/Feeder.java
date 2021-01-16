package team3647.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import team3647.lib.drivers.VictorSPXFactory;

public class Feeder implements PeriodicSubsystem {
    private final VictorSPX frontRoller;
    private final VictorSPX organizingRoller;
    private final VictorSPX tunnelRoller;
    private final DigitalInput ballDetection;

    private boolean ballDetectionStatus;

    public Feeder(VictorSPXFactory.Configuration victorConfigs, int ballDetectorPin) {
        //since all motors spinning same direction, I can use same config?
        frontRoller = VictorSPXFactory.createVictor(victorConfigs);
        organizingRoller = VictorSPXFactory.createVictor(victorConfigs);
        tunnelRoller = VictorSPXFactory.createVictor(victorConfigs);
        ballDetection = new DigitalInput(ballDetectorPin);
    }

    

    

    

    public boolean getBallDetection() {
        return ballDetectionStatus;
    }

    @Override
    public void readPeriodicInputs() {
        // TODO Auto-generated method stub
        ballDetectionStatus = ballDetection.get();
        PeriodicSubsystem.super.readPeriodicInputs();
    }

    @Override
    public void writePeriodicOutputs() {
        // TODO Auto-generated method stub
        PeriodicSubsystem.super.writePeriodicOutputs();
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }
}