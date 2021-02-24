package team3647.frc2020.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import team3647.lib.drivers.ClosedLoopFactory;
import team3647.lib.drivers.SparkMaxFactory;
import team3647.lib.drivers.ClosedLoopFactory.ClosedLoopConfig;

public class Flywheel implements PeriodicSubsystem {
    private final CANSparkMax master;
    private final CANSparkMax slave;
    private final CANEncoder masterEncoder;
    private final CANEncoder slaveEncoder;
    private final CANPIDController PIDController;
    private final ClosedLoopConfig masterPIDConfig;
    private final SimpleMotorFeedforward feedforward;
    private ControlType controlType;
    private final periodicIO pIO = new periodicIO();

    public Flywheel(SparkMaxFactory.Configuration masterConfig, SparkMaxFactory.Configuration slaveConfig, ClosedLoopConfig masterPID) {
        this.master = SparkMaxFactory.createSparkMax(masterConfig);
        this.masterEncoder = master.getEncoder();
        //slave inverted
        PIDController = ClosedLoopFactory.createSparkMaxPIDController(master, masterEncoder, masterPID, 0);
        this.slave = SparkMaxFactory.createSparkMax(slaveConfig);
        this.slaveEncoder = slave.getEncoder();
        this.masterPIDConfig = masterPID;
        feedforward = new SimpleMotorFeedforward(masterPID.kS, masterPID.kV);
        slave.follow(master);
        controlType = ControlType.kDutyCycle;
    }

    class periodicIO {
        public double RPMReading;
        public double prevRPMReading;
        //volts b/c not talons
        public double feedforward;
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        PeriodicSubsystem.super.init();
    }

    public void setVelocity(double velocity) {
        double accelerationDemand = (pIO.RPMReading - velocity)/0.02;
        pIO.feedforward = this.feedforward.calculate(velocity/60, accelerationDemand/60);
        this.controlType = ControlType.kVelocity;
    }



    @Override
    public void end() {

    }

    @Override
    public void readPeriodicInputs() {
        // TODO Auto-generated method stub
        PeriodicSubsystem.super.readPeriodicInputs();
        pIO.prevRPMReading = pIO.RPMReading;
        pIO.RPMReading = (masterEncoder.getVelocity() * masterPIDConfig.kEncoderVelocityToRPM + slaveEncoder.getVelocity() * masterPIDConfig.kEncoderTicksToUnits)/2;

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