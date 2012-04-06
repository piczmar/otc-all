package pl.piczkowski;

public class OTCPriorityQueue<T extends Comparable<T>> implements PriorityQueue<T> {

	private T element;
	
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void insert(T element) {
		if (element == null) {
			throw new NullPointerException("Argument should not be null.");
		}
		this.element = element;
		
	}

	public T popMax() {
		return this.element;
	}

}
