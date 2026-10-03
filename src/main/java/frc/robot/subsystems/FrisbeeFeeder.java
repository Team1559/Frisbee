package frc.robot.subsystems;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Rotation;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Frequency;
import edu.wpi.first.units.measure.Voltage;
import frc.lib.component.AngularVelocityComponent;
import frc.lib.intermediate.AngularVelocityToVoltageAdapter;
import frc.lib.io.TalonSrxIoReal;
import frc.lib.logging.LoggableSubsystem;

public class FrisbeeFeeder extends LoggableSubsystem {
    private static final int FEEDER_ID = 4;
    private final AngularVelocityComponent feeder;
    
    public static final AngularVelocity FEEDER_MAX_ANGULAR_VELOCITY = RPM.of(75);
    public static final Voltage FEEDER_MAX_VOLTAGE = Volts.of(12);

    public static final Frequency FEED_RATE = Seconds.of(2).asFrequency();

    public FrisbeeFeeder() {
        super("FrisbeeFeeder");
        feeder = createFeeder();
        addChild("Feeder", feeder);
    }

    public void spinFeeder(Frequency feedRate){
        AngularVelocity feederVelocity = feedRate.times(Rotation.one());
        feeder.setVelocity(feederVelocity);
    }

    public void stopFeeder(){
        feeder.setVelocity(RadiansPerSecond.zero());
    }

    private static AngularVelocityComponent createFeeder(){
        WPI_TalonSRX motorTalonSRX = new WPI_TalonSRX(FEEDER_ID);
        TalonSrxIoReal motor = new TalonSrxIoReal(motorTalonSRX);
        return new AngularVelocityToVoltageAdapter(motor, FEEDER_MAX_ANGULAR_VELOCITY, FEEDER_MAX_VOLTAGE);
    }
    
}
