package day3_trees;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TreesCounter
{
	/* Hold the input data as 2-Dimensional char Array */
	char[][] treeData;

	/**
	 * read input file, and store it as 2-D char Array
	 */
	private void readInputFile() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("day3_trees/input.txt");
		assert is != null;
		treeData = new BufferedReader(new InputStreamReader(is))
					.lines()
					.map(String::toCharArray)
					.toArray(char[][]::new);
	}

	/**
	 * count the amount of trees encountered during traversal of map (input file / char array)
	 * start at position 0,0 (char[0][0]), move the position defined by parameter:
	 * @param right move position by this much rightside: char[x][y+right % 31]
	 *              modulo 31 because char[0].length is 31 (it will start from beginning again)
	 * @param down  move position by this much downside:  char[x+down][y]
	 * the method will then check  if the content of the array is '.' or '#', where '#' represent tree
	 * @return how many trees are encountered during traversal
	 */
	private int countTrees(int right, int down) {
		assert this.treeData != null;
		int x_index = 0;				// column index in char[][]
		int y_index = 0;				// row index in char[][]
		int treeCounter = 0;

		/* so it does not go out of bound */
		while (x_index <= treeData.length -1 -down) {
			y_index = (y_index + right) % treeData[0].length;
			if(treeData[x_index += down][y_index] == '#')
				treeCounter++;
		}
		return treeCounter;
	}

	public static void main(String[] args)
	{
		TreesCounter treesCounter = new TreesCounter();
		treesCounter.readInputFile();
		List<Integer> treeCounts = new ArrayList<>();
		treeCounts.add(treesCounter.countTrees(1, 1));			// different mode of traversals
		treeCounts.add(treesCounter.countTrees(3, 1));
		treeCounts.add(treesCounter.countTrees(5, 1));
		treeCounts.add(treesCounter.countTrees(7, 1));
		treeCounts.add(treesCounter.countTrees(1, 2));

		/* How many trees are encountered during each traversal */
		assert treeCounts.size() != 0;
		for (Integer tree : treeCounts)
			System.out.println(tree);

		/* Multiply the amount of trees together */
		System.out.println(treeCounts.stream()
									 .map(BigInteger::valueOf)
									 .reduce(BigInteger.ONE, BigInteger::multiply));
	}
}
