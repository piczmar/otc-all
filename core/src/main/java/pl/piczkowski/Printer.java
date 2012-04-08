package pl.piczkowski;

public interface Printer {
	/**
	 * Returns string with sorted values separated with commas. Uses priority
	 * queue - first inserts all values and then pops each value in priority
	 * order and attaches to string
	 * 
	 * @param values
	 *            to sort
	 * @param <T>
	 *            type of value, needs to implement Comparable interface
	 * @return string with sorted values separated with commas
	 */
	public <T extends Comparable<T>> String asSortedString(T... values);

}
