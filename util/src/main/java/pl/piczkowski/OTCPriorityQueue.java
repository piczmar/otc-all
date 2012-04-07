package pl.piczkowski;

import java.util.NoSuchElementException;

public class OTCPriorityQueue<T extends Comparable<T>> implements PriorityQueue<T> {

	private T element;
	private int elementCounter;

	public int size() {
		return elementCounter;
	}

	public void insert(T element) {
		if (element == null) {
			throw new NullPointerException("Argument should not be null.");
		}
		this.element = element;
		elementCounter++;

	}

	public T popMax() {
		if (elementCounter == 0) {
			throw new NoSuchElementException("Queue is empty.");
		}
		elementCounter--;
		return this.element;
	}

}
