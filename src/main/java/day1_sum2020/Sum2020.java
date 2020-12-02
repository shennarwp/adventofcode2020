package day1_sum2020;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

class Sum2020
{
	/**
	 * read input file from resources folder
	 * @return list of integers after converted from string
	 */
	private List<Integer> readInputFile() {
		List<Integer> list;
		InputStream is = getClass().getClassLoader().getResourceAsStream("day1_sum2020/input.txt");
		assert is != null;
		list = new BufferedReader(new InputStreamReader(is))
					.lines()
					.map(Integer::parseInt)
					.collect(Collectors.toList());
		return list;
	}

	/**
	 * check if there are 2 numbers in a list which the sum is 2020
	 * @param list list of integers read by method above
	 * @return the multiplication of said numbers, 0 if not found
	 */
	private int part1(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i; j < list.size() - 1; j++) {
				if (list.get(i) + list.get(j) == 2020) {
					return list.get(i) * list.get(j);
				}
			}
		}
		return 0;
	}

	/**
	 * check if there are 3 numbers in a list which the sum is 2020
	 * @param list list of integers read by method above
	 * @return the multiplication of said numbers, 0 if not found
	 */
	private int part2(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i; j < list.size() - 1; j++) {
				for (int k = j; k < list.size() - 2; k++) {
					if (list.get(i) + list.get(j) + list.get(k) == 2020) {
						return list.get(i) * list.get(j) * list.get(k);
					}
				}
			}
		}
		return 0;
	}

	public static void main(String[] args)
	{
		Sum2020 sum2020 = new Sum2020();
		System.out.println(sum2020.part1(sum2020.readInputFile()));
		System.out.println(sum2020.part2(sum2020.readInputFile()));
	}
}
