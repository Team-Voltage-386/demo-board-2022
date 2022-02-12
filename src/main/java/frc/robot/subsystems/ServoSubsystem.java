package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class ServoSubsystem extends SubsystemBase {
    Servo cameraServo = new Servo(8); // servo is wired to port #8
    private final ShuffleboardTab tab2 = Shuffleboard.getTab("Servo");
    // private NetworkTableEntry maxAngle = tab2.add("Max Angle",
    // 180).withPosition(0,0).withSize(0, 0)
    // .getEntry();

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
