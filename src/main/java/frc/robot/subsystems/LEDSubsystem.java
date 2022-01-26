package frc.robot.subsystems;
// 76 lights on the strip --> set.length(76)
// https://docs.wpilib.org/en/stable/docs/software/hardware-apis/misc/addressable-leds.html
public class LEDSubsystem 
{
    @Override
    public void robotInit() {
      // PWM port 2
      // Must be a PWM header, not MXP or DIO
      m_led = new AddressableLED(2);
  
      // Reuse buffer
      // Default to a length of 60, start empty output
      // Length is expensive to set, so only set it once, then just update data
      m_ledBuffer = new AddressableLEDBuffer(76);
  
      // Set the data
      m_led.setData(m_ledBuffer);
      m_led.start();
    }
}
