package pl.piczkowski.unit;

import static com.googlecode.catchexception.CatchException.*;

import static org.fest.assertions.Assertions.*;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.piczkowski.OTCPriorityQueue;
import pl.piczkowski.PriorityQueue;

public class PriorityQueueTest{

	private PriorityQueue<IElement> priorityQueue;

	@BeforeMethod
	public void setUp() {
		priorityQueue = new OTCPriorityQueue<IElement>();
	}
	
	@Test(expectedExceptions={NullPointerException.class})
	public void shouldNotAllowInsertingNullElement(){
		//given
		IElement element = null;
		
		//when
		priorityQueue.insert(element);

		//then should throw NullPointerException
		
	}
	
	@Test
	public  <T extends Comparable<T>> void shouldPopSameObjectWhenInsertedSingleElement(){
		IElement element = Mockito.mock(IElement.class);
		
		priorityQueue.insert(element);

		
		assertThat(priorityQueue.popMax()).isEqualTo(element);
	}
}
