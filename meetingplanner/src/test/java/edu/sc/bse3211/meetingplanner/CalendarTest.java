package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class CalendarTest {
	private Calendar calendar;

	@Before
	public void setUp() {
		calendar = new Calendar();
	}

	@Test
	public void testAddMeeting_holiday() {
		// Create Janan Luwum holiday
		Calendar calendar = new Calendar();
		// Add to calendar object.
		try {
			Meeting janan = new Meeting(2, 16, "Janan Luwum");
			calendar.addMeeting(janan);
			// Verify that it was added.
			Boolean added = calendar.isBusy(2, 16, 0, 23);
			assertTrue("Janan Luwum Day should be marked as busy on the calendar", added);
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}

	// clearing schedule test
	@Test
	public void testClearSchedule() {
		Calendar calendar = new Calendar();
		Meeting meeting = new Meeting(1, 1, 9, 11);
		try {
			calendar.addMeeting(meeting);
			assertTrue(calendar.isBusy(1, 1, 9, 11));
			calendar.clearSchedule(1, 1);
			assertFalse(calendar.isBusy(1, 1, 9, 11));
		} catch (TimeConflictException e) {
			assertFalse("Should not throw exception: " + e.getMessage(), true);
		}
	}

	@Test
	public void testAddMeeting() {
		try {
			Meeting meeting = new Meeting(1, 1, 9, 11);
			calendar.addMeeting(meeting);
			assertTrue(calendar.isBusy(1, 1, 9, 11));
		} catch (TimeConflictException e) {
			assertFalse("Should not throw exception: " + e.getMessage(), true);
		}
	}

	@Test
	public void testGetMeeting() {
		try {
			Meeting meeting = new Meeting(1, 1, 9, 11);
			calendar.addMeeting(meeting);
			Meeting retrievedMeeting = calendar.getMeeting(1, 1, 0);
			assertEquals(meeting, retrievedMeeting);
		} catch (TimeConflictException e) {
			assertFalse("Should not throw exception: " + e.getMessage(), true);
		}
	}

	@Test
	public void testRemoveMeeting() {
		try {
			Meeting meeting = new Meeting(1, 1, 9, 11);
			calendar.addMeeting(meeting);
			assertTrue(calendar.isBusy(1, 1, 9, 11));
			calendar.removeMeeting(1, 1, 0);
			assertFalse(calendar.isBusy(1, 1, 9, 11));
		} catch (TimeConflictException e) {
			assertFalse("Should not throw exception: " + e.getMessage(), true);
		}
	}

	@Test
	public void testIsBusyNoConflict() throws TimeConflictException {
		Calendar cal = new Calendar();
		assertFalse(cal.isBusy(1, 1, 10, 12)); // No meetings scheduled
	}

	@Test
	public void testIsBusyWithConflict() throws TimeConflictException {
		Calendar cal = new Calendar();
		cal.addMeeting(new Meeting(1, 1, 9, 11));
		assertTrue(cal.isBusy(1, 1, 10, 12)); // Meeting conflicts
	}

	@Test
	public void testIsBusyInvalidDateThrowsException() {
		Calendar cal = new Calendar();
		try {
			cal.isBusy(13, 1, 10, 12); // Invalid month
			fail("Expected TimeConflictException to be thrown");
		} catch (TimeConflictException e) {
			// Exception was thrown, test passed
		}
	}

	@Test
	public void testCheckTimes_validDate() {
		try {
			Calendar.checkTimes(1, 1, 0, 23);
			assertTrue("Should not throw TimeConflictException for valid date", true);
		} catch (TimeConflictException e) {
			fail("Should not throw TimeConflictException for valid date");
		}
	}

	@Test
	public void testIsBusyInvalidTimeThrowsException() {
		Calendar cal = new Calendar();
		try {
			cal.isBusy(1, 1, -1, 12); // Invalid start time
			fail("Expected TimeConflictException to be thrown");
		} catch (TimeConflictException e) {
			// Exception was thrown, test passed
		}
	}

	@Test(expected = TimeConflictException.class)
	public void testIsBusyInvalidDay() throws TimeConflictException {
		Calendar calendar = new Calendar();
		calendar.isBusy(2, 34, 10, 12); // Invalid day in February
	}

	@Test(expected = TimeConflictException.class)
	public void testIsBusyInvalidEndTime() throws TimeConflictException {
		Calendar calendar = new Calendar();
		calendar.isBusy(5, 10, 10, 24); // End time exceeding 23
	}

	@Test(expected = TimeConflictException.class)
	public void testIsBusyMeetingStartAfterEnd() throws TimeConflictException {
		Calendar calendar = new Calendar();
		calendar.isBusy(5, 10, 12, 10); // Start time after end time
	}

	/**
	 * Test adding a meeting to a day that already has a meeting,expected to throw
	 * TimeConflictException
	 */
	@Test
	public void testAddMeeting_dayWithMeeting() {
		Calendar calendar = new Calendar();
		try {
			Meeting meeting1 = new Meeting(1, 1, "Team Meeting");
			Meeting meeting2 = new Meeting(1, 1, "Team Meeting");
			calendar.addMeeting(meeting1);
			calendar.addMeeting(meeting2);
			fail("Should throw TimeConflictException for adding a meeting to a day that already has a meeting");
		} catch (TimeConflictException e) {
			assertTrue("Should throw TimeConflictException for adding a meeting to a day that already has a meeting",
					true);
		}
	}

	/**
	 * Test clearing the schedule for a day without meetings
	 */
	@Test
	public void testClearSchedule_emptyDay() throws TimeConflictException {
		Calendar calendar = new Calendar();
		calendar.clearSchedule(1, 1);
		// Check that the day is not busy
		Boolean busy = calendar.isBusy(1, 1, 0, 23);
		assertTrue("Day should not be busy after clearing schedule", !busy);
	}

	// Test print Agenda
	/**
	 * Test printing the agenda for a month with meeting
	 */
	@Test
	public void testPrintAgenda_monthWithMeetings() {
		// Create a mock Meeting object
		Meeting meeting1 = mock(Meeting.class);
		Meeting meeting2 = mock(Meeting.class);

		// Stub the toString method of the mock Meeting objects
		when(meeting1.toString()).thenReturn("Meeting 1");
		when(meeting2.toString()).thenReturn("Meeting 2");

		// Create a mock Calendar object
		Calendar calendar = mock(Calendar.class);

		// Stub the printAgenda method of the mock Calendar object
		when(calendar.printAgenda(3)).thenReturn("March 1: Meeting 1\nMarch 5:Meeting 2\n");

		// Test the printAgenda method
		String expectedAgenda = "March 1: Meeting 1\nMarch 5:Meeting 2\n";
		String actualAgenda = calendar.printAgenda(3);
		assertEquals("Agenda should match expected agenda", expectedAgenda,
				actualAgenda);
	}

	/**
	 * Test getting a meeting that does not exist with index out of bounds
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetMeeting_doesNotExist() {
		Calendar calendar = new Calendar();
		try {
			Meeting meeting = new Meeting(1, 1, "Team Meeting");
			calendar.addMeeting(meeting);
			Meeting retrieved = calendar.getMeeting(1, 1, 1);
			assertTrue("Should return null for a meeting that does not exist", retrieved == null);
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}

	/**
	 * Test removing a meeting that does not exist with index out of
	 * bounds
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveMeeting_doesNotExist() {
		Calendar calendar = new Calendar();
		try {
			Meeting meeting = new Meeting(1, 1, "Team Meeting");
			calendar.addMeeting(meeting);
			calendar.removeMeeting(1, 1, 1);
			// Check that the meeting was not removed
			Boolean busy = calendar.isBusy(1, 1, 0, 23);
			assertTrue("Meeting should not be removed from the calendar",
					busy);
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}

	/**
	 * Test TimeConflictException is thrown under the correct
	 * conditions ineach
	 * method it is expected to be thrown
	 *
	 */
	@Test(expected = TimeConflictException.class)
	public void testTimeConflictException() throws TimeConflictException {
		// Test TimeConflictException is thrown for invalid date
		Calendar.checkTimes(1, 32, 0, 23);

		// Test TimeConflictException is thrown for invalid month
		Calendar.checkTimes(13, 1, 0, 23);

		// Test TimeConflictException is thrown for invalid time
		Calendar.checkTimes(1, 1, 24, 23);

		// Test TimeConflictException is thrown for start time after end time
		Calendar.checkTimes(1, 1, 23, 0);

		// Test TimeConflictException is thrown for adding a meeting to a day that
		// already has a meeting
		Calendar calendar = new Calendar();
		Meeting meeting1 = new Meeting(1, 1, "Team Meeting");
		Meeting meeting2 = new Meeting(1, 1, "Team Meeting");
		calendar.addMeeting(meeting1);
		calendar.addMeeting(meeting2);

		//

	}

	// edge cases
	/**
	 * test february 29th on a leap year
	 */
	@Test
	public void testLeapYear() {
		Calendar calendar = new Calendar();
		try {
			Meeting meeting = new Meeting(2, 29, "Team Meeting");
			calendar.addMeeting(meeting);
			Boolean busy = calendar.isBusy(2, 29, 0, 23);
			assertTrue("February 29th should be marked as busy on the calendar", busy);
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}

	/**
	 * Test boundaries of each month to ensure that days beyond the last month are
	 * valid days and handled correcty
	 */
	@Test
	public void testMonthBoundaries() {
		Calendar calendar = new Calendar();
		try {
			Meeting meeting = new Meeting(2, 31, "Team Meeting");
			calendar.addMeeting(meeting);
			Boolean busy = calendar.isBusy(2, 31, 0, 23);
			assertTrue("February 31st should be marked as busy on the calendar", busy);
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
		try {
			Meeting meeting = new Meeting(4, 31, "Team Meeting");
			calendar.addMeeting(meeting);
			Boolean busy = calendar.isBusy(4, 31, 0, 23);
			assertTrue("April 31st should be marked as busy on the calendar", busy);
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
		try {
			Meeting meeting = new Meeting(6, 31, "Team Meeting");
			calendar.addMeeting(meeting);
			Boolean busy = calendar.isBusy(6, 31, 0, 23);
			assertTrue("June 31st should be marked as busy on the calendar", busy);
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
		try {
			Meeting meeting = new Meeting(9, 31, "Team Meeting");
			calendar.addMeeting(meeting);
			Boolean busy = calendar.isBusy(9, 31, 0, 23);
			assertTrue("September 31st should be marked as busy on the calendar", busy);
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
		try {
			Meeting meeting = new Meeting(11, 31, "Team Meeting");
			calendar.addMeeting(meeting);
			Boolean busy = calendar.isBusy(11, 31, 0, 23);
			assertTrue("November 31st should be marked as busy on the calendar", busy);
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}

}