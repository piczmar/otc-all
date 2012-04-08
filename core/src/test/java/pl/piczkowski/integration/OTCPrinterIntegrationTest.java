package pl.piczkowski.integration;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.piczkowski.OTCPrinter;

public class OTCPrinterIntegrationTest {

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
	public void shouldPrintCommaSeparatedElementsInSortedOrder() {
		String expected = elements.get(0) + "," + elements.get(1) + "," + elements.get(2);

		String actual = printer.asSortedString(elements.get(1), elements.get(2), elements.get(0));
		
		assertThat(actual).isEqualTo(expected);
	}
}
