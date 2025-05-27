package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class OrganizationTest {

    private Organization organization;

    @Before
    public void setUp() {
        organization = new Organization();
    }

    @Test
    public void testGetEmployees() {
        ArrayList<Person> employees = organization.getEmployees();
        assertNotNull(employees);
        assertEquals(5, employees.size());
    }

    @Test
    public void testGetRooms() {
        ArrayList<Room> rooms = organization.getRooms();
        assertNotNull(rooms);
        assertEquals(5, rooms.size());
    }

    @Test
    public void testGetRoom() {
        try {
            Room room = organization.getRoom("LLT6A");
            assertNotNull(room);
            assertEquals("LLT6A", room.getID());
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetRoomNonExistent() {
        try {
            organization.getRoom("LAB1");
            fail("Expected exception to be thrown");
        } catch (Exception e) {
            assertEquals("Requested room does not exist", e.getMessage());
        }
    }

    @Test
    public void testGetEmployee() {
        try {
            Person employee = organization.getEmployee("Namugga Martha");
            assertNotNull(employee);
            assertEquals("Namugga Martha", employee.getName());
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetEmployeeNonExistent() {
        try {
            organization.getEmployee("Naggita Ethel");
            fail("Expected exception to be thrown");
        } catch (Exception e) {
            assertEquals("Requested employee does not exist", e.getMessage());
        }
    }

    // constructor test
    @Test
    public void testOrganizationConstructor() {
        Organization organization = new Organization();

        // Check that employees list is initialized and contains expected employees
        ArrayList<Person> employees = organization.getEmployees();
        assertNotNull(employees);
        assertEquals(5, employees.size());
        assertEquals("Namugga Martha", employees.get(0).getName());
        assertEquals("Shema Collins", employees.get(1).getName());
        assertEquals("Acan Brenda", employees.get(2).getName());
        assertEquals("Kazibwe Julius", employees.get(3).getName());
        assertEquals("Kukunda Lynn", employees.get(4).getName());

    }

    // exception test
    /**
     * Test that the getEmployee method throws an exception when passed a null name
     */
    @Test
    public void testGetEmployeeWithNullName() {
        Organization organization = new Organization();
        Person employee = null;
        try {
            // Test with a null name
            employee = organization.getEmployee(null);
            fail("Expected an exception to be thrown");
        } catch (Exception e) {
            assertEquals("Requested employee does not exist", e.getMessage());
        }
        assertNull(employee);
    }

    // integration test
    /**
     * TestOrganizationIntegration: Test the interaction between getRoom,
     * getEmployee, and the constructor to ensure they work together as expected.
     */
    @Test
    public void testOrganizationIntegration() {
        Organization organization = new Organization();
        ArrayList<Person> employees = organization.getEmployees();
        ArrayList<Room> rooms = organization.getRooms();
        assertNotNull(employees);
        assertNotNull(rooms);
        assertEquals(5, employees.size());
        assertEquals(5, rooms.size());
        assertEquals("Namugga Martha", employees.get(0).getName());
        assertEquals("Shema Collins", employees.get(1).getName());
        assertEquals("Acan Brenda", employees.get(2).getName());
        assertEquals("Kazibwe Julius", employees.get(3).getName());
        assertEquals("Kukunda Lynn", employees.get(4).getName());
        assertEquals("LLT6A", rooms.get(0).getID());
        assertEquals("LLT6B", rooms.get(1).getID());
        assertEquals("LLT3A", rooms.get(2).getID());
        assertEquals("LLT2C", rooms.get(3).getID());
        assertEquals("LAB2", rooms.get(4).getID());
    }
}
