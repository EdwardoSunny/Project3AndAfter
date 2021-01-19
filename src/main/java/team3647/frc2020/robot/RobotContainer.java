package team3647.frc2020.robot;

import com.ctre.phoenix.CANifier;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import team3647.frc2020.commands.ArcadeDrive;
import team3647.frc2020.commands.GoStraightDistance;
import team3647.frc2020.commands.HatchGrabber;
import team3647.frc2020.inputs.Joysticks;
import team3647.frc2020.subsystems.Drivetrain;
import team3647.frc2020.subsystems.Elevator;
import team3647.frc2020.subsystems.Hood;
import team3647.frc2020.subsystems.Indexer;


public class RobotContainer {
  private final Joysticks controller = new Joysticks(0);
  private final CANifier canifier = new CANifier(Constants.canifierID);

  public final Drivetrain dt = new Drivetrain(Constants.leftMasterConfig, Constants.rightMasterConfig, Constants.leftSlaveConfig, Constants.rightSlaveConfig, canifier);
  public final Elevator elevator = new Elevator(Constants.ElevatorMasterConfig, Constants.ElevatorSPX1Config, Constants.ElevatorSPX2Config, dt::setSlow, true);
  private final CommandScheduler m_commandScheduler = CommandScheduler.getInstance();
  public final Hood hood = new Hood(Constants.hoodPWMPortChannel);

  public final Indexer m_Indexer = new Indexer(Constants.frontRollerConfig, Constants.tunnelRollerConfig, Constants.LorganizingRollerConfig, Constants.RorganizingRollerConfig, Constants.ballDetectionPin);

  public final Command autonomousCommand = new GoStraightDistance(dt, 120);

  public RobotContainer() {
    configButtonBindings();
    m_commandScheduler.registerSubsystem(dt);
    m_commandScheduler.setDefaultCommand(dt,
        new ArcadeDrive(dt, controller::getLeftStickY, controller::getRightStickX));
    


  }

  public Command getAutonomousCommand() {
    return autonomousCommand;
  }

  public boolean getDrivetrainSlowed() {
    return dt.getSlow();
  }

  public boolean getCargoCondition() {
    return dt.cargoDetection();
  }

  private void configButtonBindings(){
    controller.buttonX.whenActive(new InstantCommand(() -> dt.setSlow(!dt.getSlow()), dt));
    controller.leftBumper.whenActive(new HatchGrabber(dt::getdtVelocity, true, dt::cargoDetection));
    controller.rightBumper.whenActive(new HatchGrabber(dt::getdtVelocity, false, dt::cargoDetection));


    controller.buttonA.whenActive(new InstantCommand(() -> elevator.moveToBottom(), elevator));
    controller.buttonB.whenActive(new InstantCommand(() -> elevator.moveToMiddle(), elevator));
    controller.buttonY.whenActive(new InstantCommand(() -> elevator.moveToTop(), elevator));


    //what should the presets be?
    controller.dPadDown.whenActive(new InstantCommand(() -> hood.setPosition(0)));
    controller.dPadUp.whenActive(new InstantCommand(() -> hood.setPosition(0.3)));
    controller.dPadLeft.whenActive(new InstantCommand(() -> hood.setPosition(0.6)));
    controller.dPadRight.whenActive(new InstantCommand(() -> hood.setPosition(1)));

    


  }

  public void init(){
    
  }
}