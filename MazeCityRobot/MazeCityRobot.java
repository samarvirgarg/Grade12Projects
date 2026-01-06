package unit_2_robot;

import becker.robots.*;
import java.util.*;

/**
 * Template class for robot to find the end point to get out of maze
 * @author Samarvir
 * @version April 17th, 2025
 */

public class MazeCityRobot extends RobotSE {

	/**
	 * Constructor Method
	 * @param c : The maze city where the robot is trapped
	 * @param avenue: The avenue where robot starts find the way
	 * @param street: The street where robot starts find the way
	 * @param d: The direction in which the robot starts
	 */
	public MazeCityRobot(City c, int avenue, int street, Direction d) {
		super(c, avenue, street, d);
	}
	
	/**
	 * Method that allows robot to find the end point to get out of maze
	 */
	public void run() {
		//Checks if the robot reaches the final point
		while(!this.canPickThing()) {
			
			// Checks if the robot can turn left
			if(this.checkLeft())
				this.turnLeft();
			
			//Checks if robot can move in the front
			if(this.checkFront())
				this.move();
			else
				this.turnRight();
		}
	}
	
	/**
	 * Method to check if the robot the move in its same direction
	 * @return : Returns either yes or no if the robot can move in the front
	 */
	private boolean checkFront() {
		boolean condition = false;
		
		// Checks if the front is clear
		if(this.frontIsClear())
			condition=true;
		return condition;
	}
	
	/**
	 * Method to check if the robot the move in its same direction
	 * @return: Returns either yes or no if the robot can turn left
	 */
	private boolean checkLeft() {
		boolean condition = false;
		this.turnLeft();
		
		// Checks if the front is clear after turning a left
		if(this.frontIsClear())
			condition=true;
		this.turnRight();
		return condition;
	}
}



