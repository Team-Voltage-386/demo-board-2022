
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

public class LEDSubsystem extends SubsystemBase {
     //LED initialization 
     AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(kLedLength);
     int m_rainbowFirstPixelHue;
     AddressableLED m_led = new AddressableLED(9);
    public LEDSubsystem(){
        m_led.setLength(m_ledBuffer.getLength());
         m_led.setData(m_ledBuffer);
         m_led.start();
    }

    // Rainbow Lights - not changing
    public void StaticRainbowLights(int ballCount, boolean rob) {
        if (ballCount==1){
            int distance = 180 / (kLedLength);

            for (var i = 0; i < kLedLength; i++) {
            m_ledBuffer.setHSV(i, i * distance, 255, 255);
            }
            m_led.setData(m_ledBuffer);
        }else if(ballCount==2){
            if(rob==true){
                for (var i = kLedLength/2; i < kLedLength; i++) {
                    setRed(i);
                }
                m_led.setData(m_ledBuffer);
                int distance = 180 / (kLedLength/2);

                for (var i = 0; i < kLedLength/2; i++) {
                     m_ledBuffer.setHSV(i, i * distance, 255, 255);
                }
                m_led.setData(m_ledBuffer);
            }else{
                for (var i = kLedLength/2; i < kLedLength; i++) {
                    setBlue(i);
                }
                m_led.setData(m_ledBuffer);
                int distance = 180 / (kLedLength/2);

                for (var i = 0; i < kLedLength/2; i++) {
                     m_ledBuffer.setHSV(i, i * distance, 255, 255);
                }
                m_led.setData(m_ledBuffer);
            }
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
    public void StaticRedStarLights(int ballCount, boolean fbb) {
        if(ballCount==0){
            for (var i = kLedLength/2; i < kLedLength; i++) {
                setRed(i);
            }
            m_led.setData(m_ledBuffer);
            int distance = 180 / (kLedLength/2);
            for (var i = 0; i < kLedLength/2; i++) {
                 m_ledBuffer.setHSV(i, i * distance, 255, 255);
            }
            m_led.setData(m_ledBuffer);
        }else if(ballCount==1){
            if(fbb=false){
                for (var i = 0; i < kLedLength; i++) {
                    setRed(i);
                }
                m_led.setData(m_ledBuffer);
            }else{
                for (var i = 0; i < kLedLength/2; i++) {
                    setRed(i);
                }
                m_led.setData(m_ledBuffer);
                for (var i = kLedLength/2; i < kLedLength; i++) {
                    setBlue(i);
                }
                m_led.setData(m_ledBuffer);
            }
        }
    }

    // blue light method use this to change LED
    public void StaticBlueStarLights(int ballCount, boolean fbb) {

        if(ballCount==0){
            for (var i = kLedLength/2; i < kLedLength; i++) {
            setBlue(i);
            }
            m_led.setData(m_ledBuffer);
            int distance = 180 / (kLedLength/2);
            for (var i = 0; i < kLedLength/2; i++) {
                 m_ledBuffer.setHSV(i, i * distance, 255, 255);
            }
            m_led.setData(m_ledBuffer);
        }else if(ballCount==1){
            if(fbb=true){
                for (var i = 0; i < kLedLength; i++) {
                    setBlue(i);
                }
                m_led.setData(m_ledBuffer);
            }else{
                for (var i = 0; i < kLedLength/2; i++) {
                    setBlue(i);
                }
                m_led.setData(m_ledBuffer);
                for (var i = kLedLength/2; i < kLedLength; i++) {
                    setRed(i);
                }
                m_led.setData(m_ledBuffer);
            }
        }
    }
}
