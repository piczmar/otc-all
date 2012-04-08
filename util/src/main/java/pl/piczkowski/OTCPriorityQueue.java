package pl.piczkowski;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OTCPriorityQueue<T extends Comparable<T>> implements PriorityQueue<T> {

	private List<T> elements = new ArrayList<T>();

	private Lock lock = new ReentrantLock();

	public int size() {
		lock.lock();
		try {
			return getElements().size();
		} finally {
			lock.unlock();
		}

	}

	public void insert(T element) {
		if (element == null) {
			throw new NullPointerException("Argument should not be null.");
		}
		lock.lock();
		try {
			addElement(element);
		} finally {
			lock.unlock();
		}
	}

	public T popMax() {
		lock.lock();
		try {
			if (getElements().isEmpty()) {
				throw new NoSuchElementException("Queue is empty.");
			}

			T maxElement = getMaxElement();
			return maxElement;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public String toString() {
		if (getElements().isEmpty()) {
			return "<is empty>";
		}
		final StringBuffer buffer = new StringBuffer();
		final String newLine = "\n";
		final String tab = "\t";
		int itemCnt = 0;
		int levelCnt = 1;
		buffer.append("<").append(newLine);
		for (T element : getElements()) {
			itemCnt++;
			buffer.append(element.toString()).append(tab);
			if (itemCnt == levelCnt) {
				levelCnt *= 2;
				buffer.append(newLine);
				itemCnt = 0;
			}
		}
		buffer.append(newLine).append(">");
		return buffer.toString();
	}

	protected T getMaxElement() {
		T maxElement = getElements().get(0);
		T lastElement = getElements().remove(getElements().size() - 1);
		if (!getElements().isEmpty()) {
			getElements().set(0, lastElement);
			heapify(0);
		}
		return maxElement;
	}

	protected void addElement(T element) {
		getElements().add(element);
		orderHeap(getElements().size() - 1);
	}

	protected void setLock(Lock lock) {
		this.lock = lock;
	}

	protected List<T> getElements() {
		return elements;
	}

	private void heapify(int pos) {
		while (pos < getElements().size() / 2) {
			int child = getGreaterChildIndex(pos);
			if (isChildGreaterThanParent(pos, child)) {
				break;
			}
			Collections.swap(getElements(), pos, child);
			pos = child;
		}
	}

	private void orderHeap(int addedIndex) {
		int pos = addedIndex;
		while (pos > 0) {
			int parent = getParent(pos);
			if (isChildGreaterThanParent(parent, pos)) {
				break;
			}
			Collections.swap(getElements(), pos, parent);
			pos = parent;
		}
	}

	private boolean isChildGreaterThanParent(int parentIndex, int childIndex) {
		return getElements().get(childIndex).compareTo(getElements().get(parentIndex)) <= 0;
	}

	private int getGreaterChildIndex(int parent) {
		int child = getChild(parent);
		if (isInArrayRange(child) && getElements().get(child).compareTo(getElements().get(child + 1)) < 0) {
			++child;
		}
		return child;
	}

	private boolean isInArrayRange(int child) {
		return child < getElements().size() - 1;
	}

	private int getParent(int pos) {
		return (pos - 1) / 2;
	}

	private int getChild(int pos) {
		return 2 * pos + 1;
	}
}
