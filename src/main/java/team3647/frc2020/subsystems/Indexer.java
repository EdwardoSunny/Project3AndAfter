package team3647.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;

import team3647.lib.drivers.VictorSPXFactory;
//rolls in balls into the tunnel for shooting stuff and stuff
//I will figure this alternate indexer out later for fun ig
public class Indexer implements PeriodicSubsystem {
    private final VictorSPX frontRoller;
    private final VictorSPX organizingRollerR;
    private final VictorSPX organizingRollerL;
    private final VictorSPX tunnelRoller;
    private final DigitalInput ballDetection;

    public enum IndexerLevel {
        HIGH, LOW, STOP
    }

    private boolean ballDetectionStatus;

    public Indexer(VictorSPXFactory.Configuration frontRollerConfig, VictorSPXFactory.Configuration tunnelRollerConfig, VictorSPXFactory.Configuration leftOrganizerConfig, VictorSPXFactory.Configuration rightOrganizerConfig, int ballDetectorPin) {
        //since all motors spinning same direction, I can use same config?
        frontRoller = VictorSPXFactory.createVictor(frontRollerConfig);
        organizingRollerR = VictorSPXFactory.createVictor(rightOrganizerConfig);
        //left hand is inverted
        organizingRollerL = VictorSPXFactory.createVictor(leftOrganizerConfig);
        tunnelRoller = VictorSPXFactory.createVictor(tunnelRollerConfig);
        ballDetection = new DigitalInput(ballDetectorPin);
    }

    public void set(IndexerLevel level) {
        switch(level) {
            case HIGH:
                frontRoller.set(ControlMode.PercentOutput, 0.8);
                //inverted, roll balls together into tunnel with brushes
                organizingRollerR.set(ControlMode.PercentOutput, 0.6);
                organizingRollerL.set(ControlMode.PercentOutput, 0.6);
                tunnelRoller.set(ControlMode.PercentOutput, 0.5);
                break;
            case LOW:
                frontRoller.set(ControlMode.PercentOutput, 0.4);
                //inverted, roll balls together into tunnel with brushes
                organizingRollerR.set(ControlMode.PercentOutput, 0.3);
                organizingRollerL.set(ControlMode.PercentOutput, 0.3);
                tunnelRoller.set(ControlMode.PercentOutput, 0.25);
                break;
        }
    }

    public void manuallySet(double demand) {
        frontRoller.set(ControlMode.PercentOutput, Math.signum(demand));
        organizingRollerR.set(ControlMode.PercentOutput, Math.signum(demand));
        organizingRollerL.set(ControlMode.PercentOutput, Math.signum(demand));
        tunnelRoller.set(ControlMode.PercentOutput, Math.signum(demand));
        
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