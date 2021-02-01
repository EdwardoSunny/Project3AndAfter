package team3647.frc2020.subsystems;

import team3647.lib.wpi.Solenoid;

public class Intake implements PeriodicSubsystem {
    private final Solenoid leftPair;
    private final Solenoid rightPair;

    public Intake(int leftPairPin, int rightPairPin) {
        this.leftPair = new Solenoid(leftPairPin);
        this.rightPair = new Solenoid(rightPairPin);
    }

    public enum IntakeState {
        GROUND, LOADING_STATION, TACOBELL, STOWED
    }

    public void setIntakeMode(IntakeState state) {
        switch (state) {
            case GROUND:
                
        }
    }

    

    @Override
    public void readPeriodicInputs() {
        // TODO Auto-generated method stub
        PeriodicSubsystem.super.readPeriodicInputs();
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