package team3647.frc2020.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import team3647.frc2020.subsystems.KickerWheel;

public class AccelerateKickerWheel extends CommandBase {
    private final KickerWheel kicker;
    private double demandRPM;
    private double lastTimeupdate;
    private double previousError;
    private final double kP;
    private final double kI;
    private final double kD;

    public AccelerateKickerWheel(KickerWheel kicker, double demand, double kP, double kI, double kD) {
        this.kicker = kicker;
        this.demandRPM = demand;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
        lastTimeupdate = Timer.getFPGATimestamp();
        previousError = 0;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        super.execute();
        double currentRPM = kicker.getRPM();
        double error = demandRPM - currentRPM;
        double errorRate = (error - previousError)/lastTimeupdate;

        double output = kP * error + kD * errorRate;
        kicker.setRPM(output);
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
        kicker.end();
    }
}