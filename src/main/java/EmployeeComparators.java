package main.java;

import java.util.Comparator;

public class EmployeeComparators {

    public static class EmployeeSalaryComparator<T> implements Comparator<Employee<T>> {
        @Override
        public int compare(Employee<T> e1, Employee<T> e2) {
            return Double.compare(e2.getSalary(), e1.getSalary());
        }
    }

    public static class EmployeePerformanceComparator<T> implements Comparator<Employee<T>> {
        @Override
        public int compare(Employee<T> e1, Employee<T> e2) {
            return Double.compare(e2.getPerformanceRating(), e1.getPerformanceRating());
        }
    }

    public static class EmployeeNameComparator<T> implements Comparator<Employee<T>> {
        @Override
        public int compare(Employee<T> e1, Employee<T> e2) {
            return e1.getName().compareToIgnoreCase(e2.getName());
        }
    }

    public static class EmployeeDepartmentComparator<T> implements Comparator<Employee<T>> {
        @Override
        public int compare(Employee<T> e1, Employee<T> e2) {
            return e1.getDepartment().compareToIgnoreCase(e2.getDepartment());
        }
    }
}