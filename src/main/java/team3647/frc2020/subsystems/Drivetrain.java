package team3647.frc2020.subsystems;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.GeneralPin;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpiutil.math.MathUtil;

import team3647.lib.GroupPrinter;
import team3647.lib.drivers.SparkMaxFactory;
import team3647.lib.drivers.TalonSRXFactory;
import team3647.lib.drivers.VictorSPXFactory;


public class Drivetrain implements PeriodicSubsystem {
  private final CANSparkMax leftMaster;
  private final CANSparkMax rightMaster;
  private final CANSparkMax leftSlave;
  private final CANSparkMax rightSlave;

  private CANifier canifier;

  private double leftMasterEncoderValue;
  private double rightMasterEncoderValue;
  private double leftVelocity;
  private double rightVelocity;

  private CANEncoder leftMasterEncoder;
  private CANEncoder rightMasterEncoder;

  private double throttleMulti;
  private boolean isSlowed;


  public periodicIO p_IO = new periodicIO();
  public static final double kDefaultDeadband = 0.02;
  public static final double kDefaultMaxOutput = 1.0;

  protected double m_deadband = kDefaultDeadband;
  protected double m_maxOutput = kDefaultMaxOutput;
  private boolean squareInputs = false;

    //because config is already inverted
  private double m_rightSideInvertMultiplier = 1.0;


  public Drivetrain(SparkMaxFactory.Configuration leftMasterConfig, SparkMaxFactory.Configuration rightMasterConfig, SparkMaxFactory.Configuration leftSlaveConfig, SparkMaxFactory.Configuration rightSlaveConfig) {
      
    leftMaster = SparkMaxFactory.createSparkMax(leftMasterConfig);
    rightMaster = SparkMaxFactory.createSparkMax(rightMasterConfig);
    leftSlave = SparkMaxFactory.createSparkMax(leftSlaveConfig);
    rightSlave = SparkMaxFactory.createSparkMax(rightSlaveConfig);

    leftMasterEncoder = leftMaster.getEncoder();
    rightMasterEncoder = rightMaster.getEncoder();

    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);
    throttleMulti = 0.6;
  
  }  

  public static class periodicIO {
    public double distanceTraveled;
  }

  public void arcadeDrive(double xSpeed, double zRotation)  {
    xSpeed *= throttleMulti;
    zRotation *= 0.3;
    xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);
    xSpeed = applyDeadband(xSpeed, m_deadband);

    zRotation = MathUtil.clamp(zRotation, -1.0, 1.0);
    zRotation = applyDeadband(zRotation, m_deadband);

    // Square the inputs (while preserving the sign) to increase fine control
    // while permitting full power.
    if (squareInputs) {
      xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
      zRotation = Math.copySign(zRotation * zRotation, zRotation);
    }

    double leftMotorOutput;
    double rightMotorOutput;

    double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

    if (xSpeed >= 0.0) {
      // First quadrant, else second quadrant
      if (zRotation >= 0.0) {
        leftMotorOutput = maxInput;
        rightMotorOutput = xSpeed - zRotation;
      } else {
        leftMotorOutput = xSpeed + zRotation;
        rightMotorOutput = maxInput;
      }
    } else {
      // Third quadrant, else fourth quadrant
      if (zRotation >= 0.0) {
        leftMotorOutput = xSpeed + zRotation;
        rightMotorOutput = maxInput;
      } else {
        leftMotorOutput = maxInput;
        rightMotorOutput = xSpeed - zRotation;
      }
    }

    leftMaster.set(MathUtil.clamp(leftMotorOutput, -1.0, 1.0));
    
    rightMaster.set(MathUtil.clamp(rightMotorOutput, -1.0, 1.0));
  }

  private double applyDeadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  public void updateEncoders() {
    //convert to revolution
    leftMasterEncoderValue = (leftMasterEncoder.getPosition()/4096);
    rightMasterEncoderValue = (rightMasterEncoder.getPosition()/4096);

    //convert from ticks/100ms to rev/sec to ft/s
    leftVelocity = leftMasterEncoder.getVelocity() * (10/4096) * 0.5;
    rightVelocity = rightMasterEncoder.getVelocity() * (10/4096) * 0.5;
  }

  public void resetEncoders() {
    leftMasterEncoderValue = 0;
    rightMasterEncoderValue = 0;
    leftMasterEncoder.setPosition(0);
    rightMasterEncoder.setPosition(0);
    leftVelocity = 0;
    rightVelocity = 0;
  } 

  public void updateDistanceTraveled() {
    double distanceL = leftMasterEncoderValue * 6 * Math.PI;
    double distanceR = rightMasterEncoderValue * 6 * Math.PI;
    p_IO.distanceTraveled = (distanceL + distanceR)/2;
  }

  public void resetDistanceTraveled(){
    p_IO.distanceTraveled = 0;
  }

  public void setSlow(boolean slowed) {
    if (slowed) {
      isSlowed = true;
      throttleMulti = 0.2;
    } else {
      isSlowed = false;
      throttleMulti = 0.6;
    }
  }

  public boolean getSlow() {
    return isSlowed;
  }

  public double getDistanceTraveled() {
    return p_IO.distanceTraveled;
  }

  public double getdtVelocity() {
    return (leftVelocity + rightVelocity)/2;
  }


  public void init() {
    resetEncoders();
    resetDistanceTraveled();
  }

  public void end() {
    //stops everything
    leftMaster.set(0);
    rightMaster.set(0);
  }

  public void readPeriodicInputs() {
    updateEncoders();
    updateDistanceTraveled();
  }

  public void writePeriodicOutputs() {
  }

  @Override
  public void periodic() {
    //when its regeistered in the commandScheuler --> every command interation, this is called before the command
    PeriodicSubsystem.super.periodic();
      
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return "DriveTrain";
  }

}
