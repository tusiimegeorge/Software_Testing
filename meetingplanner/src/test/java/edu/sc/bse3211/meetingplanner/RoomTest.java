package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class RoomTest {

    private Room room;

    @Before
    public void setUp() {
        room = new Room("LLT6A");
    }

    @Test
    public void testDefaultConstructor() {
        Room room = new Room();
        assertNotNull(room);
    }

    @Test
    public void testParameterizedConstructor() {
        Room room = new Room("LLT6B");
        assertEquals("LLT6B", room.getID());
        assertNotNull(room);
    }

    @Test
    public void testGetID() {
        assertEquals("LLT6A", room.getID());
    }

    @Test
    public void testAddMeeting() {
        try {
            Meeting meeting = new Meeting(1, 1, 9, 11);
            room.addMeeting(meeting);
            assertTrue(room.isBusy(1, 1, 9, 11));
        } catch (TimeConflictException e) {
            assertFalse("Should not throw exception: " + e.getMessage(), true);
        }
    }

    @Test
    public void testIsBusy() {
        try {
            Meeting meeting = new Meeting(1, 1, 9, 11);
            room.addMeeting(meeting);
            assertTrue(room.isBusy(1, 1, 9, 11));
        } catch (TimeConflictException e) {
            assertFalse("Should not throw exception: " + e.getMessage(), true);
        }
    }

    @Test
    public void testGetMeeting() {
        try {
            Meeting meeting = new Meeting(1, 1, 9, 11);
            room.addMeeting(meeting);
            Meeting retrievedMeeting = room.getMeeting(1, 1, 0);
            assertEquals(meeting, retrievedMeeting);
        } catch (TimeConflictException e) {
            assertFalse("Should not throw exception: " + e.getMessage(), true);
        }
    }

    @Test
    public void testRemoveMeeting() {
        try {
            Meeting meeting = new Meeting(1, 1, 9, 11);
            room.addMeeting(meeting);
            assertTrue(room.isBusy(1, 1, 9, 11));
            room.removeMeeting(1, 1, 0);
            assertFalse(room.isBusy(1, 1, 9, 11));
        } catch (TimeConflictException e) {
            assertFalse("Should not throw exception: " + e.getMessage(), true);
        }
    }

}
