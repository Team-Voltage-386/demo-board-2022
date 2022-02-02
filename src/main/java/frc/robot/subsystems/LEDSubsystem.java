package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.LedConstants.*;

// using 10 lights on the strip (for now)--> set.length(10)
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
    FixedColorRainbow();
  }

  public void StaticRedStartLights() {
    for (int i = 0; i < kLedLength; i++) {
      setMagenta(i);
    }
  }

  public void FixedColorRainbow() { 
    for (var i = 0; i < kLedLength; i++) {
      int remainder = i % 5;

      switch (remainder) {
      case 0:
        setLightPurple(i);
        break;
      case 1:
        setLightPink(i);
        break;
      case 2:
        setDarkTurquoise(i);
        break;
      case 3:
        setShadow(i);
        break;
      case 4:
        setMagenta(i);
        break;
      default:
        setWhite(i);
        break;
      }
    }

  }

  public void setMagenta(int index) {

    ledBuffer.setRGB(index, 255, 0, 255); // Magenta
  }
  // @Override
  // public void robotInit() {

  // }
  // Set LED to White Color (R=255, G=255, B=255)
  public void setWhite(int index) {

    ledBuffer.setRGB(index, 255, 255, 255);
  }

  // Set LED to Light Pink Color (R=255, G=182, B=193)
  public void setLightPink(int index) {

    ledBuffer.setRGB(index, 255, 255, 0);
  }

  // Set LED to Dark Turquoise Color (R=0, G=206, B=209)
  public void setDarkTurquoise(int index) {

    ledBuffer.setRGB(index, 0, 206, 209);
  }

  // Set LED to Shadow Color (R=51, G=204, B=51)
  public void setShadow(int index) {

    ledBuffer.setRGB(index, 51, 204, 51);
  }

  // Set LED to Light Purple Color (R=135, G=66, B=245)
  public void setLightPurple(int index) {

    ledBuffer.setRGB(index, 135, 66, 245);
  }

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

