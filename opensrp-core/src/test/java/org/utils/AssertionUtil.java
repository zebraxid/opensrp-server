package org.utils;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class AssertionUtil {

	private AssertionUtil() {

	}

	public static <T> void assertTwoListAreSameIgnoringOrder(List<T> expectedList, List<T> actualList) {
		assertEquals(expectedList.size(), actualList.size());
		assertTrue("expected: " + expectedList.toString() + "\n" + "actual: " + actualList.toString(),
				expectedList.containsAll(actualList) && actualList.containsAll(expectedList));
	}

}
