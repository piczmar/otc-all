package pl.piczkowski.integration;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.piczkowski.DataProviders;
import pl.piczkowski.OTCPrinter;

public class OTCPrinterIntegrationIntegersTest {

	private OTCPrinter printer;

	interface DummyComparable extends Comparable<DummyComparable> {
	}

	@BeforeMethod
	public void setUp() {
		printer = new OTCPrinter();
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "getIntegers", skipFailedInvocations = true)
	public void shouldPrintCommaSeparatedElementsInSortedOrder(Integer[] integers, String expected) {

		String actual = printer.asSortedString(integers);

		assertThat(actual).isEqualTo(expected);
	}
}
