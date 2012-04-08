package pl.piczkowski.integration;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.piczkowski.DataProviders;
import pl.piczkowski.OTCPrinter;

public class OTCPrinterIntegrationStringsTest {

	private OTCPrinter printer;

	interface DummyComparable extends Comparable<DummyComparable> {
	}

	@BeforeMethod
	public void setUp() {
		printer = new OTCPrinter();
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "getStrings", skipFailedInvocations = true)
	public void shouldPrintCommaSeparatedElementsInSortedOrder(String[] strings, String expected) {

		String actual = printer.asSortedString(strings);

		assertThat(actual).isEqualTo(expected);
	}
}
