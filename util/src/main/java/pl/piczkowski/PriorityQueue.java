package pl.piczkowski;

import java.util.NoSuchElementException;

public interface PriorityQueue<T extends Comparable<T>> {
	/**
	 * Return number of priority queue elements.
	 * 
	 * @return size of priority queue
	 */
	int size();

	/**
	 * Inserts new element to priority queue.
	 * 
	 * @param element
	 *            new element
	 * @throws NullPointerException
	 *             when element is null
	 */
	void insert(T element);

	/**
	 * Gets maximum element from heap, removes it from the heap.
	 * 
	 * @return maximum element from heap
	 * @throws NoSuchElementException
	 *             if heap is empty or element does not exist
	 */
	T popMax();
}
