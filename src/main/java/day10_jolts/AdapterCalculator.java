package day10_jolts;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/* Link to Problem: https://adventofcode.com/2020/day/10 */
public class AdapterCalculator
{
	/**
	 * read input file
	 * @return each line parsed as long then collected in a list, sorted
	 */
	private List<Long> readInputFile() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("day10_jolts/input.txt");
		assert is != null;
		return new BufferedReader(new InputStreamReader(is))
				.lines()
				.map(Long::parseLong)
				.sorted()
				.collect(Collectors.toList());
	}

	/**
	 * from input file, find the count of differences between two numbers
	 * @param adapterList list of long read from method above
	 * @return long[0]: how many times the difference is 1
	 * 			long[1]: how many times the difference is 3
	 */
	private long[] calculateDifferences(List<Long> adapterList) {
		long before =0;
		int counterOne = 0;
		int counterThree = 0;
		for (Long s : adapterList) {
			if (s - before == 1)
				counterOne++;
			if (s - before == 3)
				counterThree++;
			before = s;
		}
		return new long[]{counterOne, counterThree};
	}

	public static void main(String[] args)
	{
		AdapterCalculator adapterCalculator = new AdapterCalculator();
		List<Long> adapterList = adapterCalculator.readInputFile();
		adapterList.add(Collections.max(adapterList) + 3); // +3 for the device
		System.out.println(Arrays.toString(adapterCalculator.calculateDifferences(adapterList)));

	}
}
