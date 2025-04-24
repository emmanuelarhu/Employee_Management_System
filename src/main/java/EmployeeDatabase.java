package main.java;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeDatabase<T> {
    private final Map<T, Employee<T>> employees;

    public EmployeeDatabase() {
        this.employees = new HashMap<>();
    }

    public boolean addEmployee(Employee<T> employee) {
        if (employees.containsKey(employee.getEmployeeId())) {
            return false;
        }
        employees.put(employee.getEmployeeId(), employee);
        return true;
    }

    public boolean removeEmployee(T employeeId) {
        if (!employees.containsKey(employeeId)) {
            return false;
        }
        employees.remove(employeeId);
        return true;
    }

    public Optional<Employee<T>> getEmployee(T employeeId) {
        return Optional.ofNullable(employees.get(employeeId));
    }

    public boolean updateEmployeeDetails(T employeeId, String field, Object newValue) {
        Employee<T> employee = employees.get(employeeId);
        if (employee == null) {
            return false;
        }

        try {
            switch (field.toLowerCase()) {
                case "name":
                    employee.setName((String) newValue);
                    break;
                case "department":
                    employee.setDepartment((String) newValue);
                    break;
                case "salary":
                    if (newValue instanceof String) {
                        employee.setSalary(Double.parseDouble((String) newValue));
                    } else {
                        employee.setSalary((Double) newValue);
                    }
                    break;
                case "performancerating":
                    if (newValue instanceof String) {
                        employee.setPerformanceRating(Double.parseDouble((String) newValue));
                    } else {
                        employee.setPerformanceRating((Double) newValue);
                    }
                    break;
                case "yearsofexperience":
                    if (newValue instanceof String) {
                        employee.setYearsOfExperience(Integer.parseInt((String) newValue));
                    } else {
                        employee.setYearsOfExperience((Integer) newValue);
                    }
                    break;
                case "isactive":
                    if (newValue instanceof String) {
                        employee.setActive(Boolean.parseBoolean((String) newValue));
                    } else {
                        employee.setActive((Boolean) newValue);
                    }
                    break;
                default:
                    return false;
            }
            return true;
        } catch (ClassCastException | NumberFormatException e) {
            return false;
        }
    }

    public List<Employee<T>> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    public List<Employee<T>> getEmployeesByDepartment(String department) {
        return employees.values().stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    public List<Employee<T>> searchEmployeesByName(String searchTerm) {
        return employees.values().stream()
                .filter(e -> e.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Employee<T>> getEmployeesByMinRating(double minRating) {
        return employees.values().stream()
                .filter(e -> e.getPerformanceRating() >= minRating)
                .collect(Collectors.toList());
    }

    public List<Employee<T>> getEmployeesBySalaryRange(double minSalary, double maxSalary) {
        return employees.values().stream()
                .filter(e -> e.getSalary() >= minSalary && e.getSalary() <= maxSalary)
                .collect(Collectors.toList());
    }

    public List<Employee<T>> getEmployeesSortedByExperience() {
        List<Employee<T>> sortedList = new ArrayList<>(employees.values());
        Collections.sort(sortedList);
        return sortedList;
    }

    public List<Employee<T>> getEmployeesSortedBySalary() {
        List<Employee<T>> sortedList = new ArrayList<>(employees.values());
        sortedList.sort(new EmployeeComparators.EmployeeSalaryComparator<>());
        return sortedList;
    }

    public List<Employee<T>> getEmployeesSortedByPerformance() {
        List<Employee<T>> sortedList = new ArrayList<>(employees.values());
        sortedList.sort(new EmployeeComparators.EmployeePerformanceComparator<>());
        return sortedList;
    }

    public List<Employee<T>> getEmployeesSortedByName() {
        List<Employee<T>> sortedList = new ArrayList<>(employees.values());
        sortedList.sort(new EmployeeComparators.EmployeeNameComparator<>());
        return sortedList;
    }

    public List<Employee<T>> getEmployeesSortedByDepartment() {
        List<Employee<T>> sortedList = new ArrayList<>(employees.values());
        sortedList.sort(new EmployeeComparators.EmployeeDepartmentComparator<>());
        return sortedList;
    }

    public int giveSalaryRaise(double minRating, double percentage) {
        int count = 0;
        for (Employee<T> employee : employees.values()) {
            if (employee.getPerformanceRating() >= minRating) {
                double newSalary = employee.getSalary() * (1 + percentage / 100);
                employee.setSalary(newSalary);
                count++;
            }
        }
        return count;
    }

    public List<Employee<T>> getTopNHighestPaidEmployees(int n) {
        return employees.values().stream()
                .sorted(new EmployeeComparators.EmployeeSalaryComparator<>())
                .limit(n)
                .collect(Collectors.toList());
    }

    public double calculateAverageSalaryByDepartment(String department) {
        List<Employee<T>> deptEmployees = getEmployeesByDepartment(department);
        if (deptEmployees.isEmpty()) {
            return 0;
        }

        return deptEmployees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);
    }

    public List<Employee<T>> filterEmployees(Predicate<Employee<T>> predicate) {
        return employees.values().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public int getEmployeeCount() {
        return employees.size();
    }

    public Iterator<Employee<T>> getEmployeeIterator() {
        return employees.values().iterator();
    }

    public Map<String, Long> getDepartmentCounts() {
        return employees.values().stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.counting()
                ));
    }

    public void clearDatabase() {
        employees.clear();
    }
}