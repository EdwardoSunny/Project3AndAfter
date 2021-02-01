package team3647.frc2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team3647.frc2020.subsystems.Drivetrain;

public class GoStraightDistancePID extends CommandBase {
    private final Drivetrain dt;
    private boolean atSetpoint;
    private int setpoint;
    private double kP;

    public GoStraightDistancePID(Drivetrain dt, int setpoint, double kP) {
        this.setpoint = setpoint;
        this.dt = dt;
        atSetpoint = false;
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
        dt.resetDistanceTraveled();
        dt.resetEncoders();
    }

    @Override
    public void execute() {
        double error = setpoint - dt.getDistanceTraveled();
        double outputSpeed = kP * error; 
        dt.arcadeDrive(outputSpeed, 0);
        if (Math.abs(error) < 0.2) {
            atSetpoint = true;
        }
    }

    @Override 
    public void end(boolean interrupted) {
        dt.end();
    }  

    @Override 
    public boolean isFinished() {
        return atSetpoint;
    }
}