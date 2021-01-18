package team3647.frc2020.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team3647.frc2020.subsystems.Indexer;

public class IndexerManualMode extends CommandBase {
    private final Indexer indexer;
    private final double demand;

    public IndexerManualMode(Indexer indexer, DoubleSupplier demand) {
        this.indexer = indexer;
        this.demand = demand.getAsDouble();
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        indexer.manuallySet(demand);
    }

    @Override 
    public void end(boolean interrupted) {
        
    }  

    @Override 
    public boolean isFinished() {
        return false;
    }
}