package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class DriveCommand extends Command{
    private CommandXboxController controller;
    private DriveTrain driveTrain;

    public DriveCommand(DriveTrain driveTrain, CommandXboxController controller) {
        this.controller = controller;
        this.driveTrain = driveTrain;
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
        driveTrain.drive(forwardSpeed, rotationSpeed);
    }

    @Override 
    public void end(boolean isInterrupted) {
        driveTrain.drive(0, 0);
    }
}
