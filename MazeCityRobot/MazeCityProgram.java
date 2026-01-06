package unit_2_robot;

import becker.robots.*;
import java.util.*;

/**
 * Robot finding out the end point to end out of the maze
 * @author Samarvir
 * @version April 17th, 2025
 */
public class MazeCityProgram {

	public static void main(String[] args) {
		
		final int MAZE_SIZE_X = 10;
		final int MAZE_SIZE_Y = 10;
		
		Random generator = new Random();
		MazeCity maze = new MazeCity(MAZE_SIZE_X,MAZE_SIZE_Y);
		
		int robotX = generator.nextInt(MAZE_SIZE_X);
		int robotY = generator.nextInt(MAZE_SIZE_Y);
		MazeCityRobot karel = new MazeCityRobot(maze,robotX,robotY,Direction.EAST);
		
		int endX = generator.nextInt(MAZE_SIZE_X);
		int endY = generator.nextInt(MAZE_SIZE_Y);
		Thing endPoint = new Thing(maze,endX,endY);
		
		karel.run();
	}
}
