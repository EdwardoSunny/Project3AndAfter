package team3647.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import team3647.lib.drivers.ClosedLoopFactory;
import team3647.lib.drivers.TalonSRXFactory;
import team3647.lib.drivers.ClosedLoopFactory.ClosedLoopConfig;

public class KickerWheel implements PeriodicSubsystem {
    private final TalonSRX kickerWheel;
    private final ClosedLoopConfig PIDConfig;
    
    public KickerWheel(TalonSRXFactory.Configuration kickerConfig, ClosedLoopConfig PIDConfig) {
        this.kickerWheel = TalonSRXFactory.createTalon(kickerConfig);
        this.PIDConfig = PIDConfig;
        ClosedLoopFactory.configTalonPIDController(kickerWheel, FeedbackDevice.CTRE_MagEncoder_Relative, this.PIDConfig, 0);
    } 
    
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}