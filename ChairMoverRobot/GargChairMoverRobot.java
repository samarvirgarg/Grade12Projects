package unit_2_robot;

import becker.robots.*;

/**
 * Allows the robot to clean cafeteria by picking all chairs and keeping them in the storage room
 * @author Samarvir Garg
 * @version April 10th, 2025
 */
public class GargChairMoverRobot extends RobotSE {

	// Final constants that have some meaning in the program
	private final int MAX_STACK_CHAIRS = 10;
	private boolean path1 = true;

	/**
	 * Constructor method that allows the robot to get access to template class
	 * @param c: The city in which the cafeteria is located
	 * @param avenue: The avenue in the cafeteria where the robot starts
	 * @param street: The street in the cafeteria where the robot starts
	 * @param d: Direction in which the robot spawns in the cafeteria
	 */
	public GargChairMoverRobot(City c, int avenue, int street, Direction d) {
		super(c, avenue, street, d);
	}

	/**
	 * Allows the robot to turn east and check for a chair at the starting point and then start moving in the cafeteria
	 */
	public void moveChairs() {
		this.turnEastDirection();
		this.moveIfChair();
		this.moveInCafeteria(); 
	}

	/**
	 * Allows the robot to move inside the cafeteria and pick up chair in its way
	 */
	private void moveInCafeteria() {

		//Allows to robot to move and clear it path till it reaches the top right corner
		while(this.cleanTillTopRightCorner()==false) { 
			this.moveIfChair();
		}
		path1=false;

		//allows the robot to move right or left inside the cafeteria until it cleans everything
		while(this.moveLeftRightInCafeteria()==false) {
			this.moveIfChairPath2();
		}
		
		this.goToEndPoint();
	}

	/**
	 *Allows the robot to reach the final end point after the cafeteria is cleaned
	 */
	private void goToEndPoint() {
		this.turnEastDirection();
		this.findDoor();
		this.goToRestingPoint();
	}

	/**
	 * Allows the robot to find the last stack of chairs and reach the end point
	 */
	private void goToRestingPoint() {
		this.moveUntilWall();
		this.turnLeft();
		this.moveUntilWall();
		this.turnAround();

		// Move the robot at the intersection where the last stack of chairs is placed
		while(!this.canPickThing()) {
			
			
			// also check wall. 
			// Program crashes out when there is zero chairs in cafeteria
			
			
			
			this.move();
		}
		this.turnRight();
		this.move();
	}

	/**
	 * Allows the robot to check if there is a chair at an intersection and keep it in the storage
	 */
	private void moveIfChair() {

		// Checks if there is a chair at an intersection
		if(this.checkChair()) {
			this.moveChairToStorage();
		}
	}

	/**
	 * Allows the robot to check if there is a chair at an intersection and keep it in the storage
	 */
	private void moveIfChairPath2() {

		// Checks if there is a chair at an intersection and allows the robot to move to the top left corner after keeping the chair in the storage room.
		if(this.checkChair()) {
			this.moveChairToStorage();
			this.moveToTopRightCorner();
		}
	}

	/**
	 * Allows the robot to move right or left within the cafeteria 
	 * @return : Returns true or false whether is has moved everywhere in the cafeteria
	 */
	private boolean moveLeftRightInCafeteria(){
		boolean isEndPoint = false;

		// Allows the robot to keep moving right or left if there is no wall in the front
		if((this.getDirection()==Direction.EAST || this.getDirection()==Direction.WEST) && this.frontIsClear()) {
			this.move();
		}

		// Allows the robot to change direction its direction in the column below if it has reached the west end of its current column
		else if(this.getDirection()==Direction.WEST && this.frontIsClear()!=true) {
			this.turnLeft();

			// Checks if this point is the last point in cafeteria.
			if(!this.frontIsClear()) {
				isEndPoint=true;
			}
			else {
				this.move();
				// Checks if there is a door of cafeteria at that point
				if(!this.checkRight()) {
					this.turnLeft();
				}
				else {
					this.turnAround();
					this.move();
					isEndPoint=true;
				}
			}
		}

		// Allows the robot to change direction its direction in the column below if it has reached the east end of its current column
		else if(this.getDirection()==Direction.EAST && this.frontIsClear()!=true) {
			this.turnRight();

			// Checks if this point is the last point in cafeteria.
			if(!this.frontIsClear()) {
				isEndPoint=true;
			}
			else {
				this.move();
				// Checks if there is a door of cafeteria at that point
				if(!this.checkLeft()) {
					this.turnRight();
				}
				else {
					this.turnAround();
					this.move();
					isEndPoint=true;
				}
			}
		}
		return isEndPoint;
	}

	/**
	 * Allows the robot to move to the top right corner until that path is clean.
	 * @return: Returns true or false whether the robot has reached the top right corner
	 */
	private boolean cleanTillTopRightCorner() {
		boolean condition = false;

		// Checks if robot's direction is east and the front is clear
		if(this.getDirection()==Direction.EAST && this.frontIsClear()) {
			this.move();
		}
		// Checks if robot's direction is east and the front is not clear
		else if(this.getDirection()==Direction.EAST && this.frontIsClear()!=true) {
			this.turnLeft();
		}
		// Checks if robot's direction is north and the front is clear
		else if(this.getDirection()==Direction.NORTH && this.frontIsClear()) {
			this.move();
		}
		// Checks if robot's direction is north and the front is not clear
		else if(this.getDirection()==Direction.NORTH && this.frontIsClear()!=true) {
			this.turnLeft();
			condition = true;
		}
		return condition;
	}

