package day6_custom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/* Link to Problem: https://adventofcode.com/2020/day/6 */
public class CustomQuestion
{
	/**
	 * read input file
	 * @return list of groups, each group has multiple list of string
	 * this list of strings represent different person's answer
	 */
	private List<List<String>> readInputFile() throws IOException {
		List<List<String>> listOfGroups = new ArrayList<>();

		InputStream is = getClass().getClassLoader().getResourceAsStream("day6_custom/input.txt");
		assert is != null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		List<String> singleGroup = new ArrayList<>();
		for (String line; (line = reader.readLine())!= null;) {
			if (!line.isBlank()) {
				singleGroup.addAll(Arrays.asList(line.split("\\s+")));
				continue;
			}
			listOfGroups.add(singleGroup);
			singleGroup = new ArrayList<>();
		}
		listOfGroups.add(singleGroup);

		return listOfGroups;
	}

	/**
	 * count how many questions for which ANYONE in a group answers "yes"
	 * -> count ALL distinct characters in each group
	 * e.g. abc, abg, abd -> counted as abcdg -> 5
	 * @param listOfGroups list of group's answer
	 * @return sum of these questions in all groups
	 */
	private int p1(List<List<String>> listOfGroups) {
		return listOfGroups.stream()
				.map(list -> list.stream()
						.map(String::valueOf)
						.collect(Collectors.joining()))
				.collect(Collectors.toList())
				.stream()
				.map(string -> Arrays.stream(string.split(""))
						.distinct()
						.collect(Collectors.joining()))
				.map(String::length)
				.mapToInt(Integer::valueOf)
				.sum();
	}

	/**
	 * count how many questions for which EVERYONE in a group answers "yes"
	 * -> count how many of the SAME characters appear in ALL group member
	 * e.g. 'a' must appear in all member's answer to be counted
	 * @param listOfGroups list of group's answer
	 * @return sum of these questions in all groups
	 */
	private int p2(List<List<String>> listOfGroups) {
		int counter = 0;
		for (List<String> group :listOfGroups) {
			String x = group.stream()
							.map(String::valueOf)
							.collect(Collectors.joining());
			for (String count : Arrays.stream(x.split("")).distinct().collect(Collectors.toList())) {
				long sum = x.chars()
							.filter(ch -> ch == count.charAt(0))
							.count();
				if (sum == group.size())
					counter++;
			}
		}
		return counter;
	}

	public static void main(String[] args) throws IOException {
		CustomQuestion customQuestion = new CustomQuestion();
		System.out.println(customQuestion.p1(customQuestion.readInputFile()));
		System.out.println(customQuestion.p2(customQuestion.readInputFile()));
	}
}
