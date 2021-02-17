package team3647.frc2020.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team3647.frc2020.commands.ArcadeDrive;
import team3647.frc2020.commands.GoStraightDistancePID;
import team3647.frc2020.commands.GroundIntake;
import team3647.frc2020.commands.IndexerManualMode;
import team3647.frc2020.commands.LoadBalls;
import team3647.frc2020.commands.LoadingStationIntake;
import team3647.frc2020.commands.StowIntake;
import team3647.frc2020.commands.TacoBell;
import team3647.frc2020.commands.TunnelIn;
import team3647.frc2020.commands.TunnelOut;
import team3647.frc2020.inputs.Joysticks;
import team3647.frc2020.subsystems.BallStopper;
import team3647.frc2020.subsystems.Drivetrain;
import team3647.frc2020.subsystems.Hood;
import team3647.frc2020.subsystems.HotDogIndexer;
import team3647.frc2020.subsystems.Intake;



public class RobotContainer {
  private final Joysticks controller = new Joysticks(0);
  private final Joysticks coController = new Joysticks(1);

  public final Drivetrain dt = new Drivetrain(Constants.leftMasterConfig, Constants.rightMasterConfig, Constants.leftSlaveConfig, Constants.rightSlaveConfig, Constants.leftMasterPIDConfig, Constants.rightMasterPIDConfig, Constants.driveWheelDiameter);
  private final CommandScheduler m_commandScheduler = CommandScheduler.getInstance();
  public final Hood hood = new Hood(Constants.hoodPWMPortChannel);
  public final Intake intake = new Intake(Constants.innerSolenoidPin, Constants.outerSolenoidPin, Constants.intakeMotorConfig);
  public final BallStopper stopper = new BallStopper(Constants.solenoidPin);

  public final HotDogIndexer m_Indexer = new HotDogIndexer(Constants.rightRollersConfig, Constants.leftRollersConfig, Constants.horizontalRollersConfig, Constants.tunnelConfig, Constants.bannerSensorPin);

  //public final Command autonomousCommand = new GoStraightDistance(dt, 10);
  public final Command autonomousCommand = new GoStraightDistancePID(dt, 10, Constants.kP, Constants.kI, Constants.kD); 
  public RobotContainer() {
    configButtonBindings();
    m_commandScheduler.registerSubsystem(dt, m_Indexer, intake, hood
    );
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
    coController.dPadDown.whenActive(new InstantCommand(() -> hood.setPosition(0)));
    coController.dPadUp.whenActive(new InstantCommand(() -> hood.setPosition(0.3)));
    coController.dPadLeft.whenActive(new InstantCommand(() -> hood.setPosition(0.6)));
    coController.dPadRight.whenActive(new InstantCommand(() -> hood.setPosition(1)));

    //automatic organize feeder
    coController.buttonA.whenHeld(new SequentialCommandGroup(new TunnelOut(m_Indexer), new TunnelIn(m_Indexer).withTimeout(0.5)));
    //indexer manual mode
    coController.buttonB.whenHeld(new IndexerManualMode(m_Indexer, controller::getRightStickY));

    //intake positions
    //GroundIntake
    coController.leftTrigger.whenActive(new SequentialCommandGroup(new RunCommand(intake::retractInner, intake).withTimeout(0.5), new ParallelCommandGroup(new GroundIntake(intake), new LoadBalls(m_Indexer, stopper))));
    //loading station
    coController.rightBumper.whenActive(new ParallelCommandGroup(new LoadingStationIntake(intake), new LoadBalls(m_Indexer, stopper)));
    // stowed
    coController.leftTrigger.whenReleased(new StowIntake(intake).withTimeout(0.5));
    // tacobell
    coController.rightTrigger.whenActive(new ParallelCommandGroup(new TacoBell(intake), new TunnelOut(m_Indexer)));
  }

  public void init(){
    
  }
}