package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.LedConstants.*;

// 10 lights on the strip --> set.length(10)
// https://docs.wpilib.org/en/stable/docs/software/hardware-apis/misc/addressable-leds.html
public class LEDSubsystem extends SubsystemBase {

  // PWM port 9
  // Must be a PWM header, not MXP or DIO
  AddressableLED led = new AddressableLED(kLedPort);

  // Reuse buffer
  // Default to a length of 10, start empty output
  // Length is expensive to set, so only set it once, then just update data
  AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(kLedLength);

  /** Creates a new LEDSubsystem. */
  public LEDSubsystem() { // main method
    // Set the data
    led.setLength(kLedLength);
    led.setData(ledBuffer);
    led.start();

    StaticRedStartLights();
  }

  public void StaticRedStartLights() {
    for (int i = 0; i < kLedLength; i++) {
      setRed(i);
    }
  }

  public void setRed(int index) {

    ledBuffer.setRGB(index, 255, 0, 0); // red
  }
  // @Override
  // public void robotInit() {

  // }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Set the LEDs
    led.setData(ledBuffer);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

}
