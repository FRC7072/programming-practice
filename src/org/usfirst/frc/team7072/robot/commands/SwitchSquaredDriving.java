/**
 * 
 */
package org.usfirst.frc.team7072.robot.commands;

import org.usfirst.frc.team7072.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SwitchSquaredDriving extends Command {
	
	@Override
	protected void initialize() {
		super.initialize();
		requires(Robot.driveTrain);
	}
	
	@Override
	protected void execute() {
		super.execute();
		Robot.driveTrain.switchSquaredSpeed();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
