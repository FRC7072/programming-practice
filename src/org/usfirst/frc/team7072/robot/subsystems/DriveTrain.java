package org.usfirst.frc.team7072.robot.subsystems;

import java.rmi.server.SkeletonNotFoundException;

import org.usfirst.frc.team7072.robot.RobotMap;
import org.usfirst.frc.team7072.robot.commands.DriveWithJoystick;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	
	public CANTalon leftFrontMotor = new CANTalon(RobotMap.leftFrontMotor);
	public CANTalon leftBackMotor = new CANTalon(RobotMap.leftBackMotor);
	public CANTalon rightFrontMotor = new CANTalon(RobotMap.rightFrontMotor);
	public CANTalon rightBackMotor = new CANTalon(RobotMap.rightBackMotor);
	
	//Need both front and back drives working together since there are two motor on
	// each side powering the same wheels.
	private RobotDrive frontDrive;
	private RobotDrive backDrive;
	
	public DriveTrain() {
		// TODO Auto-generated constructor stub
		frontDrive = new RobotDrive(leftFrontMotor, rightFrontMotor);
		backDrive = new RobotDrive(leftBackMotor, rightBackMotor);
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
		frontDrive.tankDrive(leftValue, rightValue, true);
		backDrive.tankDrive(leftValue, rightValue, true);
	}
	
	public void stop() {
		frontDrive.tankDrive(0, 0);
		backDrive.tankDrive(0, 0);
	}
}
