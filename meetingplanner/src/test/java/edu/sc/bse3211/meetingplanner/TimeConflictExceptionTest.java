package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimeConflictExceptionTest {

    @Test
    public void testDefaultConstructor() {
        TimeConflictException exception = new TimeConflictException();
        assertEquals(null, exception.getMessage());
    }

    @Test
    public void testMessageConstructor() {
        TimeConflictException exception = new TimeConflictException("Test Message");
        assertEquals("Test Message", exception.getMessage());
    }

    @Test
    public void testCauseConstructor() {
        Throwable cause = new Throwable("Test Cause");
        TimeConflictException exception = new TimeConflictException(cause);
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        Throwable cause = new Throwable("Test Cause");
        TimeConflictException exception = new TimeConflictException("Test Message", cause);
        assertEquals("Test Message", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

}
