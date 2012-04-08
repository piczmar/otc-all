package pl.piczkowski;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OTCPrinterTest {

	private OTCPrinter printer;
	private List<DummyComparable> elements;

	interface DummyComparable extends Comparable<DummyComparable> {
	}

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
	}

	@Test
	public void shouldReturnEmptyStringWhenNoArguments() {
		// given
		String expected = "";

		// when
		String result = printer.asSortedString();

		// then
		assertThat(result).isEqualTo(expected);
	}

	@Test
	public void shouldPrintCommaSeparatedElements() {
		String expected = elements.get(1) + "," + elements.get(2) + "," + elements.get(0);
		OTCPrinter mockPrinter = new OTCPrinter(){
			@Override
			protected <T extends java.lang.Comparable<T>> T[] getSorted(T...values) {
				return values;
			};
		};
		
		String actual = mockPrinter.asSortedString(elements.get(1), elements.get(2), elements.get(0));
		
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void shouldPrintCommaSeparatedElementsInSortedOrder() {
		String expected = elements.get(0) + "," + elements.get(1) + "," + elements.get(2);
		OTCPrinter mockPrinter = new OTCPrinter() {
			@Override
			protected <T extends Comparable<T>> T[] getSorted(T... values) {
				return (T[]) new DummyComparable[]{ elements.get(0),elements.get(1), elements.get(2)};
			}
		};

		String actual = mockPrinter.asSortedString(elements.get(1), elements.get(2), elements.get(0));
		
		assertThat(actual).isEqualTo(expected);
	}
}
