package day4_passport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Predicate;

/* Link to Problem: https://adventofcode.com/2020/day/4 */
public class PassportValidator
{
	/* Requierements as Predicate */
	Predicate<Map<String, String>> validPass =
		s -> Integer.parseInt(s.get("byr")) >= 1920 && Integer.parseInt(s.get("byr")) <= 2002 &&									// birth year
			Integer.parseInt(s.get("iyr")) >= 2010 && Integer.parseInt(s.get("iyr")) <= 2020 &&										// issue year
			Integer.parseInt(s.get("eyr")) >= 2020 && Integer.parseInt(s.get("eyr")) <= 2030 &&										// expiration year
			s.get("hcl").matches("#[0-9a-f]{6}$") &&																			// hair color
			s.get("ecl").matches("amb|blu|brn|gry|grn|hzl|oth") &&															// eye color
			s.get("pid").length() == 9 && Integer.parseInt(s.get("pid")) <= 999999999 && Integer.parseInt(s.get("pid")) > 0 &&		// passport id
			(s.get("hgt").contains("cm") &&																							// height cm
					Integer.parseInt(s.get("hgt").replace("cm", "")) >= 150 &&
					Integer.parseInt(s.get("hgt").replace("cm", "")) <= 193 ||
			s.get("hgt").contains("in") &&																							// height in
					Integer.parseInt(s.get("hgt").replace("in", "")) >= 59 &&
					Integer.parseInt(s.get("hgt").replace("in", "")) <= 76);

	/**
	 * read input file from resource folder
	 * @return List of Map, one map -> one passport,
	 * where the key is passport's field (byr, iyr, eyr, hcl, ecl, pid, hgt, cid)
	 */
	private List<Map<String, String>> readInputFile() throws IOException {
		List<List<String>> listOfLines = new ArrayList<>();
		List<Map<String, String>> listOfPassports = new ArrayList<>();

		InputStream is = getClass().getClassLoader().getResourceAsStream("day4_passport/input.txt");
		assert is != null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		/* hold passport fields until it encounters blank line */
		List<String> singlePassport = new ArrayList<>();
		for (String line; (line = reader.readLine())!= null;) {
			/* if line is not blank, then add fields to existing passport */
			if (!line.isBlank()) {
				singlePassport.addAll(Arrays.asList(line.split("\\s+")));
				continue;
			}
			/* if line is blank, then add finished passport to the list of lines */
			listOfLines.add(singlePassport);
			singlePassport = new ArrayList<>();
		}
		/* for last passport */
		listOfLines.add(singlePassport);

		/* convert list of lines to list of map passport */
		for (List<String> singleLine : listOfLines) {
			Map<String, String> paspport = new HashMap<>();
			/* for each field and its value in a line*/
			for (String fieldData : singleLine) {
				/* split the field by its name and its value, then add to the map */
				String[] fields = fieldData.split(":");
				paspport.put(fields[0], fields[1]);
			}
			/* collect each map */
			listOfPassports.add(paspport);
		}
		return listOfPassports;
	}

	/**
	 * check which passport is valid, criteria:
	 * all 8 fields are presentr OR only missing "cid" field
	 * @param passportList list of passport read by method above
	 * @return how many passport is valid
	 */
	private long checkValidPassportp1(List<Map<String, String>> passportList) {
		return passportList.stream()
							.filter(pass -> pass.size() == 8 || (pass.size() == 7 && (!pass.containsKey("cid"))))
							.count();
	}

	/**
	 * check which passport is valid, criteria:
	 * see Predicate<String> validPassport above
	 * @param passportList list of passport read by method above
	 * @return how many passport is valid
	 */
	private long checkValidPassportp2(List<Map<String, String>> passportList) {
		return passportList.stream()
				.filter(pass -> pass.size() == 8 || (pass.size() == 7 && (!pass.containsKey("cid"))))
				.filter(validPass).count();
	}

	public static void main(String[] args) throws IOException {
		PassportValidator validator = new PassportValidator();
		System.out.println(validator.checkValidPassportp1(validator.readInputFile()));
		System.out.println(validator.checkValidPassportp2(validator.readInputFile()));
	}
}
