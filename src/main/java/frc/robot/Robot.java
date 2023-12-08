// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.SensorVelocityMeasPeriod;

import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private TalonFX[] falcons;

  private CANCoder[] canCoders;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit(){
    this.falcons = new TalonFX[4];
    for (int i = 0; i < 4; i++)
    {
      this.falcons[i] = new TalonFX(i + 1, "CANIVORE1");
      this.falcons[i].setNeutralMode(NeutralMode.Brake);
      this.falcons[i].configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
      this.falcons[i].setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10, 10);
      this.falcons[i].setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 100, 10);
      this.falcons[i].setInverted(TalonFXInvertType.CounterClockwise);
      this.falcons[i].configVelocityMeasurementPeriod(SensorVelocityMeasPeriod.valueOf(10), 10);
      this.falcons[i].configVelocityMeasurementWindow(32, 10);
      this.falcons[i].config_kP(0, 0.1, 10);
      this.falcons[i].config_kI(0, 0.0, 10);
      this.falcons[i].config_kD(0, 0.0, 10);
      this.falcons[i].config_kF(0, 0.05115, 10);
      this.falcons[i].configVoltageCompSaturation(11.0);
      this.falcons[i].enableVoltageCompensation(true);
      this.falcons[i].configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 35, 35, 0.25));
      this.falcons[i].selectProfileSlot(0, 0);
    }

    this.canCoders = new CANCoder[4];
    for (int i = 0; i < 4; i++)
    {
      this.canCoders[i] = new CANCoder(i + 1, "CANIVORE1");
      this.canCoders[i].configAbsoluteSensorRange(AbsoluteSensorRange.Unsigned_0_to_360, 10);
    }
  }

  @Override
  public void robotPeriodic() { }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    for (int i = 0; i < 4; i++)
    {
      double absPos = this.canCoders[i].getAbsolutePosition();
      double pos = this.falcons[i].getSelectedSensorPosition(0);
      double err = this.falcons[i].getClosedLoopError(0);
      double vel = this.falcons[i].getSelectedSensorVelocity(0);
      // System.out.println("" + absPos + "," + pos + "," + err + "," + vel);
      this.falcons[i].set(ControlMode.Velocity, 4000);
    }
  }

  @Override
  public void disabledInit() {
    for (int i = 0; i < 4; i++)
    {
      this.falcons[i].set(ControlMode.Disabled, 0.0);
    }
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
