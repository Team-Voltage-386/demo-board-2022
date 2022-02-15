package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// using 10 lights on the strip (for now)--> set.length(10)
// https://docs.wpilib.org/en/stable/docs/software/hardware-apis/misc/addressable-leds.html
public class LEDSubsystem extends SubsystemBase {

  private static final int kLedPort = 9;

  private static final int kLedLength = 76;

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
      setRed(i);
    }
  }

  public void FixedColorRainbow() { // not sure if this works yet because we did not get a chance to deploy
    for (var i = 0; i < kLedLength; i++) {
      int remainder = i % 5;

      switch (remainder) {
        case 0:
          setYellow(i);
          break;
        case 1:
          setGreen(i);
          break;
        case 2:
          setBlue(i);
          break;
        case 3:
          setPurple(i);
          break;
        case 4:
          setRed(i);
          break;
        default:
          setWhite(i);
          break;
      }
    }

  }

  /**
   * shifts the LED lights by 1
   */
  public void MoveLights() {
    Color initialColor = ledBuffer.getLED(0);
    for (var i = 0; i < kLedLength - 1; i++) {
      ledBuffer.setLED(i, ledBuffer.getLED(i + 1));
    }
    ledBuffer.setLED(kLedLength - 1, initialColor);

  }

  public void setRed(int index) {

    ledBuffer.setRGB(index, 255, 0, 0); // red
  }
  // @Override
  // public void robotInit() {

  // }
  // Set LED to White Color (R=255, G=255, B=255)
  public void setWhite(int index) {

    ledBuffer.setRGB(index, 255, 255, 255);
  }

  // Set LED to Green Color (R=0, G=255, B=0)
  public void setGreen(int index) {

    ledBuffer.setRGB(index, 0, 255, 0);
  }

  // Set LED to Blue Color (R=0, G=0, B=255)
  public void setBlue(int index) {

    ledBuffer.setRGB(index, 0, 0, 255);
  }

  // Set LED to Purple Color (R=128, G=0, B=128)
  public void setPurple(int index) {

    ledBuffer.setRGB(index, 128, 0, 128);
  }

  // Set LED to Yellow Color (R=255, G=255, B=0)
  public void setYellow(int index) {

    ledBuffer.setRGB(index, 255, 255, 0);
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
