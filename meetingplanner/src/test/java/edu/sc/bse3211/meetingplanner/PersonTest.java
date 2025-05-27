package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class PersonTest {

    private Person person;
    private Meeting meeting;

    @Before
    public void setUp() {
        person = new Person("Acan Brenda");
        Room room = new Room("LLT6A"); // Mock Room object
        ArrayList<Person> attendees = new ArrayList<>(); // Mock attendees list
        attendees.add(person); // Add the person to the attendees list
        meeting = new Meeting(1, 1, 9, 11, attendees, room, "Team Meeting");
    }

    @Test
    public void testGetName() {
        assertEquals("Acan Brenda", person.getName());
    }

    @Test
    public void testAddMeeting() {
        try {
            person.addMeeting(meeting);
            assertTrue(person.isBusy(1, 1, 9, 11));
        } catch (TimeConflictException e) {
            assertFalse("Should not throw exception: " + e.getMessage(), true);
        }
    }

    @Test
    public void testPrintAgendaByMonth() {
        try {
            person.addMeeting(meeting);
            String agenda = person.printAgenda(1);
            assertTrue(agenda.contains("1/1"));
            assertTrue(agenda.contains("9 - 11"));
            assertTrue(agenda.contains("Team Meeting"));
            // assertTrue(agenda.contains("LLT6A"));
        } catch (TimeConflictException e) {
            assertFalse("Should not throw exception: " + e.getMessage(), true);
        }
    }

    @Test
    public void testPrintAgendaByDay() {
        try {
            person.addMeeting(meeting);
            String agenda = person.printAgenda(1, 1);
            assertTrue(agenda.contains("1/1"));
            assertTrue(agenda.contains("9 - 11"));
            assertTrue(agenda.contains("Team Meeting"));
        } catch (TimeConflictException e) {
            assertFalse("Should not throw exception: " + e.getMessage(), true);
        }
    }

    @Test
    public void testIsBusy() {
        try {
            person.addMeeting(meeting);
            assertTrue(person.isBusy(1, 1, 9, 11));
        } catch (TimeConflictException e) {
            assertFalse("Should not throw exception: " + e.getMessage(), true);
        }
    }

    @Test
    public void testGetMeeting() {
        try {
            person.addMeeting(meeting);
            Meeting retrievedMeeting = person.getMeeting(1, 1, 0);
            assertEquals(meeting, retrievedMeeting);
        } catch (TimeConflictException e) {
            assertFalse("Should not throw exception: " + e.getMessage(), true);
        }
    }

    @Test
    public void testRemoveMeeting() {
        try {
            person.addMeeting(meeting);
            assertTrue(person.isBusy(1, 1, 9, 11));
            person.removeMeeting(1, 1, 0);
            assertFalse(person.isBusy(1, 1, 9, 11));
        } catch (TimeConflictException e) {
            assertFalse("Should not throw exception: " + e.getMessage(), true);
        }
    }

    @Test(expected = TimeConflictException.class)
    public void testAddMeetingWithConflict() throws TimeConflictException {
        try {
            Person person = new Person("Acan Brenda");
            Meeting meeting1 = new Meeting(5, 15, "Going Home");
            Meeting meeting2 = new Meeting(5, 15, "meeting lecturer"); // This should conflict with meeting1
            person.addMeeting(meeting1);
            person.addMeeting(meeting2);
        } catch (TimeConflictException e) {
            throw new TimeConflictException("Conflict for attendee " + "Acan Brenda" + ":\n" + e.getMessage());
        }
    }
}
