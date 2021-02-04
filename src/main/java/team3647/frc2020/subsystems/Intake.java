package team3647.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import team3647.lib.drivers.TalonSRXFactory;
import team3647.lib.wpi.Solenoid;

public class Intake implements PeriodicSubsystem {
    private final Solenoid inner;
    private final Solenoid outer;
    private final TalonSRX intakeMotor;
    private boolean innerExtended;
    private boolean outerExtended;

    public Intake(int leftPairPin, int rightPairPin, TalonSRXFactory.Configuration intakeMotorConfig) {
        this.inner = new Solenoid(leftPairPin);
        this.outer = new Solenoid(rightPairPin);
        this.intakeMotor = TalonSRXFactory.createTalon(intakeMotorConfig);
    }

    public enum IntakeState {
        GROUND, LOADING_STATION, TACOBELL, STOWED
    }

    public void setIntakeMode(IntakeState state) {
        switch (state) {
            case GROUND:
                if (innerExtended && !outerExtended) {
                    inner.set(false);
                }
                outer.set(true);
                inner.set(true);
                break;
            case LOADING_STATION:
                inner.set(true);
                outer.set(false);
                break;
            case TACOBELL:
                inner.set(false);
                outer.set(true);
                break;
            case STOWED:
                inner.set(false);
                outer.set(false);
                break;
        }
    }

    public void setOpenLoop(double demand) {
        this.intakeMotor.set(ControlMode.PercentOutput, demand);
    }

    public void rollBallsIn(double demand) {
        setOpenLoop(-demand);
    }

    public void rollBallsOut(double demand) {
        setOpenLoop(demand);
    }

    @Override
    public void readPeriodicInputs() {
        // TODO Auto-generated method stub
        PeriodicSubsystem.super.readPeriodicInputs();
        innerExtended = this.inner.get();
        outerExtended = this.outer.get();
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
    }



    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }
    
}