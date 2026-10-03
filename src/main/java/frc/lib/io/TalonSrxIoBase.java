package frc.lib.io;

import edu.wpi.first.units.measure.Voltage;
import frc.lib.component.VoltageComponent;
import frc.lib.logging.LoggableIo;

import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class TalonSrxIoBase extends LoggableIo<TalonSrxIoBase.TalonSrxIoInputs> 
    implements VoltageComponent {

    @AutoLog
    public static abstract class TalonSrxIoInputs implements LoggableInputs {
        public Voltage motorVoltage;
    }

    public TalonSrxIoBase() {
        super(new TalonSrxIoInputsAutoLogged());
    }

    @Override
    public void neutralOutput() {
        logger().debug("Active", false);
    }

    @Override
    public void setVoltage(Voltage voltage) {
        logger().debug("Voltage", voltage.in(Volts))
                .debug("Active", true);
    }

    @Override
    public Voltage getVoltage() {
        return getInputs().motorVoltage;
    }
}
