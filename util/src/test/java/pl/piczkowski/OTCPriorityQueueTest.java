package pl.piczkowski;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;

import static org.fest.assertions.Assertions.*;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.piczkowski.OTCPriorityQueue;
import pl.piczkowski.PriorityQueue;

public class OTCPriorityQueueTest {

	private static final int numberOfTestElements = 3;
	private static ITestElement[] elements = new ITestElement[numberOfTestElements];
	private OTCPriorityQueue<ITestElement> priorityQueue;

	interface ITestElement extends Comparable<ITestElement> {
	}

	@BeforeClass
	public static void initSuite() {
		ITestElement elem1 = Mockito.mock(ITestElement.class);
		ITestElement elem2 = Mockito.mock(ITestElement.class);
		ITestElement elem3 = Mockito.mock(ITestElement.class);
		Mockito.when(elem1.toString()).thenReturn("elem1");
		Mockito.when(elem2.toString()).thenReturn("elem2");
		Mockito.when(elem3.toString()).thenReturn("elem3");

		// elem1 > elem2
		Mockito.when(elem1.compareTo(elem2)).thenReturn(1);
		Mockito.when(elem2.compareTo(elem1)).thenReturn(-1);
		// elem2 > elem3
		Mockito.when(elem2.compareTo(elem3)).thenReturn(1);
		Mockito.when(elem3.compareTo(elem2)).thenReturn(-1);
		// elem1 > elem3
		Mockito.when(elem1.compareTo(elem3)).thenReturn(1);
		Mockito.when(elem3.compareTo(elem1)).thenReturn(-1);
		elements[0] = elem1;
		elements[1] = elem2;
		elements[2] = elem3;
	}

	@BeforeMethod
	public void setUp() {
		priorityQueue = new OTCPriorityQueue<ITestElement>();
	}

	@Test
	public void shouldBeEmptyWhenCreated() {
		assertThat(priorityQueue.size()).isEqualTo(0);
	}

	@Test(expectedExceptions = { NullPointerException.class })
	public void shouldNotAllowInsertingNullElement() {
		// given
		ITestElement element = null;

		// when
		priorityQueue.insert(element);

		// then should throw NullPointerException

	}

	@Test
	public void shouldPopSameObjectWhenInsertedSingleElement() {
		priorityQueue.insert(elements[0]);

		assertThat(priorityQueue.popMax()).isEqualTo(elements[0]);
	}

	@Test
	public void shouldHaveSizeOfOneWhenInsertedSingleElement() {
		priorityQueue.insert(elements[0]);

		assertThat(priorityQueue.size()).isEqualTo(1);
	}

	@Test
	public void shouldBeEmptyWhenPoppedLastElement() {
		priorityQueue.insert(elements[0]);
		priorityQueue.popMax();

		assertThat(priorityQueue.size()).isEqualTo(0);
	}

	@Test(expectedExceptions = NoSuchElementException.class)
	public void shouldThrowExceptionWhenPopFromEmpty() {
		priorityQueue.popMax();
	}

	@Test
	public void shouldPopGreaterElementWhenInsertedGreaterFirst() {
		priorityQueue.insert(elements[0]);
		priorityQueue.insert(elements[1]);

		assertThat(priorityQueue.popMax()).isEqualTo(elements[0]);
	}

	@Test
	public void shouldPopGreaterElementWhenInsertedGreaterLast() {
		priorityQueue.insert(elements[1]);
		priorityQueue.insert(elements[0]);

		assertThat(priorityQueue.popMax()).isEqualTo(elements[0]);
	}

	@Test
	public void shouldHaveSizeOfTwoWhenInsertedTwoElements() {
		priorityQueue.insert(elements[1]);
		priorityQueue.insert(elements[0]);

		assertThat(priorityQueue.size()).isEqualTo(2);
	}

	@Test
	public void shouldAlwlaysPopGreatestElementWhenInsertedGreaterLast() {
		priorityQueue.insert(elements[1]);
		priorityQueue.insert(elements[2]);
		priorityQueue.insert(elements[0]);

		assertThat(priorityQueue.popMax()).isEqualTo(elements[0]);
		assertThat(priorityQueue.popMax()).isEqualTo(elements[1]);
		assertThat(priorityQueue.popMax()).isEqualTo(elements[2]);
	}

	@Test
	public void shouldAlwlaysPopGreatestElementWhenInsertedGreaterFirst() {
		priorityQueue.insert(elements[0]);
		priorityQueue.insert(elements[2]);
		priorityQueue.insert(elements[1]);

		assertThat(priorityQueue.popMax()).isEqualTo(elements[0]);
		assertThat(priorityQueue.popMax()).isEqualTo(elements[1]);
		assertThat(priorityQueue.popMax()).isEqualTo(elements[2]);
	}

