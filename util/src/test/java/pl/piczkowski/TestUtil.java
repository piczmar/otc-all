package pl.piczkowski;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TestUtil {
	/**
	 * Generates n random integers form range 0..range.
	 * 
	 * @param n
	 *            number count
	 * @param range
	 * @return generated random numbers in set
	 */
	public static Set<Integer> generateUniqueRandomIntegers(int n, int range) {
		Random randomGenerator = new Random();
		Set<Integer> numbers = new HashSet<Integer>(n);
		while (numbers.size() != n) {
			numbers.add(randomGenerator.nextInt(range));
		}
		return numbers;
	}
	
	
}
