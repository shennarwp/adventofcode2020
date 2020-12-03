package day2_password;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
/* Link to Problem: https://adventofcode.com/2020/day/2 */

/**
 * Hold the Password object
 */
class Password
{
	private final int min;
	private final int max;
	private final String rule;
	private final String pass;

	Password(int min, int max, String rule, String pass) {
		this.min = min;
		this.max = max;
		this.rule = rule;
		this.pass = pass;
	}
	public int getMin() { return min; }
	public int getMax() { return max; }
	public String getRule() { return rule; }
	public String getPass() { return pass; }
}

class PasswordChecker
{
	/**
	 * turn String array e.g. "1 7 r rszchrrrzgr" to Password object
	 * @param input String array after read by method below
	 * @return Password object
	 */
	private static Password createPassword(String[] input) {
		return new Password(Integer.parseInt(input[0]),
						    Integer.parseInt(input[1]),
							input[2],
							input[3]);
	}

	/**
	 * read input file, line by line, turn "1-7 r: rszchrrrzgr" to "1 7 r: rszchrrrzgr"
	 * in an array
	 * @return list of password objects after converted
	 */
	private List<Password> readInputFile() {
		List<Password> list;
		InputStream is = getClass().getClassLoader().getResourceAsStream("day2_password/input.txt");
		assert is != null;
		list = new BufferedReader(new InputStreamReader(is))
				.lines()
				.map(s -> s.replaceAll("[-]", " "))
				.map(s -> s.replaceAll("[:]", ""))
				.map(s -> s.split("\\s+"))
				.map(PasswordChecker::createPassword)
				.collect(Collectors.toList());
		return list;
	}

	/**
	 * count how many password is valid
	 * rule: Password must contain a certain number of specific character,
	 * 		 this specific character occurrence must be between min and max
	 * @param list List of passwords to be checked
	 * @return how many passwords are valid according to the rule
	 */
	private int validPasswordp1(List<Password> list) {
		int valid = 0;
		for (Password p : list) {
			long count = p.getPass().chars().filter(c -> c == p.getRule().charAt(0)).count();
			if (count >= p.getMin() && count <= p.getMax())
				valid++;
		}
		return valid;
	}

	/**
	 * count how many password is valid
	 * rule: Password must now contains exactly ONE specific character at specific position
	 * 		 Position is defined by min and max
	 * 		 The specific character must exist in EITHER position min OR position max, can not be both
	 * 		 Position is an index starting at 1
	 * @param list List of passwords to be checked
	 * 	 * @return how many passwords are valid according to the rule
	 */
	private int validPasswordp2(List<Password> list) {
		int valid = 0;
		for (Password p : list)
			if (p.getPass().charAt(p.getMin()-1) == p.getRule().charAt(0) ^ p.getPass().charAt(p.getMax()-1) == p.getRule().charAt(0))
				valid++;
		return valid;
	}

	public static void main(String[] args)
	{
		PasswordChecker checker = new PasswordChecker();
		System.out.println(checker.validPasswordp1(checker.readInputFile()));
		System.out.println(checker.validPasswordp2(checker.readInputFile()));
	}
}