	/**
	 * Allows the robot to move to the top right corner
	 */
	private void moveToTopRightCorner() {
		this.turnEastDirection();
		this.moveUntilWall();
		this.turnLeft();
		this.moveUntilWall();
		this.turnLeft();
	}

	/**
	 * Allows the robot to find the way to the storage, keep the chair and come back to the cafeteria
	 */
	private void moveChairToStorage() {
		this.turnEastDirection();
		this.findDoor();
		this.putChairInStorage();
		this.comeBackToDoor();
	}

	/**
	 * Allows the robot to find the door in the cafeteria
	 */
	private void findDoor() {
		this.moveUntilWall();
		this.turnRight();
		boolean condition = true;

		// Allows the robot to move until it finds a wall while also checking for corner door
		while(this.frontIsClear()) {
			this.move();

			// Checks it the robot has come out of cafeteria
			if(this.checkLeft()) {
				condition=false;
				break;
			}
		}

		// Checks if the robot is still inside cafeteria
		if(condition==true)
			this.turnRight();

		// Allows the robot to move until it finds the door on the left
		while(condition) {
			this.move();

			// Checks if there is a door on left side of robot
			if(this.checkLeft()) {
				this.turnLeft();
				break;
			}
		}
	}

	/**
	 * Allows the robot to go to the storage room and put the chair on the existing stacks
	 */
	private void putChairInStorage() {
		this.moveUntilWall();
		this.turnRight();
		this.moveUntilWall();
		this.turnAround();

		// Allows the robot to move until it finds a stack that has less MAX_STACK_CHAIRS chairs and put the chair there
		while(this.countThingsInBackpack()!=0) {

			// Checks if the number of chairs in the stack exceeds limit
			if(this.countThings()<MAX_STACK_CHAIRS)
				this.putThing();
			else
				this.move();
		}
	}
	
	/**
	 * Allows the robot to come back the the door of cafeteria after it has kept the chair
	 */
	private void comeBackToDoor() {
		this.moveUntilWall();
		this.turnLeft();
		boolean condition= true;

		// Allows the robot to move until it reaches the south wall boundary of cafeteria
		while(this.frontIsClear()) {
			this.move();

			// Checks it robot has entered the cafeteria from any corner door
			if(!this.checkRight()) {
				condition=false;
				break;
			}
		}

		// Checks if there is no corner door in cafeteria
		if(condition==true)
			this.turnLeft();

		// Allows the robot to move until it finds the door of cafeteria
		while(condition) {
			this.move();

			// Checks if there is door on the right side of robot
			if(this.checkRight()) {
				this.turnRight();
				break;
			}
		}

		// Allows robot to move inside the cafeteria
		if(condition==true) {
			this.move();
			this.turnRight();
		}
		
		this.moveIfChair();
		
		// Checks if Robot is moving the first path or the second
		if(!path1)
			this.moveToTopRightCorner();
	}

	/**
	 * Methods that allows to count the number of chairs at an intersection
	 * @return: Return the number of chairs at in intersection
	 */
	private int countThings() {
		int count = 0;

		// counting the number of things at the intersection
		while(this.canPickThing()) {
			count++;
			this.pickThing();
		}

		// Checking if count is zero
		if(count==0) {
			return count;
		}
		// putting the things back the returning the value
		else {
			for(int i=1;i<=count;i++) {
				this.putThing();
			}
			return count;
		}
	}

	/**
	 * Method to check if there a wall on the left side
	 * @return: return true or false whether there is a wall on left side
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
	
	/**
	 * Method to check if there a wall on the right side
	 * @return: return true or false whether there is a wall on right side
	 */
	private boolean checkRight() {
		boolean condition = false;
		this.turnRight();

		// Checks if the front is clear after turning a right
		if(this.frontIsClear())
			condition=true;
		this.turnLeft();
		return condition;
	}

	/**
	 * Checks if there is a chair at the intersection where the robot is
	 * @return : Return true or false whether the there is a chair at in intersection where robot is
	 */
	private boolean checkChair() {
		boolean condition = false;

		// Checks if there is a chair at an intersection
		if(this.canPickThing()) {
			condition = true;
			this.pickThing();
		}
		return condition;
	}

	/**
	 * Allows the robot to turn in East direction.
	 */
	private void turnEastDirection() {
		Direction direction = this.getDirection();

		// Checks if the robot is in North direction
		if(direction==Direction.NORTH) {
			this.turnRight();
		}

		// Checks if the robot is in West direction
		else if(direction==Direction.WEST) {
			this.turnAround();
		}

		// Checks if the robot is in South direction
		else if(direction==Direction.SOUTH) {
			this.turnLeft();
		}
	}
	
	/**
	 * Allows the robot to move until it is stopped by a wall
	 */
	private void moveUntilWall() {

		// Moves the robot until it finds a wall
		while(this.frontIsClear()) {
			this.move();
		}
	}
}



