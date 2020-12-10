package day9_encoding;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* Link to Problem: https://adventofcode.com/2020/day/9 */
public class EncodingBreaker
{
	private static final int PREAMBLE_LENGTH = 25;

	/**
	 * read input file, store each number in line as element in a long array
	 * @return array of each number
	 */
	private long[] readInputFile() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("day9_encoding/input.txt");
		assert is != null;
		return new BufferedReader(new InputStreamReader(is))
					.lines()
					.mapToLong(Long::parseLong)
					.toArray();
	}

	/**
	 * check from an array, if there is a pair sum combination of a given number
	 * @param preamble array to be checked
	 * @param sum number, which the pair sum combination is looked for
	 * @return whether the pair combination exists or not
	 */
	private boolean checkSumPair(long[] preamble, long sum) {
		for (int i = 0; i < preamble.length; i++)
			for (int j = i; j < preamble.length ; j++)
				if (preamble[i] + preamble[j] == sum)
					return true;
		return false;
	}

	/**
	 * from the input file, find the first number that cannot be expressed
	 * as sum of 2 numbers from previous 25 element in the array / line
	 * the function will call checkSumPair() with parameter: long[] with previous 25 elements as its content
	 * @param xmasEncoding array of long from input file
	 * @return the number that can not abide by this rule
	 */
	private long findNumberp1(long[] xmasEncoding) {
		for (int i = PREAMBLE_LENGTH; i < xmasEncoding.length; i++)
			if (!checkSumPair(Arrays.copyOfRange(xmasEncoding, i-PREAMBLE_LENGTH, i), xmasEncoding[i]))
				return xmasEncoding[i];
		return -1;
	}

	/**
	 * from an array, find contiguous set of numbers, where its elements add up to given sum
	 * @param xmasEncoding array to be checked
	 * @param sum number, which the contiguous set should sum to
	 * @return the contiguous set as list of number
	 */
	private List<Long> findContiguousSet(long[] xmasEncoding, long sum) {
		long acc = 0;
		List<Long> contiguousSet = new ArrayList<>();
		for (long l : xmasEncoding) {
			if (acc == sum)
				return contiguousSet;
			if (acc > sum || l >= sum) {
				contiguousSet = new ArrayList<>();
				acc = 0;
			}
			acc += l;
			contiguousSet.add(l);
		}
		return contiguousSet;
	}

	/**
	 * from input file and findNumberp1(), find contiguous set of numbers,
	 * where the sum is number found before from findNumberp1()
	 * this function will call findContiguousSet() where the parameter is:
	 * the same long[], but the first element is reduced one by one
	 * @param xmasEncoding xmasEncoding array of long from input file
	 * @param sum number found before from findNumberp1()
	 * @return max + min number from this contiguous set
	 */
	private long findNumberp2(long[] xmasEncoding, long sum) {
		for (int i = 0; i < xmasEncoding.length; i++) {
			List<Long> contiguousSet = findContiguousSet(Arrays.copyOfRange(xmasEncoding, i, xmasEncoding.length), sum);
			if (contiguousSet.size() > 1)
				return Collections.max(contiguousSet) + Collections.min(contiguousSet);
		}
		return -1;
	}

	public static void main(String[] args)
	{
		EncodingBreaker encodingBreaker = new EncodingBreaker();
		long[] inputFile = encodingBreaker.readInputFile();
		System.out.println(encodingBreaker.findNumberp1(inputFile));
		System.out.println(encodingBreaker.findNumberp2(inputFile, encodingBreaker.findNumberp1(inputFile)));
	}
}
