package team3647.frc2020.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team3647.frc2020.subsystems.HotDogIndexer;

public class OrganizeFeeder extends SequentialCommandGroup {

    public OrganizeFeeder(HotDogIndexer indexer) {
        super(new TunnelOut(indexer), new TunnelIn(indexer).withTimeout(0.5));
    }
}