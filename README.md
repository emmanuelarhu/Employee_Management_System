# Employee_Management_System

A comprehensive JavaFX desktop application for managing employee data, performance, and departmental information.

![Employee_Management_System Screenshot](![alt text](image.png))

## Features

- **Employee Data Management**: Add, edit, view, and delete employee records
- **Comprehensive Employee Profiles**: Track essential employee information including:
  - Personal details
  - Department assignments
  - Salary information
  - Performance ratings
  - Years of experience
  - Active/inactive status
- **Advanced Filtering & Sorting**: Easily find and organize employee records
- **Performance Analytics**: Identify top performers and highest-paid employees
- **Automated Actions**: Apply batch operations like performance-based raises
- **Reporting Tools**: Generate department-specific reports

## User Guide

### Getting Started
1. **Launch the application**: Double-click the JAR file or run it from the command line
2. **Main Interface**: The application loads with the employee table displaying all current employees

### Managing Employees
1. **View Employees**: The main table displays all employee information
2. **Search**: Enter text in the search box to filter employees by name
3. **Sort**: Click the "Sort by" dropdown and select a field to sort the employee list
4. **Filter**: Use the filter dropdown to view specific employee groups

### Adding a New Employee
1. Click the "Clear" button in the Add/Edit Employee panel
2. Fill in the employee details:
   - Name: Enter the employee's full name
   - Department: Select or enter the department name
   - Salary: Enter the annual salary
   - Rating: Enter performance rating (0-5 scale)
   - Experience: Enter years of experience
   - Active Employee: Check if the employee is currently active
3. Click "Save" to add the employee to the system

### Editing an Employee
1. Click on an employee row in the table
2. Their information will populate in the Add/Edit Employee form
3. Modify the necessary fields
4. Click "Save" to update the employee record

### Deleting Employees
1. Select one or more employees in the table
2. Click the "Delete Selected" button
3. Confirm the deletion when prompted

### Quick Actions
- **Show Top 5 Paid**: Displays the five employees with the highest salaries
- **Show Top Performers**: Displays employees with the highest performance ratings
- **Give 10% Raise to Top Performers**: Automatically increases the salary of top-rated employees by 10%
- **Generate Department Report**: Creates a detailed report of employees grouped by department

## Technical Details

### System Requirements
- Java 11 or higher
- JavaFX 11 or higher
- 4GB RAM recommended
- 100MB available disk space

### Installation

1. Download the latest release from the [Releases](https://github.com/emmanuelarhu/Employee_Management_System/releases) page
2. Ensure Java 11+ is installed on your system
3. Run the application using:
   ```
   java -jar EmployeeManagementSystem.jar
   ```

### Building from Source

```bash
# Clone the repository
git clone https://github.com/emmanuelarhu/Employee_Management_System.git

# Navigate to project directory
cd employee-management-system

# Build with Maven
mvn clean package

# Run the application
java -jar target/Employee_Management_System.jar
```

## Technology Stack

- **JavaFX**: UI framework
- **Java 11**: Programming language
- **Maven/Gradle**: Build automation
- **SQLite/H2**: Local database (optional)
- **JUnit 5**: Testing framework

## Database Schema

The application uses a local database with the following schema:

| Column | Type | Description |
|--------|------|-------------|
| ID | Integer | Primary key, auto-incremented |
| Name | String | Employee's full name |
| Department | String | Department assignment |
| Salary | Decimal | Annual salary amount |
| Rating | Decimal | Performance rating (0-5 scale) |
| Experience | Integer | Years of relevant experience |
| Active | Boolean | Current employment status |

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Built with JavaFX framework
- Icons from [Icon Source]
- Special thanks to all contributors
