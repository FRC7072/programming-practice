package org.usfirst.frc.team7072.robot.subsystems;

import org.usfirst.frc.team7072.robot.RobotMap;
import org.usfirst.frc.team7072.robot.commands.DriveWithJoystick;

import edu.wpi.first.wpilibj.PWMTalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends Subsystem {
	
	public PWMTalonSRX leftFrontMotor = new PWMTalonSRX(RobotMap.leftFrontMotor);
	public PWMTalonSRX leftBackMotor = new PWMTalonSRX(RobotMap.leftBackMotor);
	public PWMTalonSRX rightFrontMotor = new PWMTalonSRX(RobotMap.rightFrontMotor);
	public PWMTalonSRX rightBackMotor = new PWMTalonSRX(RobotMap.rightBackMotor);
	
	public SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftFrontMotor, leftBackMotor);
	public SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightFrontMotor, rightBackMotor);
	
	//Need both front and back drives working together since there are two motor on
	// each side powering the same wheels.
	private DifferentialDrive drive;
	
	public DriveTrain() {
		drive = new DifferentialDrive(leftMotors, rightMotors);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}
	
	public void tankDrive(Joystick controller) {
		// TODO Figure out the actual axis value for logitech controller
		tankDrive(controller.getY(), controller.getRawAxis(4));
	}
	
	public void tankDrive(double leftValue, double rightValue) {
		drive.tankDrive(leftValue, rightValue, true);
	}
	
	public void stop() {
		drive.tankDrive(0, 0);
	}
}
