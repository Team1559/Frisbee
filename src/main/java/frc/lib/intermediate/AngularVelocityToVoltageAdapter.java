package frc.lib.intermediate;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import frc.lib.component.AngularVelocityComponent;
import frc.lib.component.VoltageComponent;
import frc.lib.logging.LoggableAdapter;

public class AngularVelocityToVoltageAdapter extends LoggableAdapter<VoltageComponent> implements AngularVelocityComponent {   //MIGHT need a new name
    //UNITS ARE VOLTS PER ROTATIONS PER MINUTE
    private final AngularVelocity maxAngularVelocity;
    private final Voltage maxVoltage;

    public AngularVelocityToVoltageAdapter(VoltageComponent child, AngularVelocity maxAngularVelocity, Voltage maxVoltage) {
        super(child);
        this.maxAngularVelocity = maxAngularVelocity;
        this.maxVoltage = maxVoltage;
    }

    @Override
    public void neutralOutput() {
        child.neutralOutput();
    }

    @Override
    public void setVelocity(AngularVelocity setpoint) {
        child.setVoltage(setpoint.div(maxAngularVelocity).times(maxVoltage));
    }

    @Override
    public AngularVelocity getCurrentVelocity() {
        return child.getVoltage().div(maxVoltage).times(maxAngularVelocity);
    }
}
