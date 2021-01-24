package team3647.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import team3647.lib.IndexerSignal;
import team3647.lib.drivers.TalonSRXFactory;
import team3647.lib.drivers.VictorSPXFactory;

public class HotDogIndexer implements PeriodicSubsystem {
    private final TalonSRX rightVerticalRoller;
    private final VictorSPX leftVerticalRoller;
    private final VictorSPX horizontalRoller;
    private final VictorSPX tunnelBeltRoller;
    private final DigitalInput tunnelBallDetection;

    private boolean hasBallInTunnel;

    public HotDogIndexer(TalonSRXFactory.Configuration RVConfig, VictorSPXFactory.Configuration LVConfig, VictorSPXFactory.Configuration hRollerConfig, VictorSPXFactory.Configuration TBConfig, int ballDetectPin) {
        rightVerticalRoller = TalonSRXFactory.createTalon(RVConfig);
        leftVerticalRoller = VictorSPXFactory.createVictor(LVConfig);
        horizontalRoller = VictorSPXFactory.createVictor(hRollerConfig);
        tunnelBeltRoller = VictorSPXFactory.createVictor(TBConfig);
        tunnelBallDetection = new DigitalInput(ballDetectPin);
    }

    public void setSignal(IndexerSignal demand) {
        double LverticalRollerDemand = demand.getLeftVerticalOutput();
        double RverticalRollerDemand = demand.getRightVerticalOutput();
        double hRollerDemand = demand.getHorizontalRollersOutput();
        double tunnelBeltRollerDemand = demand.getTunnelOutput();

        leftVerticalRoller.set(ControlMode.PercentOutput, LverticalRollerDemand);
        rightVerticalRoller.set(ControlMode.PercentOutput, RverticalRollerDemand);
        horizontalRoller.set(ControlMode.PercentOutput, hRollerDemand);
        tunnelBeltRoller.set(ControlMode.PercentOutput, tunnelBeltRollerDemand);
    }

    public void setManual(double demand) {
        double LVdemand = Math.signum(demand);
        double RVdemand = Math.signum(demand) * 0.8;
        
        leftVerticalRoller.set(ControlMode.PercentOutput, LVdemand);
        rightVerticalRoller.set(ControlMode.PercentOutput, RVdemand);
        horizontalRoller.set(ControlMode.PercentOutput, demand);
        tunnelBeltRoller.set(ControlMode.PercentOutput, demand);
    }

    public boolean getTunnelBallDetection() {
        return hasBallInTunnel;
    }

    @Override
    public void readPeriodicInputs() {
        // TODO Auto-generated method stub
        PeriodicSubsystem.super.readPeriodicInputs();
        this.hasBallInTunnel = tunnelBallDetection.get();
    }

    @Override
    public void writePeriodicOutputs() {
        // TODO Auto-generated method stub
        PeriodicSubsystem.super.writePeriodicOutputs();
    }

    @Override
    public void end() {
        // TODO Auto-generated method stub
        PeriodicSubsystem.super.end();
        setSignal(IndexerSignal.STOP);
    }



    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}