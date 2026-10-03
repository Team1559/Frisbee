package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FrisbeeFlinger;

public class ShooterCommand extends Command {

    private final FrisbeeFlinger flinger;
    private final Supplier<AngularVelocity> setpoint;

    public ShooterCommand(FrisbeeFlinger flinger, Supplier<AngularVelocity> setpoint) {
        this.flinger = flinger;
        this.setpoint = setpoint;
        this.addRequirements(flinger);
    }

    @Override
    public void execute() {
        flinger.spinShooter(setpoint.get());
    }

    @Override
    public void end(boolean interrupted) {
        flinger.stopShooter();
    }

}
