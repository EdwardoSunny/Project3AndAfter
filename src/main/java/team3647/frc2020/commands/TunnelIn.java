package team3647.frc2020.commands; 
import edu.wpi.first.wpilibj2.command.CommandBase;
import team3647.frc2020.subsystems.HotDogIndexer;
import team3647.lib.IndexerSignal;

public class TunnelIn extends CommandBase {
    private final HotDogIndexer indexer;

    public TunnelIn(HotDogIndexer indexer) {
        this.indexer = indexer;
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        indexer.setSignal(IndexerSignal.INDEXERFWD_SLOW);
    }

    @Override 
    public void end(boolean interrupted) {
        indexer.setSignal(IndexerSignal.STOP);
    }  

    @Override 
    public boolean isFinished() {
        return indexer.getTunnelBallDetection();
    }

}