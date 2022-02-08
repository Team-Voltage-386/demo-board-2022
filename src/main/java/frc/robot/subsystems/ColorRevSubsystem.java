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
import frc.robot.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj.Timer;

public class ColorRevSubsystem extends SubsystemBase {
    private final LEDSubsystem m_LEDSubsystem = new LEDSubsystem();
    
    /*Creates Color Sensor Rev Subsystem*/
    I2C.Port demoSensorI2CPort = I2C.Port.kOnboard; // Port 0  
    /* sensor instantiations */
    private final ColorMatch m_colorMatcher = new ColorMatch();
    public final ColorSensorV3 m_demoColorSensor = new ColorSensorV3(demoSensorI2CPort);

    // Creates a Shuffleboard tab for the for Rev Color Detection
    private ShuffleboardTab tab = Shuffleboard.getTab("Ball Color");

    private Timer m_timer = new Timer();

    // Create sensor widgets
    private NetworkTableEntry isBlueOrRedWidget = tab.add("Blue or Red", "Red").withPosition(7, 3).withSize(2, 1).getEntry();
    
    //intializes Colors
    private final Color kBlueTarget = new Color(0.143, 0.427, 0.429);
    private final Color kRedTarget = new Color(0.361, 0.524, 0.113);
    private final Color kGreenTarget = new  Color(0.561, 0.232, 0.114);
    private final Color kYellowTarget = new Color(0.197, 0.561, 0.240);
    private boolean isBall= false;
    private boolean redOrBlue;
    private boolean shoot=false;
    private int ballCount=0;
    private int ballCountRed=0;
    private int ballCountBlue=0;
    private boolean firstBallBlue;
    private boolean onFirst;

    
    // constructs LED and ColorMatcher
    public ColorRevSubsystem(){
         m_colorMatcher.addColorMatch(kBlueTarget);
         m_colorMatcher.addColorMatch(kRedTarget);
         m_colorMatcher.addColorMatch(kYellowTarget);
         m_colorMatcher.addColorMatch(kGreenTarget);
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
        shoot=m_timer.advanceIfElapsed(20.0);
        // This method will be called once per scheduler run
        //colormatch logic
        Color detectedColor = m_demoColorSensor.getColor();
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
        if (match.color == kBlueTarget) {
            isBlueOrRedWidget.setString(getDemoColorSenseBlue());
            
            
            if(ballCount<2){
                ballCount++;
                isBall=true;
                redOrBlue=false;
                if(ballCount==0){
                    onFirst=true;
                    firstBallBlue=true;
                }
            }
        }else if (match.color == kRedTarget) {
            isBlueOrRedWidget.setString(getDemoColorSenseRed());
            if(ballCount<2){
                ballCount++;
                redOrBlue=false;
                isBall=true;
                if(ballCount==0){
                    onFirst=true;
                    firstBallBlue=false;
                }
            }
            
            
        }else if (match.color == kYellowTarget) {
            isBlueOrRedWidget.setString(getDemoColorSenseNull());
            isBall=false;
        }
        
        //call to method after booleans updated 
        ColorDisplay();
        shoot=false;
        onFirst=false;
    }    
    //LED update Method
    public void ColorDisplay(){
        if(shoot=true){
            ballCount--;
            m_LEDSubsystem.StaticRainbowLights(ballCount, redOrBlue);
        } else
        if(isBall==false){
            
        }else{
            if(redOrBlue==true){
                m_LEDSubsystem.StaticRedStarLights(ballCountRed, firstBallBlue);
                ballCountBlue++;
            }else{
                m_LEDSubsystem.StaticBlueStarLights(ballCountBlue, firstBallBlue);
                ballCountRed++;
            }
        }
        
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
 
  
  
  


    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
