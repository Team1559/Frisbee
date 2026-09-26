package frc.lib.io;

import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.units.measure.Voltage;



public class TalonSrxIoReal extends TalonSrxIoBase {
    private final WPI_TalonSRX motor;
    
    public TalonSrxIoReal (WPI_TalonSRX motor) {
        this.motor = motor;
    }

    @Override
    protected void updateInputs(TalonSrxIoInputs inputs) {
        inputs.motorVoltage = Volts.of(motor.getMotorOutputVoltage());
    }

    @Override
    public void setVoltage(Voltage voltage) {
        super.setVoltage(voltage);
        motor.setVoltage(voltage);
    }

    @Override
    public void neutralOutput() {
        super.neutralOutput();
        motor.stopMotor();
    }
}
