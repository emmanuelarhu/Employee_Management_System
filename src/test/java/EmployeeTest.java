package test.java;

import main.java.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee(1, "John", "Doe", 50000.0, 40.0, 101, false);
    }

    @Test
    public void testEmployeeCreation() {
        assertEquals(1, employee.getEmployeeId());
        assertEquals("John", employee.getName());
        assertEquals("Doe", employee.getLastName());
        assertEquals(5000.0, employee.getSalary());
        assertEquals(40.0, employee.getPerformanceRating());
        assertEquals(101, employee.getDepartment());
        assertFalse(employee.isActive());
    }

    @Test
    public void testSetSalary() {
        Employee employee = new Employee(1, "John", "Doe", 50000.0, 40.0, 101, false);

        employee.setSalary(55000.0);
        assertEquals(55000.0, employee.getSalary());
    }

    @Test
    public void testGetFullName() {
        Employee employee = new Employee(1, "John", "Doe", 50000.0, 40.0, 101, false);

        assertEquals("John Doe", employee.getName());
    }

    @Test
    public void testPromoteToManager() {
        Employee employee = new Employee(1, "John", "Doe", 50000.0, 40.0, 101, false);

        employee.setPerformanceRating(40.0);
        assertTrue(employee.isActive());
    }
}
