// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class USBCam extends SubsystemBase {
  Thread m_visionThread;
  CvSink cvSink;
  CvSource outputStream;
  Mat mat;
  // shuffleboard tab
  ShuffleboardTab _tab = Shuffleboard.getTab("Vision");
  // shuffleboard widgets
  ComplexWidget _visionWidget = _tab.add(outputStream).withPosition(0, 0).withSize(4, 4);

  /** Creates a new USBCam. */
  public USBCam() {
    // Get the UsbCamera from CameraServer
    UsbCamera camera = CameraServer.startAutomaticCapture();
    // Set the resolution
    camera.setResolution(640, 480);
    // Get a CvSink. This will capture Mats from the camera
    cvSink = CameraServer.getVideo();
    // Setup a CvSource. This will send images back to the Dashboard
    outputStream = CameraServer.putVideo("Rectangle", 640, 480);
    // Mats are very memory expensive. Lets reuse this Mat.
    mat = new Mat();
  }

  @Override
  public void periodic() {
    // Put a rectangle on the image
    Imgproc.rectangle(
        mat, new Point(100, 100), new Point(400, 400), new Scalar(255, 255, 255), 5);
    // Give the output stream a new image to display
    outputStream.putFrame(mat);
  }
}
