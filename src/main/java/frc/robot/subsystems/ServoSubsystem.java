package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class ServoSubsystem extends SubsystemBase {
    Servo cameraServo = new Servo(8); // servo is wired to port #8


    public ServoSubsystem() {
       
    }

    public void moveServobyAngle(double angle) {
        cameraServo.setAngle(angle);
    }

    public void moveServobyValue(double value) {
        cameraServo.set(value);
    }

    @Override
    public void periodic() {
    // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    }
}

