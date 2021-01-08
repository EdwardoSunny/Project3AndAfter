package team3647.frc2020.subsystems;

import edu.wpi.first.wpilibj.Servo;

public class Hood implements PeriodicSubsystem {
    private final Servo hood;

    public Hood(int PWMPort) {
        this.hood = new Servo(PWMPort);
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
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }
    
}