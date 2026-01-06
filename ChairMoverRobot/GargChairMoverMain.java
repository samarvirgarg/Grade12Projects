package unit_2_robot;

import becker.robots.*;
import java.util.*;


/**
 * Program that allows robot to pick chairs from the cafeteria of different dimensions and put them in a storage room
 * @author Samarvir Garg
 * @version April 10th, 2025
 */
public class GargChairMoverMain {

	/**
	 * Main method that creates the whole cafeteria and allows the robot the clean it
	 */
	public static void main(String[] args) {
		
		// Final constants that have some meaning in the program
		final int CAFETERIA_HEIGHT= 4;
		final int CAFETERIA_WIDTH= 7;
		final int MAX_STACK_CHAIRS = 10; // Also used in template class. Need to changes this at both places if needed
		final int STORAGE_DIST_FROM_CAF = 3; // should be 2 or more
		final int CAFETERIA_START_STREET=1;
		final int CAFETERIA_START_AVENUE=1;
		
		City cafeteria = new City();
		cafeteria.showThingCounts(true);
		Random generator = new Random();
		int randomDoor = generator.nextInt(CAFETERIA_WIDTH);
		
		// Creates east and west wall boundary of cafeteria
		for(int i=0;i<CAFETERIA_HEIGHT;i++) {
			Wall wall = new Wall(cafeteria,i+CAFETERIA_START_STREET,CAFETERIA_START_AVENUE,Direction.WEST);
			Wall wall2 = new Wall(cafeteria,i+CAFETERIA_START_STREET,CAFETERIA_WIDTH-1+CAFETERIA_START_AVENUE,Direction.EAST);
		}
		
		// Creates North and South wall boundary of cafeteria and the wall for storage room
		for(int i=0;i<CAFETERIA_WIDTH;i++) {
			Wall wall = new Wall(cafeteria,CAFETERIA_START_STREET,i+CAFETERIA_START_AVENUE,Direction.NORTH);
			Wall wall2 = new Wall(cafeteria,CAFETERIA_HEIGHT-1+STORAGE_DIST_FROM_CAF+CAFETERIA_START_STREET,i+CAFETERIA_START_AVENUE,Direction.SOUTH);
			
			// Creates a random door to exit cafeteria at the south wall boundary of cafeteria by not adding a wall
			if(i!=randomDoor) {
				Wall wall3 = new Wall(cafeteria,CAFETERIA_HEIGHT-1+CAFETERIA_START_STREET,i+CAFETERIA_START_AVENUE,Direction.SOUTH);
			}
		}
		
		// Creating storage room east and west boundary
		Wall wall = new Wall(cafeteria,CAFETERIA_HEIGHT-1+STORAGE_DIST_FROM_CAF+CAFETERIA_START_STREET,CAFETERIA_START_AVENUE,Direction.WEST);
		Wall wall2 = new Wall(cafeteria,CAFETERIA_HEIGHT-1+STORAGE_DIST_FROM_CAF+CAFETERIA_START_STREET,CAFETERIA_WIDTH-1+CAFETERIA_START_AVENUE,Direction.EAST);

		int numChairs = generator.nextInt(CAFETERIA_WIDTH*MAX_STACK_CHAIRS)+1;
		
		// Creates chairs at random locations inside the cafeteria
		for(int i=1;i<=numChairs;i++) {
			int y = generator.nextInt(CAFETERIA_HEIGHT)+CAFETERIA_START_STREET;
			int x = generator.nextInt(CAFETERIA_WIDTH)+CAFETERIA_START_AVENUE;
			Thing chair = new Thing(cafeteria,y,x);
		}

		// Making the robot spawn at random location and direction inside the cafeteria
		int y = generator.nextInt(CAFETERIA_HEIGHT)+CAFETERIA_START_STREET;
		int x = generator.nextInt(CAFETERIA_WIDTH)+CAFETERIA_START_AVENUE;
		Direction [] directions = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
		Direction randomDirection = directions[generator.nextInt(directions.length)];
		GargChairMoverRobot karel = new GargChairMoverRobot(cafeteria,y,x,randomDirection);
		
		karel.moveChairs();
	}
}
