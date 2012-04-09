package pl.piczkowski;


public class OTCPrinter implements Printer {


	public <T extends Comparable<T>> String asSortedString(T... values) {
		T[] sortedElements = getSorted(values);

		return buildCommaSeparatedString(sortedElements);
	}

	private <T extends Comparable<T>> String buildCommaSeparatedString(T[] sortedElements) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < sortedElements.length; i++) {
			buffer.append(sortedElements[i]);
			if (i < sortedElements.length - 1) {
				buffer.append(',');
			}
		}
		return buffer.toString();
	}

	private <T extends Comparable<T>> T[] getSorted(T... values) {
		PriorityQueue<T> queue = getPriorityQueue();
		for (int i = 0; i < values.length; i++) {
			queue.insert(values[i]);
		}
		
		for (int i = 0; i < values.length; i++) {
			values[i] = queue.popMax();
		}
		queue = null; // let GC work
		return values;
	}
	
	protected  <T extends Comparable<T>> PriorityQueue<T> getPriorityQueue() {
		return new OTCPriorityQueue<T>();
	}
}
