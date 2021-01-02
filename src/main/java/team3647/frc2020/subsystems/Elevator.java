package team3647.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import team3647.frc2020.robot.Constants;
import team3647.lib.drivers.TalonSRXFactory;
import team3647.lib.drivers.VictorSPXFactory;

public class Elevator implements PeriodicSubsystem {
    private final TalonSRX talon;
    private final VictorSPX gearBoxSPX1;
    private final VictorSPX gearBoxSPX2;


    private int encoderValue;
    private int encoderVelocity;
    private boolean bannerSensorValue = false;
    private DigitalInput limitSwitch = new DigitalInput(Constants.elevatorBeamBreakPin);

    public Elevator(TalonSRXFactory.Configuration talonConfig, VictorSPXFactory.Configuration SPX1Config, VictorSPXFactory.Configuration SPX2Config) {
        talon = TalonSRXFactory.createTalon(talonConfig);
        gearBoxSPX1 = VictorSPXFactory.createVictor(SPX1Config);
        gearBoxSPX2 = VictorSPXFactory.createVictor(SPX2Config);
        
        gearBoxSPX1.follow(talon);
        gearBoxSPX2.follow(talon);
        talon.setInverted(false);
        talon.setInverted(false);
        
    }

    public void init() {

    }

    public void resetSRXEncoderValue() {
        encoderValue = 0;
        talon.setSelectedSensorPosition(0);
    }

    public void updateBannerSensor() {
        bannerSensorValue = limitSwitch.get();
    }

    public void updateSRXEncoder() {
        encoderValue = talon.getSelectedSensorPosition();
        encoderVelocity = talon.getSelectedSensorVelocity();
    }

    public void initEncoders() {
        talon.enableCurrentLimit(true);
		talon.configContinuousCurrentLimit(Constants.kElevatorContinuousCurrent);
        talon.setNeutralMode(NeutralMode.Brake);
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.kTimeoutMs);
		talon.setSensorPhase(true);
		talon.setInverted(false);
		talon.setNeutralMode(NeutralMode.Brake);
		updateBannerSensor();
    }

    public boolean getBannerSensorValue() {
        return bannerSensorValue;
    }

    public void moveToBottom() {
        
    }



    @Override
    public void readPeriodicInputs() {
        updateBannerSensor();
        updateSRXEncoder();
    }

    @Override
    public void writePeriodicOutputs() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    
}