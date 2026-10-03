package frc.robot.subsystems;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import frc.lib.component.AngularVelocityComponent;
import frc.lib.intermediate.AngularVelocityToVoltageAdapter;
import frc.lib.io.TalonSrxIoReal;
import frc.lib.logging.LoggableSubsystem;

public class FrisbeeFlinger extends LoggableSubsystem {
    private static final int FLYWHEEL_ID = 6;
    private final AngularVelocityComponent flywheel;

    public static final AngularVelocity FLYWHEEL_MAX_ANGULAR_VELOCITY = RPM.of(5000);
    public static final Voltage FLYWHEEL_MAX_VOLTAGE = Volts.of(12);

    public FrisbeeFlinger() {
        super("FrisbeeFlinger");
        flywheel = createFlywheel();
        addChild("Flywheel", flywheel);
    }

    public void spinShooter(AngularVelocity flywheelVelocity) {
        flywheel.setVelocity(flywheelVelocity);
    }

    public void stopShooter() {
        flywheel.setVelocity(RadiansPerSecond.zero());
    }

    private static AngularVelocityComponent createFlywheel() {
        WPI_TalonSRX motorTalonSRX = new WPI_TalonSRX(FLYWHEEL_ID);
        TalonSrxIoReal motor = new TalonSrxIoReal(motorTalonSRX);
        return new AngularVelocityToVoltageAdapter(motor, FLYWHEEL_MAX_ANGULAR_VELOCITY, FLYWHEEL_MAX_VOLTAGE);
    }

}
