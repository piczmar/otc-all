package pl.piczkowski;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OTCPriorityQueueStringTest {

	private PriorityQueue<String> priorityQueue;

	@BeforeMethod
	public void setUp() {
		priorityQueue = new OTCPriorityQueue<String>();
	}

	@SuppressWarnings("unused")
	@DataProvider
	private Iterator<Object[]> getData() throws BiffException, IOException {
		ArrayList<Object[]> entries = new ArrayList<Object[]>();
		File file = new File("src/test/resources/strings.xls");
		Workbook workbook = Workbook.getWorkbook(file);
		Sheet[] sheets = workbook.getSheets();
		for (int i = 0; i < sheets.length; i++) {
			Sheet sheet = workbook.getSheet(i);

			for (int col = 0; col < sheet.getColumns(); col++) {
				Set<String> unordered = new HashSet<String>();
				for (int row = 1; row < sheet.getRows(); row++) {
					String entry = sheet.getCell(col, row).getContents();
					unordered.add(entry);
				}
				entries.add(new Object[] { unordered });
			}

		}
		return entries.iterator();
	}

	@Test(dataProvider = "getData")
	public void shouldPopGreatestElement(Set<String> unOrderedEntries) {
		for (String string : unOrderedEntries) {
			priorityQueue.insert(string);
		}
		Set<String> sortedSet = new TreeSet<String>(Collections.reverseOrder());
		sortedSet.addAll(unOrderedEntries);

		for (String string : sortedSet) {
			assertThat(priorityQueue.popMax()).isEqualTo(string);
		}
	}

	@Test(dataProvider = "getData")
	public void shouldHaveSizeOfNumberInsertedElements(Set<String> unOrderedEntries) {

		for (String string : unOrderedEntries) {
			priorityQueue.insert(string);
		}

		assertThat(priorityQueue.size()).isEqualTo(unOrderedEntries.size());
	}

}
