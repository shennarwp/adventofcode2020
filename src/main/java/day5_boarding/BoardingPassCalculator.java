package day5_boarding;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/* Link to Problem: https://adventofcode.com/2020/day/5 */
public class BoardingPassCalculator
{
	/**
	 * read input file and then return each line as string
	 * @return List of each line read
	 */
	private List<String> readInputFile() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("day5_boarding/input.txt");
		assert is != null;
		return new BufferedReader(new InputStreamReader(is))
				.lines()
				.collect(Collectors.toList());
	}

	/**
	 * convert each boarding pass code to [seat row, seat column, seat id]
	 * @param boardingPass boarding pass code passed as string
	 * @return List containing Integers: seat row, seat column, seat id
	 */
	private static List<Integer> findSeat(String boardingPass) {
		/* calculate row */
		int seatRow = 0;
		char seatRowLastMove = 0;
		int minRow = 0;
		int maxRow = 127;
		/* first 6 characters of boarding pass */
		for (int i = 0; i < 7; i++) {
			char a = boardingPass.charAt(i);
			/* if 6th characters, mark which move is the latest one F or B */
			if (i ==6)
				seatRowLastMove = a;
			/* if F - front row, then max is reduced (lower half) */
			if (a == 'F')
				maxRow = (minRow + maxRow)/2;
			/* if B - back row, then min is increased (upper half) */
			if (a == 'B')
				minRow = (minRow + maxRow)/2 + 1;
		}
		/* when maxrow != min row, seatRowLastMove is used to choose which one is the last move */
		if (seatRowLastMove == 'F')
			seatRow = maxRow;
		if (seatRowLastMove == 'B')
			seatRow = minRow;

		/* calculate column */
		int seatColumn = 0;
		char seatColumnLastMove = 0;
		int minColumn = 0;
		int maxColumn = 7;
		/* last 3 characters of boarding pass */
		for (int i = 7; i < 10; i++) {
			char a = boardingPass.charAt(i);
			/* if 9th characters, mark which move is the latest one L or R */
			if (i == 9)
				seatColumnLastMove = a;
			/* if L - left side, then max is reduced (lower half) */
			if (a == 'L')
				maxColumn = (minColumn + maxColumn)/2;
			/* if R - right side, then min is increased (upper half) */
			if (a == 'R')
				minColumn = (minColumn+ maxColumn)/2 + 1;
		}

		/* when max column != min column, seatColumnLastMove is used to choose which one is the last move */
		if (seatColumnLastMove == 'L')
			seatColumn = maxColumn;
		if (seatColumnLastMove == 'R')
			seatColumn = minColumn;

		/* pack all of those in a list with the seat id */
		List<Integer> seatCode = new ArrayList<>();
		seatCode.add(seatRow);
		seatCode.add(seatColumn);
		seatCode.add(seatRow * 8 + seatColumn);

		return seatCode;
	}

	/**
	 * calculate the highest seat id
	 * @param boardingPasses List of boarding passes codes
	 * @return integer containing highest value of seat id
	 */
	private int findHighestSeatId(List<String> boardingPasses) {
		return Collections.max(boardingPasses.stream()
						.map(BoardingPassCalculator::findSeat)
						.map(l -> l.get(2))
						.collect(Collectors.toList()));
	}

	/**
	 * find missing seat
	 * the parameter specifies all occupied seats, there is only one missing seat
	 * @param seatsOccupied List of [row, column, seatId] which is occupied
	 * @return missing seat
	 */
	private int findMySeat(List<List<Integer>> seatsOccupied) {
		List<Integer> occupiedIds = seatsOccupied.stream()
												.map(l -> l.get(2))
												.collect(Collectors.toList());

		for (int i = Collections.min(occupiedIds); i < Collections.max(occupiedIds); i++)
			if (!occupiedIds.contains(i))
				return i;
		return -1;
	}

	public static void main(String[] args) {
		BoardingPassCalculator calculator = new BoardingPassCalculator();
		System.out.println(calculator.findHighestSeatId(calculator.readInputFile()));
		System.out.println(calculator.findMySeat(calculator.readInputFile()
									.stream()
									.map(BoardingPassCalculator::findSeat)
									.collect(Collectors.toList())));
	}
}
