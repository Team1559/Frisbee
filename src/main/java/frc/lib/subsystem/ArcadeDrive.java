package frc.lib.subsystem;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import frc.lib.component.LinearVelocityComponent;
import frc.lib.logging.LoggableSubsystem;

public class ArcadeDrive extends LoggableSubsystem {
    private final LinearVelocityComponent leftMotor;
    private final LinearVelocityComponent rightMotor;
    private final Distance radius;
    private final LinearVelocity maxMotorLinearVelocity;

    //literally just holds left / right speeds
    public static class WheelSpeeds {
        public LinearVelocity left;
        public LinearVelocity right;

        public WheelSpeeds() {}

        public WheelSpeeds(LinearVelocity left, LinearVelocity right) {
            this.left = left;
            this.right = right;
        }
    }

    public ArcadeDrive(String name, LinearVelocityComponent leftMotor, LinearVelocityComponent rightMotor, Distance radius, LinearVelocity maxMotorLinearVelocity) {
        super(name);
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.radius = radius;
        this.maxMotorLinearVelocity = maxMotorLinearVelocity;
        addChild("leftMotor", leftMotor);
        addChild("rightMotor", rightMotor);
    }

    public void drive(LinearVelocity forwardSpeed, AngularVelocity rotationRate) {
        WheelSpeeds speeds = arcadeDriveInverseKinematics(forwardSpeed, rotationRate);

        leftMotor.setVelocity(speeds.left);
        rightMotor.setVelocity(speeds.right);
    }

    public WheelSpeeds arcadeDriveInverseKinematics(LinearVelocity xSpeed, AngularVelocity zRotationRate) {
        LinearVelocity arcVelocity = MetersPerSecond.of(zRotationRate.in(RadiansPerSecond) * radius.in(Meters));
        LinearVelocity leftVelocity = xSpeed.minus(arcVelocity);
        LinearVelocity rightVelocity = xSpeed.plus(arcVelocity);

        LinearVelocity greaterSpeed = max(leftVelocity, rightVelocity);

        if (greaterSpeed.gte(maxMotorLinearVelocity)) {
            leftVelocity = leftVelocity.times(maxMotorLinearVelocity.div(greaterSpeed));
            rightVelocity = rightVelocity.times(maxMotorLinearVelocity.div(greaterSpeed));
        }

        return new WheelSpeeds(leftVelocity, rightVelocity);
    }

    private static LinearVelocity max(LinearVelocity v1, LinearVelocity v2) {
        if (v1.gte(v2)) {
            return v1;
        }
        return v2;
    }
}
