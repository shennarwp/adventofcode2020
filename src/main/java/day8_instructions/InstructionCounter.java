package day8_instructions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InstructionCounter
{
	private static final int ACC = 0;
	private static final int JMP = 1;
	private static final int NOP = 2;

	/**
	 * read input file
	 * @return list of int[], where int[0] is the type of instruction, and int[1] is its argument
	 */
	private List<int[]> readInputFile() {
		List<String[]> lines;
		InputStream is = getClass().getClassLoader().getResourceAsStream("day8_instructions/input.txt");
		assert is != null;
		lines = new BufferedReader(new InputStreamReader(is))
						.lines()
						.map(s -> s.split("\\s+"))
						.collect(Collectors.toList());

		List<int[]> instructionsList = new ArrayList<>();
		/* convert string[] to integer[] */
		for (String[] arr : lines) {
			/* arr[0] contains instruction type */
			/* acc = 0; jmp = 1; nop = 2; */
			int type = NOP;					//default to NOP
			if (arr[0].equals("acc"))
				type = ACC;
			if (arr[0].equals("jmp"))
				type = JMP;

			/* arr[1] contains instruction argument, also parse + and - sign */
			int argument;
			if (arr[1].charAt(0) == '+')
				argument = Integer.parseInt(arr[1].replace("+", ""));
			else
				argument = -1 * Integer.parseInt(arr[1].replace("-", ""));

			int[] singleInstruction = new int[]{type, argument};
			instructionsList.add(singleInstruction);
		}
		return instructionsList;
	}

	/**
	 * execute intstruction list read by method above
	 * @return global accumulator as integer after executing instructions in the list
	 */
	private int performInstructions() {
		List<int[]> instructionsList = readInputFile();
		int globalAccumulator = 0;
		int programCounter = 0;
		Set<Integer> executedInstructions = new HashSet<>();

		for (int i = 0; i < instructionsList.size(); i++) {
			/* when the instruction has already been executed before, return global accumulator */
			if (executedInstructions.contains(programCounter))
				return globalAccumulator;

			/* perform instruction */
			/* add PC to executed instructions */
			executedInstructions.add(programCounter);
			/* acc */
			if (instructionsList.get(programCounter)[0] == ACC) {
				globalAccumulator += instructionsList.get(programCounter)[1];
				programCounter++;
			}
			/* jmp */
			else if (instructionsList.get(programCounter)[0] == JMP)
				programCounter += instructionsList.get(programCounter)[1];
			/* nop */
			else if (instructionsList.get(programCounter)[0] == NOP)
				programCounter++;
		}
		return globalAccumulator;
	}

	/**
	 * change instruction to JMP to NOP or NOP to JMP
	 * @param instructionsList instruction list
	 * @param counter which line / PC to be changed
	 * @return modified instructions list
	 */
	private List<int[]> fixInstructions(List<int[]> instructionsList, int counter) {
		if (instructionsList.get(counter)[0] == JMP)
			instructionsList.get(counter)[0] = NOP;
		else if (instructionsList.get(counter)[0] == NOP)
			instructionsList.get(counter)[0] = JMP;
		return instructionsList;
	}

	/**
	 * find broken instruction line by brute forcing
	 * change instruction one line at a time (using above method), then performing / executing the list
	 * like performInstructions() to get globalAccumulator
	 * we are not interested for modified instruction list where the outcome is endless loop
	 * only the one where the program terminates (where the program counter exceed the number of lines)
	 * @return global accumulator, when the program terminates
	 */
	private int findBrokenInstructionAndPerform() {
		int globalAccumulator = 0;

		for (int i = 0; i < readInputFile().size(); i++) {
			List<int[]> originalList = readInputFile();
			/* change one line at a time, if type == ACC, then skip */
			if (originalList.get(i)[0] == ACC)
				continue;
			List<int[]> modified = fixInstructions(originalList, i);

			globalAccumulator = 0;
			int programCounter = 0;
			Set<Integer> executedInstructions = new HashSet<>();

			for (int j = 0; j < modified.size(); j++) {
				/* when the instruction has already been executed before, skip, */
				/* we are not interested in  endless loop */
				if (executedInstructions.contains(programCounter))
					break;
				/* when the PC exceed the last line number */
				if (programCounter >= modified.size())
					return globalAccumulator;

				/* perform instruction */
				/* add PC to executed instructions */
				executedInstructions.add(programCounter);
				/* acc */
				if (modified.get(programCounter)[0] == ACC) {
					globalAccumulator += modified.get(programCounter)[1];
					programCounter++;
				}
				/* jmp */
				else if (modified.get(programCounter)[0] == JMP)
					programCounter += modified.get(programCounter)[1];
				/* nop */
				else if (modified.get(programCounter)[0] == NOP)
					programCounter++;
			}
		}
		return globalAccumulator;
	}

	public static void main(String[] args)
	{
		InstructionCounter instructionCounter = new InstructionCounter();
		System.out.println(instructionCounter.performInstructions());
		System.out.println(instructionCounter.findBrokenInstructionAndPerform());
	}
}
