package day12_navigation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/* Link to Problem: https://adventofcode.com/2020/day/12 */

/**
 * This class hold the ship position in (x, y)
 * facing direction for part 1 (N = 0 | E = 90 | S = 180 | W = 270) and
 * waypoint position relative to the ship for part 2
 * also corresponding method for each part
 */
class Ship
{
	int x_pos;
	int y_pos;
	int facing;

	int x_wp_relative;
	int y_wp_relative;

	Ship(int x_pos, int y_pos)
	{
		this.x_pos = x_pos;
		this.y_pos = y_pos;
	}

	public void setFacing(int facing) {
		this.facing = facing;
	}

	public void setWaypoint(int x_waypoint, int y_waypoint) {
		this.x_wp_relative = x_waypoint;
		this.y_wp_relative = y_waypoint;
	}

	/* --------- PART 1 - FACING --------- */
	/**
	 * based on facing value of the ship currently , calculate the new (x,y) position of the ship
	 * @param value how many value is added to the position
	 */
	public void moveForward(int value) {
		switch (facing) {
			case 0 -> y_pos += value;		/* currently facing north */
			case 90 -> x_pos += value;		/* currently facing east */
			case 180 -> y_pos -= value;		/* currently facing south */
			case 270 -> x_pos -= value;		/* currently facing west */
			default -> throw new IllegalStateException("Invalid facing: " + facing);
		}
	}

	/**
	 * turn the facing value of the ship
	 * @param direction left or right
	 * @param degrees angle of the turn
	 */
	public void turnLR(char direction, int degrees) {
		if (direction == 'R')
			facing = (facing + degrees) % 360;
		if (direction == 'L')				/* turning left by X degrees is turning right by 360 - X degrees*/
			facing = (facing + 360 - degrees) % 360;
	}

	/**
	 * move the ship position to certain direction without changing the facing value
	 * @param compass which way the ship is moved
	 * @param value how many value is added to the position
	 */
	public void moveShipDirection(char compass, int value) {
		switch (compass) {
			case 'N' -> y_pos += value;		/* move north */
			case 'E' -> x_pos += value;		/* move east */
			case 'S' -> y_pos -= value;		/* move south */
			case 'W' -> x_pos -= value;		/* move west */
			default -> throw new IllegalStateException("Invalid compass direction: " + compass);
		}
	}

	/* --------- PART 2 - WAYPOINT --------- */
	/**
	 * change / move the waypoint position
	 * @param compass which way the waypoint is moved
	 * @param value how many value is added to the position
	 */
	public void moveWaypoint(char compass, int value) {
		switch (compass) {
			case 'N' -> y_wp_relative += value;		/* move north */
			case 'E' -> x_wp_relative += value;		/* move east */
			case 'S' -> y_wp_relative -= value;		/* move south */
			case 'W' -> x_wp_relative -= value;		/* move west */
			default -> throw new IllegalStateException("Invalid compass direction: " + compass);
		}
	}

	/**
	 * rotate the waypoint
	 * reference: https://stackoverflow.com/a/25196651
	 * @param direction left or right
	 * @param degrees angle of the rotation
	 */
	public void rotateWaypoint(char direction, int degrees) {
		/* convert Left rotation to Right rotation */
		int degreesRight = degrees;
		if (direction == 'L')
			degreesRight = 360 - degrees;
		/* turn angle to radian */
		double radianDegree = (degreesRight * Math.PI) / 180;
		/* cast to int since we only deal with 90 degrees angle multiplier (90, 180, 270)*/
		int sinA = (int)Math.sin(radianDegree);
		int cosA = (int)Math.cos(radianDegree);

		/* rotate relative (pivot point 0,0) */
		int x_wp_new = x_wp_relative * cosA + y_wp_relative * sinA;
		int y_wp_new = - x_wp_relative * sinA + y_wp_relative * cosA;

		/* change waypoint relative's value to the new rotated one */
		x_wp_relative = x_wp_new;
		y_wp_relative = y_wp_new;
	}

	/**
	 * move the ship position
	 * @param value how many times the ship should be moved based on current waypoint
	 */
	public void moveForwardWithWaypoint(int value) {
		this.x_pos = x_pos + (value * x_wp_relative);
		this.y_pos = y_pos + (value * y_wp_relative);
	}
}

/**
 * This class read the input file and plot the ship
 */
public class NavigationPlotter
{
	/**
	 * read input file
	 * @return each line collected in a list of string
	 */
	private List<String> readInputFile() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("day12_navigation/input.txt");
		assert is != null;
		return new BufferedReader(new InputStreamReader(is))
				.lines()
				.collect(Collectors.toList());
	}

	/**
	 * Part 1 - facing related task
	 * @param instructions list of instruction read
	 * @param ship ship to be moved based on the instruction list
	 */
	private void plotShipCoursep1(List<String> instructions, Ship ship) {
		for (String ins : instructions) {
			switch (ins.charAt(0)) {
				case 'N' -> ship.moveShipDirection('N', Integer.parseInt(ins.substring(1)));
				case 'E' -> ship.moveShipDirection('E', Integer.parseInt(ins.substring(1)));
				case 'S' -> ship.moveShipDirection('S', Integer.parseInt(ins.substring(1)));
				case 'W' -> ship.moveShipDirection('W', Integer.parseInt(ins.substring(1)));
				case 'L' -> ship.turnLR('L', Integer.parseInt(ins.substring(1)));
				case 'R' -> ship.turnLR('R', Integer.parseInt(ins.substring(1)));
				case 'F' -> ship.moveForward(Integer.parseInt(ins.substring(1)));
			}
		}
	}

	/**
	 * Part 2 - waypoint related task
	 * @param instructions list of instruction read
	 * @param ship ship to be moved based on the instruction list
	 */
	private void plotShipCoursep2(List<String> instructions, Ship ship) {
		for (String ins : instructions) {
			switch (ins.charAt(0)) {
				case 'N' -> ship.moveWaypoint('N', Integer.parseInt(ins.substring(1)));
				case 'E' -> ship.moveWaypoint('E', Integer.parseInt(ins.substring(1)));
				case 'S' -> ship.moveWaypoint('S', Integer.parseInt(ins.substring(1)));
				case 'W' -> ship.moveWaypoint('W', Integer.parseInt(ins.substring(1)));
				case 'L' -> ship.rotateWaypoint('L', Integer.parseInt(ins.substring(1)));
				case 'R' -> ship.rotateWaypoint('R', Integer.parseInt(ins.substring(1)));
				case 'F' -> ship.moveForwardWithWaypoint(Integer.parseInt(ins.substring(1)));
			}
		}
	}

	public static void main(String[] args)
	{
		NavigationPlotter navigationPlotter = new NavigationPlotter();
		List<String> instructions = navigationPlotter.readInputFile();

		/* part 1, facing related task */
		Ship shipPart1 = new Ship(0, 0);
		shipPart1.setFacing(90); /* facing East -> 90 degrees */
		navigationPlotter.plotShipCoursep1(instructions, shipPart1);
		System.out.println("Part 1 position: " + shipPart1.x_pos + ", " + shipPart1.y_pos);

		/* part 2, waypoint related task */
		Ship shipPart2 = new Ship(0, 0);
		shipPart2.setWaypoint(10, 1); /* waypoint 10 East, 1 North */
		navigationPlotter.plotShipCoursep2(instructions, shipPart2);
		System.out.println("Part 2 position: " + shipPart2.x_pos + ", " + shipPart2.y_pos);
	}
}
