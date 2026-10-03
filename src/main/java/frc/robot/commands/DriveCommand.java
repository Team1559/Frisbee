package frc.robot.commands;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RPM;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class DriveCommand extends Command{
    private CommandXboxController controller;
    private DriveTrain driveTrain;
    private LinearVelocity maxLinearVelocity;
    private AngularVelocity maxAngularVelocity;

    public DriveCommand(DriveTrain driveTrain, CommandXboxController controller, LinearVelocity maxLinearVelocity, AngularVelocity maxAngularVelocity) {
        this.controller = controller;
        this.driveTrain = driveTrain;
        this.maxLinearVelocity = maxLinearVelocity;
        this.maxAngularVelocity = maxAngularVelocity;

        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        double forwardSpeed = Math.pow(controller.getLeftY(), 2);
        double rotationSpeed = -Math.pow(controller.getRightX(), 2);
        if (!(controller.rightBumper().getAsBoolean() && controller.leftBumper().getAsBoolean())) {
            if (Math.abs(forwardSpeed) > Constants.MAX_KIDDIE_DRIVE_VELOCITY_FORWARDS) {
                forwardSpeed = Math.copySign(Constants.MAX_KIDDIE_DRIVE_VELOCITY_FORWARDS, forwardSpeed);
            }
            if (Math.abs(rotationSpeed) > Constants.MAX_KIDDIE_DRIVE_VELOCITY_ROTATION) {
                rotationSpeed = Math.copySign(Constants.MAX_KIDDIE_DRIVE_VELOCITY_ROTATION, rotationSpeed);
            }

            
        }
        driveTrain.drive(maxLinearVelocity.times(forwardSpeed), maxAngularVelocity.times(rotationSpeed));
    }

    @Override 
    public void end(boolean isInterrupted) {
        driveTrain.drive(MetersPerSecond.zero(), RPM.zero());
    }
}
