package org.usfirst.frc.team7072.robot.subsystems;

import java.awt.peer.LightweightPeer;

import org.usfirst.frc.team7072.robot.RobotMap;
import org.usfirst.frc.team7072.robot.commands.DriveWithJoystick;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {
	
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
		drive = new DifferentialDrive(leftMotors, rightMotors);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
		SmartDashboard.putBoolean("Squared Speed", squaredSpeed);
	}
	
	public void switchSquaredSpeed() {
		squaredSpeed = !squaredSpeed;		
		SmartDashboard.putBoolean("Squared Speed", squaredSpeed);
	}
	
	public void tankDrive(Joystick controller) {
		// TODO Figure out the actual axis value for logitech controller
		tankDrive(controller.getRawAxis(1), controller.getRawAxis(5));
	}
	
	public void tankDrive(double leftValue, double rightValue) {
		drive.tankDrive(leftValue, rightValue, squaredSpeed);
	}
	
	public void stop() {
		drive.tankDrive(0, 0);
	}
}
