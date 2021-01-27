package team3647.frc2020.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import team3647.frc2020.commands.ArcadeDrive;
import team3647.frc2020.commands.GoStraightDistancePID;
import team3647.frc2020.commands.IndexerManualMode;
import team3647.frc2020.commands.OrganizeFeeder;
import team3647.frc2020.inputs.Joysticks;
import team3647.frc2020.subsystems.Drivetrain;
import team3647.frc2020.subsystems.Hood;
import team3647.frc2020.subsystems.HotDogIndexer;



public class RobotContainer {
  private final Joysticks controller = new Joysticks(0);

  public final Drivetrain dt = new Drivetrain(Constants.leftMasterConfig, Constants.rightMasterConfig, Constants.leftSlaveConfig, Constants.rightSlaveConfig);
  private final CommandScheduler m_commandScheduler = CommandScheduler.getInstance();
  public final Hood hood = new Hood(Constants.hoodPWMPortChannel);

  public final HotDogIndexer m_Indexer = new HotDogIndexer(Constants.rightRollersConfig, Constants.leftRollersConfig, Constants.horizontalRollersConfig, Constants.tunnelConfig, Constants.ballDetectionPin);

  //public final Command autonomousCommand = new GoStraightDistance(dt, 120);
  public final Command autonomousCommand = new GoStraightDistancePID(dt, 10, Constants.kP);
  public RobotContainer() {
    configButtonBindings();
    m_commandScheduler.registerSubsystem(dt, m_Indexer);
    m_commandScheduler.setDefaultCommand(dt,
        new ArcadeDrive(dt, controller::getLeftStickY, controller::getRightStickX));
    m_commandScheduler.setDefaultCommand(m_Indexer, new IndexerManualMode(m_Indexer, controller::getRightStickY));
    


  }

  public Command getAutonomousCommand() {
    return autonomousCommand;
  }

  public boolean getDrivetrainSlowed() {
    return dt.getSlow();
  }

  private void configButtonBindings(){
    controller.buttonX.whenActive(new InstantCommand(() -> dt.setSlow(!dt.getSlow()), dt));

    //what should the presets be?
    controller.dPadDown.whenActive(new InstantCommand(() -> hood.setPosition(0)));
    controller.dPadUp.whenActive(new InstantCommand(() -> hood.setPosition(0.3)));
    controller.dPadLeft.whenActive(new InstantCommand(() -> hood.setPosition(0.6)));
    controller.dPadRight.whenActive(new InstantCommand(() -> hood.setPosition(1)));

    //automatic organize feeder
    controller.buttonA.whenHeld(new OrganizeFeeder(m_Indexer));
  }

  public void init(){
    
  }
}