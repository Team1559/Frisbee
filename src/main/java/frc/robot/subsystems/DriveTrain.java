package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Volt;
import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Voltage;
import frc.lib.component.LinearVelocityComponent;
import frc.lib.intermediate.AngularVelocityToVoltageAdapter;
import frc.lib.intermediate.WheelVelocityAdapter;
import frc.lib.io.TalonSrxIoReal;
import frc.lib.subsystem.ArcadeDrive;

public class DriveTrain extends ArcadeDrive {
    private static final int LEFT_ID = 5;
    private static final int RIGHT_ID = 10;

    private static final double GEAR_RATIO = 7.31; // TODO: ventured a guess: https://andymark.com/products/toughbox-mini-s?srsltid=AU7gw4Uy3yfCli7c9iPXfEQ09fcMiVd90DqcPzFsPnFN0zBEDW0sfYQ7
    private static final Distance WHEEL_RADIUS = Meters.of(0.083);
    private static final AngularVelocity FREE_MOTOR_SPEED = RPM.of(5000);
    private static final Distance TURNING_RADIUS = Inches.of(16.75/2);
    private static final Voltage MAX_VOLTAGE = Volts.of(12);

    private static final LinearVelocity MAX_THEORETICAL_VELOCITY = WHEEL_RADIUS.per(Second).times(FREE_MOTOR_SPEED.in(RadiansPerSecond)).div(GEAR_RATIO);
    public static final LinearVelocity MAX_PERMITTED_VELOCITY = MAX_THEORETICAL_VELOCITY;

    // public static final AngularVelocity MAX_THEORETICAL_ANGULAR_VELOCITY = MAX_THEORETICAL_VELOCITY.div(TURNING_RADIUS).times(Radians.one());
    public static final AngularVelocity MAX_THEORETICAL_ANGULAR_VELOCITY = RadiansPerSecond.of(MAX_THEORETICAL_VELOCITY.in(MetersPerSecond) / TURNING_RADIUS.in(Meters));

    public DriveTrain() {
        super("Drive Train", 
            createDriveMotor(false, LEFT_ID), 
            createDriveMotor(true, RIGHT_ID), 
            Meters.of(0.215),
            MAX_THEORETICAL_VELOCITY);
    }

    private static LinearVelocityComponent createDriveMotor(boolean setInverted, int ID) {
        WPI_TalonSRX motorTalonSRX = new WPI_TalonSRX(ID);
        motorTalonSRX.setInverted(setInverted);
        TalonSrxIoReal motor = new TalonSrxIoReal(motorTalonSRX);
        AngularVelocityToVoltageAdapter avtva = new AngularVelocityToVoltageAdapter(motor, FREE_MOTOR_SPEED, MAX_VOLTAGE);
        return new WheelVelocityAdapter(avtva.withVelocityRatio(GEAR_RATIO), WHEEL_RADIUS);
    }
}
