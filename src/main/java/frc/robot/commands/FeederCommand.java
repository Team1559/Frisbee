package frc.robot.commands;

import edu.wpi.first.units.measure.Frequency;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FrisbeeFeeder;

public class FeederCommand extends Command {
    private final Frequency feedRate;
    private final FrisbeeFeeder feeder;

    public FeederCommand(FrisbeeFeeder feeder, Frequency feedRate) {
        this.feeder = feeder;
        this.feedRate = feedRate;
        addRequirements(feeder);
    }

    @Override
    public void execute() {
        feeder.spinFeeder(feedRate);
    }

    @Override
    public void end(boolean interrupted) {
        feeder.stopFeeder();
    }

}