	public void shouldContainOnlyUniqueElements() {
		priorityQueue.insert(elements[0]);
		priorityQueue.insert(elements[0]);

		assertThat(priorityQueue.size()).isEqualTo(1);
		assertThat(priorityQueue.popMax()).isEqualTo(elements[0]);
	}

	class MockLock extends ReentrantLock {
		public boolean locked;
		public boolean unlocked;

		@Override
		public void lock() {
			locked = true;
		}

		@Override
		public void unlock() {
			unlocked = true;
		}
	}

	@Test
	public void shouldModifyBeMutualyExclusive() {
		final MockLock lock = new MockLock();

		OTCPriorityQueue<ITestElement> queue = new OTCPriorityQueue<ITestElement>() {
			@Override
			protected void addElement(ITestElement element) {
				assertThat(lock.locked).isEqualTo(true);
				assertThat(lock.unlocked).isEqualTo(false);
			}
		};
		queue.setLock(lock);

		assertThat(lock.locked).isEqualTo(false);
		assertThat(lock.unlocked).isEqualTo(false);

		queue.insert(elements[0]);

		assertThat(lock.locked).isEqualTo(true);
		assertThat(lock.unlocked).isEqualTo(true);
	}

	@Test
	public void shouldInsertLockAndUnlockBeGuardedByTryFinally() {
		final MockLock lock = new MockLock();
		OTCPriorityQueue<ITestElement> queue = new OTCPriorityQueue<ITestElement>() {
			@Override
			protected void addElement(ITestElement element) {
				throw new RuntimeException("simulated exception");
			}
		};

		queue.setLock(lock);

		try {
			queue.insert(elements[0]);
			Assert.fail("Expected the simulated exception!");
		} catch (RuntimeException ex) {
			// Expected
		}

		assertThat(lock.locked).isEqualTo(true);
		assertThat(lock.unlocked).isEqualTo(true);
	}

	@Test
	public void shouldSynchronizeRead() {
		final MockLock lock = new MockLock();
		final List<ITestElement> mockList = new ArrayList<ITestElement>() {
			@Override
			public boolean isEmpty() {
				assertThat(lock.locked).isEqualTo(true);
				assertThat(lock.unlocked).isEqualTo(false);
				return false;
			}
		};
		OTCPriorityQueue<ITestElement> queue = new OTCPriorityQueue<ITestElement>() {
			@Override
			protected List<ITestElement> getElements() {
				return mockList;
			}

			@Override
			protected ITestElement getMaxElement() {
				assertThat(lock.locked).isEqualTo(true);
				assertThat(lock.unlocked).isEqualTo(false);
				return elements[0];
			}
		};

		queue.setLock(lock);

		ITestElement elem = queue.popMax();

		assertThat(elem).isEqualTo(elements[0]);
		assertThat(lock.locked).isEqualTo(true);
		assertThat(lock.unlocked).isEqualTo(true);
	}

	@Test
	public void shouldReadLockAndUnlockBeGuardedByTryFinally() {
		final MockLock lock = new MockLock();
		OTCPriorityQueue<ITestElement> queue = new OTCPriorityQueue<ITestElement>() {
			@Override
			protected ITestElement getMaxElement() {
				throw new RuntimeException("simulated exception");
			}
		};

		queue.setLock(lock);

		try {
			queue.popMax();
			Assert.fail("Expected the simulated exception!");
		} catch (RuntimeException ex) {
			// Expected
		}
		assertThat(lock.locked).isEqualTo(true);
		assertThat(lock.unlocked).isEqualTo(true);
	}

	@Test
	public void shouldSynchronizeSizeRead() {
		final MockLock lock = new MockLock();
		final List<ITestElement> mockList = new ArrayList<ITestElement>() {
			@Override
			public int size() {
				assertThat(lock.locked).isEqualTo(true);
				assertThat(lock.unlocked).isEqualTo(false);
				return 0;
			}
		};
		OTCPriorityQueue<ITestElement> queue = new OTCPriorityQueue<ITestElement>() {
			@Override
			protected List<ITestElement> getElements() {
				return mockList;
			}
		};

		queue.setLock(lock);

		queue.size();

		assertThat(lock.locked).isEqualTo(true);
		assertThat(lock.unlocked).isEqualTo(true);
	}

	@Test
	public void shouldReadSizeLockAndUnlockBeGuardedByTryFinally() {
		final MockLock lock = new MockLock();
		OTCPriorityQueue<ITestElement> queue = new OTCPriorityQueue<ITestElement>() {
			@Override
			protected List<ITestElement> getElements() {
				throw new RuntimeException("simulated exception");
			}
		};

		queue.setLock(lock);

		try {
			queue.size();
			Assert.fail("Expected the simulated exception!");
		} catch (RuntimeException ex) {
			// Expected
		}
		assertThat(lock.locked).isEqualTo(true);
		assertThat(lock.unlocked).isEqualTo(true);
	}
}
