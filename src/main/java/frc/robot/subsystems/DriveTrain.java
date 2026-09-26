package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class DriveTrain extends SubsystemBase{
    private DifferentialDrive driveTrain;

    public DriveTrain(int leftID, int rightID) {
        WPI_TalonSRX leftMotorTalonSRX = new WPI_TalonSRX(leftID);
        WPI_TalonSRX rightMotorTalonSRX = new WPI_TalonSRX(rightID);
        rightMotorTalonSRX.setInverted(true);
        driveTrain = new DifferentialDrive(leftMotorTalonSRX, rightMotorTalonSRX);
    }

    public void drive (double forwardSpeed, double rotation) {
        driveTrain.arcadeDrive(forwardSpeed, rotation);
    }
}
