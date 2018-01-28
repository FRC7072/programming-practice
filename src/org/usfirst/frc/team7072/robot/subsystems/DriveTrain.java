package org.usfirst.frc.team7072.robot.subsystems;

import org.usfirst.frc.team7072.robot.RobotMap;
import org.usfirst.frc.team7072.robot.commands.DriveWithJoystick;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends PIDSubsystem {
	private static final double ENCODER_UNIT_PER_ROTATION = 1/4096;
	private static final double WHEEL_DIAMETER = 4.0; //inches
	public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
	
	public enum PIDInputType {
		encoder_position,
		encoder_velocity
	}
	
	public PIDInputType inputType;
	
	public WPI_TalonSRX leftFrontMotor = new WPI_TalonSRX(RobotMap.leftFrontMotor);
	public WPI_TalonSRX leftBackMotor = new WPI_TalonSRX(RobotMap.leftBackMotor);
	public WPI_TalonSRX rightFrontMotor = new WPI_TalonSRX(RobotMap.rightFrontMotor);
	public WPI_TalonSRX rightBackMotor = new WPI_TalonSRX(RobotMap.rightBackMotor);
	
	public SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftFrontMotor, leftBackMotor);
	public SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightFrontMotor, rightBackMotor);
	
	private boolean squaredSpeed = true;
	
	//Need both front and back drives working together since there are two motor on
	// each side powering the same wheels.
	private DifferentialDrive drive;
	
	public DriveTrain() {
		super("Drive Train", 0.0, 0.0, 0.0);

		configureTalons();
		
		drive = new DifferentialDrive(leftMotors, rightMotors);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}
	
	private void configureTalons() {
		leftFrontMotor.configClosedloopRamp(2, 0);
		leftBackMotor.configClosedloopRamp(2, 0);
		rightBackMotor.configClosedloopRamp(2, 0);
		rightFrontMotor.configClosedloopRamp(2, 0);
		
		leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	}
	
	public double getLeftEncoderRotation() {
		double rawValue = leftFrontMotor.getSelectedSensorPosition(0);
		
		return rawValue * ENCODER_UNIT_PER_ROTATION;
	}
	
	public double getRightEncoderRotation() {
		double rawValue = rightFrontMotor.getSelectedSensorPosition(0);
		
		return rawValue * ENCODER_UNIT_PER_ROTATION;
	}
	
	public double getLeftEncoderVelocity() {
		double rawValue = leftFrontMotor.getSelectedSensorVelocity(0);
		double rotationPerSecond = rawValue / ENCODER_UNIT_PER_ROTATION;
		
		return rotationPerSecond;
	}
	
	public double getRightEncoderVelocity() {
		double rawValue = rightFrontMotor.getSelectedSensorVelocity(0);
		double rotationPerSecond = rawValue / ENCODER_UNIT_PER_ROTATION;
		
		return rotationPerSecond;
	}
	
	public void switchSquaredSpeed() {
		squaredSpeed = !squaredSpeed;
	}
	
	public void tankDrive(double leftValue, double rightValue) {
		drive.tankDrive(leftValue, rightValue, squaredSpeed);
	}
	
	public void stop() {
		drive.tankDrive(0, 0);
	}

	@Override
	protected double returnPIDInput() {
		switch (inputType) {
		case encoder_position:
			return getRightEncoderRotation();
		case encoder_velocity:
			return getRightEncoderVelocity();
		default:
			return 0;
		}
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		leftFrontMotor.set(output);
		rightFrontMotor.set(-output);
		leftBackMotor.follow(leftFrontMotor);
		rightBackMotor.follow(rightFrontMotor);
	}
}
