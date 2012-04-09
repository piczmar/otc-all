package pl.piczkowski;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OTCPrinterTest {

	private OTCPrinter printer;
	private PriorityQueue<DummyComparable> queue;
	private List<DummyComparable> elements;

	interface DummyComparable extends Comparable<DummyComparable> {
	}

	@SuppressWarnings("unchecked")
	@BeforeMethod
	public void setUp() {
		printer = new OTCPrinter();

		final int numberOfComparables = 3;
		elements = new ArrayList<DummyComparable>(numberOfComparables);
		for (int i = 0; i < numberOfComparables; i++) {
			elements.add(Mockito.mock(DummyComparable.class));
		}

		// elements.get(0)>elements.get(1)>elements.get(2)
		Mockito.when(elements.get(0).compareTo(elements.get(1))).thenReturn(1);
		Mockito.when(elements.get(1).compareTo(elements.get(0))).thenReturn(-1);
		Mockito.when(elements.get(1).compareTo(elements.get(2))).thenReturn(1);
		Mockito.when(elements.get(2).compareTo(elements.get(1))).thenReturn(-1);
		Mockito.when(elements.get(0).compareTo(elements.get(2))).thenReturn(1);
		Mockito.when(elements.get(2).compareTo(elements.get(0))).thenReturn(-1);
		Mockito.when(elements.get(0).toString()).thenReturn("elem0");
		Mockito.when(elements.get(1).toString()).thenReturn("elem1");
		Mockito.when(elements.get(2).toString()).thenReturn("elem2");
		
		queue = Mockito.mock(OTCPriorityQueue.class);
	}

	@Test
	public void shouldReturnEmptyStringWhenNoArguments() {
		// given
		String expected = "";

		// when
		String result = printer.asSortedString(new String[]{});

		// then
		assertThat(result).isEqualTo(expected);
	}

	OTCPrinter mockPrinter = new OTCPrinter(){
		@SuppressWarnings("unchecked")
		@Override
		protected <T extends java.lang.Comparable<T>> PriorityQueue<T> getPriorityQueue() {
			return (PriorityQueue<T>) queue;
		};
	};
	
	@Test
	public void shouldPrintCommaSeparatedElements() {
		Mockito.when(queue.popMax()).thenReturn(elements.get(1)).thenReturn(elements.get(2)).thenReturn(elements.get(0));
		String expected = elements.get(1) + "," + elements.get(2) + "," + elements.get(0);

		
		String actual = mockPrinter.asSortedString(elements.get(1), elements.get(2), elements.get(0));
		
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void shouldPrintCommaSeparatedElementsInSortedOrder() {
		Mockito.when(queue.popMax()).thenReturn(elements.get(0)).thenReturn(elements.get(1)).thenReturn(elements.get(2));
		String expected = elements.get(0) + "," + elements.get(1) + "," + elements.get(2);

		String actual = mockPrinter.asSortedString(elements.get(1), elements.get(2), elements.get(0));
		
		assertThat(actual).isEqualTo(expected);
	}
}
