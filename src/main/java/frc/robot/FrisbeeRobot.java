package frc.robot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.lib.Robot;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.FeederCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.FrisbeeFeeder;
import frc.robot.subsystems.FrisbeeFlinger;

public class FrisbeeRobot extends Robot{

    private final DriveTrain driveTrain;
    private final FrisbeeFlinger frisbeeFlinger;
    private final FrisbeeFeeder frisbeeFeeder;
    private final CommandXboxController controller;

    public FrisbeeRobot(){
        driveTrain = new DriveTrain();
        frisbeeFlinger = new FrisbeeFlinger();
        frisbeeFeeder = new FrisbeeFeeder();
        controller = new CommandXboxController(0);
    }

    @Override
    protected Command getAutoCommand() {
        throw new UnsupportedOperationException("Unimplemented method 'getAutoCommand'");
    }

    @Override
    protected void setTeleopBindings() {
        controller.rightTrigger().whileTrue(new ShooterCommand(frisbeeFlinger, () -> FrisbeeFlinger.FLYWHEEL_MAX_ANGULAR_VELOCITY));
        controller.rightTrigger().debounce(2)
                               .and(controller.leftTrigger())
                               .whileTrue(new FeederCommand(frisbeeFeeder, FrisbeeFeeder.FEED_RATE));   
        driveTrain.setDefaultCommand(new DriveCommand(driveTrain, controller, DriveTrain.MAX_PERMITTED_VELOCITY, DriveTrain.MAX_THEORETICAL_ANGULAR_VELOCITY));
    }

    @Override
    protected void setTestBindings() {
        throw new UnsupportedOperationException("Unimplemented method 'setTestBindings'");
    }
    
}
