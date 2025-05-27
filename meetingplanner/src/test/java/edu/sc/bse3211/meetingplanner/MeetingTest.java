package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class MeetingTest {

    private Meeting meeting;
    private Person attendee1;
    private Person attendee2;
    private Room room;

    @Before
    public void setUp() {
        attendee1 = new Person("Namugga Martha");
        attendee2 = new Person("Shema Collins");
        room = new Room("LAB2");
        meeting = new Meeting(1, 1, 9, 11, new ArrayList<Person>(), room, "Team Meeting");
    }

    @Test
    public void testAddAttendee() {
        meeting.addAttendee(attendee1);
        assertTrue(meeting.getAttendees().contains(attendee1));
    }

    @Test
    public void testRemoveAttendee() {
        meeting.addAttendee(attendee1);
        assertTrue(meeting.getAttendees().contains(attendee1));

        meeting.removeAttendee(attendee1);
        assertFalse(meeting.getAttendees().contains(attendee1));
    }

    @Test
    public void testToString() {
        meeting.addAttendee(attendee1);
        meeting.addAttendee(attendee2);

        String expected = "1/1, 9 - 11,LAB2: Team Meeting\nAttending: Namugga Martha,Shema Collins";
        assertEquals(expected, meeting.toString());
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(1, meeting.getMonth());
        assertEquals(1, meeting.getDay());
        assertEquals(9, meeting.getStartTime());
        assertEquals(11, meeting.getEndTime());
        assertEquals(room, meeting.getRoom());
        assertEquals("Team Meeting", meeting.getDescription());

        meeting.setMonth(2);
        meeting.setDay(2);
        meeting.setStartTime(10);
        meeting.setEndTime(12);
        meeting.setDescription("Project Kickoff Meeting");

        assertEquals(2, meeting.getMonth());
        assertEquals(2, meeting.getDay());
        assertEquals(10, meeting.getStartTime());
        assertEquals(12, meeting.getEndTime());
        assertEquals("Project Kickoff Meeting", meeting.getDescription());
    }

    @Test
    public void testDefaultConstructor() {
        Meeting defaultMeeting = new Meeting();
        assertNotNull(defaultMeeting);
    }

    @Test
    public void testConstructorForWholeDay() {
        Meeting wholeDayMeeting = new Meeting(1, 1);
        assertEquals(0, wholeDayMeeting.getStartTime());
        assertEquals(23, wholeDayMeeting.getEndTime());
    }

    /*
     * Test the above constructor with invalid month and Day
     */
    @Test
    public void ConstructorForWholeDayWithInvalidMonth() {
        Meeting meeting = new Meeting(13, 32);
        assertEquals(13, meeting.getMonth());
        assertEquals(32, meeting.getDay());
        assertEquals(0, meeting.getStartTime());
        assertEquals(23, meeting.getEndTime());
        assertNull(meeting.getDescription());
    }

    @Test
    public void testConstructorWithDescription() {
        Meeting descMeeting = new Meeting(1, 1, "Holiday");
        assertEquals("Holiday", descMeeting.getDescription());
    }

    // testing the more detailed constructor
    @Test
    public void testDetailedConstructor() {
        Room room = new Room("LAB2");
        ArrayList<Person> attendees = new ArrayList<>();
        attendees.add(new Person("Namugga Martha"));
        Meeting meeting = new Meeting(5, 12, 10, 14, attendees, room, "Project Discussion");
        assertEquals(5, meeting.getMonth());
        assertEquals(12, meeting.getDay());
        assertEquals(10, meeting.getStartTime());
        assertEquals(14, meeting.getEndTime());
        assertEquals(room, meeting.getRoom());
        assertEquals("Project Discussion", meeting.getDescription());
        assertEquals(1, meeting.getAttendees().size());
        assertEquals("Namugga Martha", meeting.getAttendees().get(0).getName());
    }

    /**
     * Test Detailed constructor with invalid Time range e.g start time > end time
     */
    @Test
    public void detailedConstructorWithInvalidTimeRange() {
        Meeting meeting = new Meeting(2, 16, 10, 8);
        assertEquals(2, meeting.getMonth());
        assertEquals(16, meeting.getDay());
        assertEquals(10, meeting.getStartTime());
        assertEquals(8, meeting.getEndTime());
        assertNull(meeting.getDescription());
    }

}
