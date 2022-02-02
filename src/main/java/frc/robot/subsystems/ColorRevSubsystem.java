package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import static frc.robot.Constants.LedConstants.*;

public class ColorRevSubsystem extends SubsystemBase {
    //LED initialization 
    AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(kLedLength);
    int m_rainbowFirstPixelHue;
    AddressableLED m_led = new AddressableLED(9);
    
    /*Creates Color Sensor Rev Subsystem*/
    I2C.Port demoSensorI2CPort = I2C.Port.kOnboard; // Port 0  
    /* sensor instantiations */
    private final ColorMatch m_colorMatcher = new ColorMatch();
    public final ColorSensorV3 m_demoColorSensor = new ColorSensorV3(demoSensorI2CPort);

    // Creates a Shuffleboard tab for the for Rev Color Detection
    private ShuffleboardTab tab = Shuffleboard.getTab("Ball Color");

    // Create sensor widgets
    private NetworkTableEntry isBlueOrRedWidget = tab.add("Blue or Red", "Red").withPosition(7, 3).withSize(2, 1).getEntry();
    
    //intializes Colors
    private final Color kBlueTarget = new Color(0.143, 0.427, 0.429);
    private final Color kRedTarget = new Color(0.561, 0.232, 0.114);
    //private final Color kGreenTarget = new  Color(0.197, 0.561, 0.240);/* unused but potential useful green color*/
    private final Color kYellowTarget = new Color(0.361, 0.524, 0.113);
    private boolean isBall= false;
    private boolean redOrBlue;

    
    // constructs LED and ColorMatcher
    public ColorRevSubsystem(){
         m_colorMatcher.addColorMatch(kBlueTarget);
         m_colorMatcher.addColorMatch(kRedTarget);
         m_colorMatcher.addColorMatch(kYellowTarget);
         m_led.setLength(m_ledBuffer.getLength());
         m_led.setData(m_ledBuffer);
         m_led.start();
    }
    //widget get methods
    public String getDemoColorSenseRed() {
        return "Red";
    }

    public String getDemoColorSenseBlue() {
        return "Blue";
    }

    public String getDemoColorSenseNull() {
        return "No Ball";
    }
        @Override
    public void periodic() {
        
        // This method will be called once per scheduler run
        //colormatch logic
        Color detectedColor = m_demoColorSensor.getColor();
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
        if (match.color == kBlueTarget) {
            isBlueOrRedWidget.setString(getDemoColorSenseBlue());
            isBall=true;
            redOrBlue=false;
        }else if (match.color == kRedTarget) {
            isBlueOrRedWidget.setString(getDemoColorSenseRed());
            isBall=true;
            redOrBlue=true;
        }else if (match.color == kYellowTarget) {
            isBlueOrRedWidget.setString(getDemoColorSenseNull());
            isBall=false;
        }
        //call to method after booleans updated 
        ColorDisplay();
    }    
    //LED update Method
    public void ColorDisplay(){
        if(isBall==false){
            StaticRainbowLights();
        }else{
            if(redOrBlue==true){
                StaticRedStarLights();
            }else{
                 StaticBlueStarLights();
            }
        }
        m_led.setData(m_ledBuffer);
    }
    //not needed but could be nessasary for other more complicated systems
/*
    public boolean getIsBall(){
        return isBall;
    }
    public boolean getRedOrBlue(){
        return redOrBlue;
    }
    public AddressableLEDBuffer getBuffer(){
        return m_ledBuffer;
    }
    public AddressableLED getLED(){
        return m_led;
    }
*/
    // Rainbow Lights - not changing
  public void StaticRainbowLights() {

    int distance = 180 / kLedLength;

    for (var i = 0; i < kLedLength; i++) {
      m_ledBuffer.setHSV(i, i * distance, 255, 255);
    }

  }
  //red lights set
  public void setRed(int index) {

    m_ledBuffer.setRGB(index, 255, 0, 0);
  }
  //blue lights set
  public void setBlue(int index) {

    m_ledBuffer.setRGB(index, 0, 0, 255);
  }
  //red light method use this to change LED
  public void StaticRedStarLights() {

    for (var i = 0; i < kLedLength; i++) {
      setRed(i);
    }
  }
  // blue light method use this to change LED
  public void StaticBlueStarLights() {

    for (var i = 0; i < kLedLength; i++) {
      setBlue(i);
    }
  }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
