package day11_seating;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SeatingSimulator
{
	private char[][] readInputFile() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("day11_seating/input.txt");
		assert is != null;
		return new BufferedReader(new InputStreamReader(is))
				.lines()
				.map(String::toCharArray)
				.toArray(char[][]::new);
	}

	public static void main(String[] args)
	{
		SeatingSimulator seatingSimulator = new SeatingSimulator();
		char[][] a = seatingSimulator.readInputFile();
		System.out.println();
	}
}
