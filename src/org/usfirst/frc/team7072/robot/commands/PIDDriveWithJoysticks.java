package org.usfirst.frc.team7072.robot.commands;

import org.usfirst.frc.team7072.robot.Robot;
import org.usfirst.frc.team7072.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class PIDDriveWithJoysticks extends PIDCommand {
	
	Joystick pilot = Robot.oi.getPilotController();
	
	public PIDDriveWithJoysticks() {
		super(0, 0, 0);
		
		requires(Robot.driveTrain);
	}
	
	@Override
	protected void initialize() {
		super.initialize();

		getPIDController().setPercentTolerance(15);
	}
	
	@Override
	protected void execute() {
		super.execute();
		
		setSetpoint(pilot.getRawAxis(RobotMap.leftJoystickYAxis)); // -1 to 1 rot/second
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected double returnPIDInput() {
		return RobotMap.leftJoystickYAxis;
	}

	@Override
	protected void usePIDOutput(double output) {
		double rotation = pilot.getRawAxis(RobotMap.rightJoystickXAxis);
		
		double leftSpeed = output + rotation;
		double rightSpeed = output - rotation;
		
		Robot.driveTrain.tankDrive(leftSpeed, rightSpeed);
	}
	
	@Override
	protected void end() {
		super.end();
		Robot.driveTrain.stop();
		getPIDController().free();
	}
	
	@Override
	public synchronized void cancel() {
		super.cancel();
		end();
	}
	
}
