package pl.piczkowski;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OTCPriorityQueueStringTest {

	private PriorityQueue<Integer> priorityQueue;
	private static final int RANDOM_NUMBERS_COUNT = 50;
	private static Set<Integer> randomNumbers;

	@BeforeClass
	public static void initSuite() {
		randomNumbers = TestUtil.generateUniqueRandomIntegers(RANDOM_NUMBERS_COUNT, 100);
	}

	@BeforeMethod
	public void setUp() {
		priorityQueue = new OTCPriorityQueue<Integer>();
	}

	@Test
	public void shouldPopGreatestElement() {
		Integer elem1 = new Integer(Integer.MAX_VALUE);
		priorityQueue.insert(elem1);
		// Generating 500 random integers in range 0..999.
		for (Integer number : randomNumbers) {
			priorityQueue.insert(number);
		}

		assertThat(priorityQueue.popMax()).isEqualTo(elem1);
	}

	@Test
	public void shouldHaveSizeOfNumberInsertedElements() {
		// Generating 500 random integers in range 0..999.
		for (Integer number : randomNumbers) {
			priorityQueue.insert(number);
		}

		assertThat(priorityQueue.size()).isEqualTo(RANDOM_NUMBERS_COUNT);
	}

}
