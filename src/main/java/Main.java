package main.java;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Application {
    // Employee database
    private final EmployeeDatabase<Integer> database = new EmployeeDatabase<>();

    // UI components
    private TableView<Employee<Integer>> employeeTable;
    private ObservableList<Employee<Integer>> employeeData;
    private ComboBox<String> sortComboBox;
    private ComboBox<String> filterComboBox;
    private TextField searchField;
    private TextField idField;
    private TextField nameField;
    private TextField departmentField;
    private TextField salaryField;
    private TextField ratingField;
    private TextField experienceField;
    private CheckBox activeCheckbox;
    private Label statusLabel;

    // Track the next available ID
    private int nextAvailableId = 1;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Add sample data
        addSampleData();

        // Create components
        HBox topControls = createTopControls();
        HBox tableSection = createTableSection();
        HBox statusBar = createStatusBar();

        root.setTop(topControls);
        root.setCenter(tableSection);
        root.setBottom(statusBar);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Employee Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createTopControls() {
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(0, 0, 10, 0));

        Label searchLabel = new Label("Search:");
        searchField = new TextField();
        searchField.setPrefWidth(150);

        Label sortLabel = new Label("Sort by:");
        sortComboBox = new ComboBox<>();
        sortComboBox.getItems().addAll(
                "Name", "Department", "Salary", "Performance", "Experience"
        );
        sortComboBox.setValue("Name");

        Label filterLabel = new Label("Filter:");
        filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll(
                "All Employees", "Active Only", "High Performers (≥4.0)",
                "IT Department", "HR Department", "Finance Department"
        );
        filterComboBox.setValue("All Employees");

        Button applyButton = new Button("Apply");
        applyButton.setOnAction(e -> applyFiltersAndSort());

        topBar.getChildren().addAll(
                searchLabel, searchField, sortLabel, sortComboBox,
                filterLabel, filterComboBox, applyButton
        );

        return topBar;
    }

    private HBox createTableSection() {
        HBox container = new HBox(10);

        // Employee table
        VBox tableContainer = new VBox(10);
        tableContainer.setPrefWidth(550);

        employeeTable = new TableView<>();
        employeeTable.setPrefHeight(450);

        TableColumn<Employee<Integer>, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        idCol.setPrefWidth(40);

        TableColumn<Employee<Integer>, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(120);

        TableColumn<Employee<Integer>, String> deptCol = new TableColumn<>("Department");
        deptCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        deptCol.setPrefWidth(100);

        TableColumn<Employee<Integer>, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        salaryCol.setPrefWidth(80);

        TableColumn<Employee<Integer>, Double> ratingCol = new TableColumn<>("Rating");
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("performanceRating"));
        ratingCol.setPrefWidth(60);

        TableColumn<Employee<Integer>, Integer> expCol = new TableColumn<>("Experience");
        expCol.setCellValueFactory(new PropertyValueFactory<>("yearsOfExperience"));
        expCol.setPrefWidth(80);

        TableColumn<Employee<Integer>, Boolean> activeCol = new TableColumn<>("Active");
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        activeCol.setPrefWidth(60);

        employeeTable.getColumns().addAll(
                idCol, nameCol, deptCol, salaryCol, ratingCol, expCol, activeCol
        );

        employeeData = FXCollections.observableArrayList(database.getAllEmployees());
        employeeTable.setItems(employeeData);

        employeeTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        populateForm(newVal);
                    }
                });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> deleteSelectedEmployee());

        tableContainer.getChildren().addAll(employeeTable, deleteButton);

        // Employee form
        VBox formContainer = new VBox(10);
        formContainer.setPadding(new Insets(0, 0, 0, 10));

        Label formTitle = new Label("Add/Edit Employee");
        formTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        GridPane form = new GridPane();
        form.setVgap(8);
        form.setHgap(10);

        idField = new TextField();
        idField.setPromptText("Auto-generated");
        idField.setEditable(false);

        nameField = new TextField();
        nameField.setPromptText("Full Name");

        departmentField = new TextField();
        departmentField.setPromptText("Department");

        salaryField = new TextField();
        salaryField.setPromptText("Salary");

        ratingField = new TextField();
        ratingField.setPromptText("Rating (0-5)");

        experienceField = new TextField();
        experienceField.setPromptText("Years");

        activeCheckbox = new CheckBox("Active Employee");
        activeCheckbox.setSelected(true);

        form.add(new Label("ID:"), 0, 0);
        form.add(idField, 1, 0);
        form.add(new Label("Name:"), 0, 1);
        form.add(nameField, 1, 1);
        form.add(new Label("Department:"), 0, 2);
        form.add(departmentField, 1, 2);
        form.add(new Label("Salary:"), 0, 3);
        form.add(salaryField, 1, 3);
        form.add(new Label("Rating:"), 0, 4);
        form.add(ratingField, 1, 4);
        form.add(new Label("Experience:"), 0, 5);
        form.add(experienceField, 1, 5);
        form.add(activeCheckbox, 1, 6);

        HBox formButtons = new HBox(10);
        Button saveButton = new Button("Save");
        Button clearButton = new Button("Clear");

        saveButton.setOnAction(e -> saveEmployee());
        clearButton.setOnAction(e -> clearForm());

        formButtons.getChildren().addAll(saveButton, clearButton);

        // Analytics buttons
        VBox analytics = new VBox(8);
        analytics.setPadding(new Insets(15, 0, 0, 0));

        Label analyticsTitle = new Label("Quick Actions");
        analyticsTitle.setStyle("-fx-font-weight: bold;");

        Button topPaidBtn = new Button("Show Top 5 Paid");
        topPaidBtn.setMaxWidth(Double.MAX_VALUE);
        topPaidBtn.setOnAction(e -> showTopPaid());

        Button topPerformersBtn = new Button("Show Top Performers");
        topPerformersBtn.setMaxWidth(Double.MAX_VALUE);
        topPerformersBtn.setOnAction(e -> showTopPerformers());

        Button raiseBtn = new Button("Give 10% Raise to Top Performers");
        raiseBtn.setMaxWidth(Double.MAX_VALUE);
        raiseBtn.setOnAction(e -> giveRaise());

        Button reportBtn = new Button("Generate Department Report");
        reportBtn.setMaxWidth(Double.MAX_VALUE);
        reportBtn.setOnAction(e -> generateDepartmentReport());

        analytics.getChildren().addAll(
                analyticsTitle, topPaidBtn, topPerformersBtn, raiseBtn, reportBtn
        );

        formContainer.getChildren().addAll(formTitle, form, formButtons, analytics);

        container.getChildren().addAll(tableContainer, formContainer);
        return container;
    }

    private HBox createStatusBar() {
        HBox statusBar = new HBox(10);
        statusBar.setPadding(new Insets(10, 0, 0, 0));

        statusLabel = new Label("Ready. " + database.getEmployeeCount() + " employees loaded.");
        statusBar.getChildren().add(statusLabel);

        return statusBar;
    }

    private void populateForm(Employee<Integer> employee) {
        idField.setText(employee.getEmployeeId().toString());
        nameField.setText(employee.getName());
        departmentField.setText(employee.getDepartment());
        salaryField.setText(String.valueOf(employee.getSalary()));
        ratingField.setText(String.valueOf(employee.getPerformanceRating()));
        experienceField.setText(String.valueOf(employee.getYearsOfExperience()));
        activeCheckbox.setSelected(employee.isActive());
    }

    private void clearForm() {
        idField.clear();
        nameField.clear();
        departmentField.clear();
        salaryField.clear();
        ratingField.clear();
        experienceField.clear();
        activeCheckbox.setSelected(true);
        employeeTable.getSelectionModel().clearSelection();
    }

    private void saveEmployee() {
        try {
            String name = nameField.getText().trim();
            String department = departmentField.getText().trim();
            String salaryText = salaryField.getText().trim();
            String ratingText = ratingField.getText().trim();
            String experienceText = experienceField.getText().trim();
            boolean isActive = activeCheckbox.isSelected();

            // Basic validation
            if (name.isEmpty() || department.isEmpty() || salaryText.isEmpty() ||
                    ratingText.isEmpty() || experienceText.isEmpty()) {
                showAlert("All fields are required!");
                return;
            }

            double salary = Double.parseDouble(salaryText);
            double rating = Double.parseDouble(ratingText);
            int experience = Integer.parseInt(experienceText);

            // Save logic
            String idText = idField.getText().trim();
            if (idText.isEmpty()) {
                // Create new employee with next available ID
                Employee<Integer> newEmployee = new Employee<>(
                        nextAvailableId, name, department, salary, rating, experience, isActive
                );
                database.addEmployee(newEmployee);
                updateStatus("Employee added: " + name);

                // Increment the next available ID
                nextAvailableId++;
            } else {
                // Update existing employee
                int id = Integer.parseInt(idText);
                Employee<Integer> updatedEmployee = new Employee<>(
                        id, name, department, salary, rating, experience, isActive
                );
                database.removeEmployee(id);
                database.addEmployee(updatedEmployee);
                updateStatus("Employee updated: " + name);
            }

            refreshTable();
            clearForm();

        } catch (NumberFormatException e) {
            showAlert("Invalid numeric values. Please check salary, rating and experience fields.");
        }
    }

    private void deleteSelectedEmployee() {
        Employee<Integer> selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Remove the employee
            database.removeEmployee(selected.getEmployeeId());
            refreshTable();
            clearForm();
            updateStatus("Employee removed: " + selected.getName());

            // No need to adjust IDs - we maintain unique IDs throughout the application lifecycle
        } else {
            showAlert("No employee selected!");
        }
    }

    private void applyFiltersAndSort() {
        List<Employee<Integer>> filteredEmployees;

        // Apply filters
        String filterValue = filterComboBox.getValue();
        filteredEmployees = switch (filterValue) {
            case "Active Only" -> database.filterEmployees(Employee::isActive);
            case "High Performers (≥4.0)" -> database.getEmployeesByMinRating(4.0);
            case "IT Department" -> database.getEmployeesByDepartment("IT");
            case "HR Department" -> database.getEmployeesByDepartment("HR");
            case "Finance Department" -> database.getEmployeesByDepartment("Finance");
            default -> database.getAllEmployees();
        };

        // Apply search if text exists
        String searchText = searchField.getText().trim();
        if (!searchText.isEmpty()) {
            filteredEmployees = filteredEmployees.stream()
                    .filter(e -> e.getName().toLowerCase().contains(searchText.toLowerCase()))
                    .toList();
        }

        // Apply sorting
        String sortValue = sortComboBox.getValue();
        filteredEmployees = switch (sortValue) {
            case "Name" -> filteredEmployees.stream()
                    .sorted(new EmployeeComparators.EmployeeNameComparator<>())
                    .toList();
            case "Department" -> filteredEmployees.stream()
                    .sorted(new EmployeeComparators.EmployeeDepartmentComparator<>())
                    .toList();
            case "Salary" -> filteredEmployees.stream()
                    .sorted(new EmployeeComparators.EmployeeSalaryComparator<>())
                    .toList();
            case "Performance" -> filteredEmployees.stream()
                    .sorted(new EmployeeComparators.EmployeePerformanceComparator<>())
                    .toList();
            case "Experience" -> filteredEmployees.stream()
                    .sorted()
                    .toList();
            default -> filteredEmployees;
        };

        employeeData.setAll(filteredEmployees);
        updateStatus("Found " + filteredEmployees.size() + " employees matching criteria");
    }

    private void showTopPaid() {
        List<Employee<Integer>> topPaid = database.getTopNHighestPaidEmployees(5);
        employeeData.setAll(topPaid);
        updateStatus("Showing top 5 highest paid employees");
    }

    private void showTopPerformers() {
        List<Employee<Integer>> topPerformers = database.getEmployeesByMinRating(4.5);
        employeeData.setAll(topPerformers);
        updateStatus("Showing employees with rating ≥ 4.5");
    }

    private void giveRaise() {
        int count = database.giveSalaryRaise(4.5, 10);
        refreshTable();
        updateStatus("Gave 10% raise to " + count + " top performers");
    }

    private void generateDepartmentReport() {
        // Example of using iterators and streaming for report generation
        StringBuilder report = new StringBuilder("Department Report:\n\n");

        // Get department statistics using streams
        Map<String, Long> deptCounts = database.getDepartmentCounts();
        Map<String, Double> avgSalaries = new HashMap<>();

        // Calculate average salary per department
        for (String dept : deptCounts.keySet()) {
            double avgSalary = database.calculateAverageSalaryByDepartment(dept);
            avgSalaries.put(dept, avgSalary);
        }

        // Generate report using iterator
        for (String dept : deptCounts.keySet()) {
            long count = deptCounts.get(dept);
            double avgSalary = avgSalaries.get(dept);

            report.append(String.format("Department: %s\n", dept));
            report.append(String.format("- Employee Count: %d\n", count));
            report.append(String.format("- Average Salary: $%.2f\n\n", avgSalary));
        }

        // Display report in a dialog
        Alert reportDialog = new Alert(Alert.AlertType.INFORMATION);
        reportDialog.setTitle("Department Report");
        reportDialog.setHeaderText("Employee Statistics by Department");

        TextArea textArea = new TextArea(report.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefHeight(300);
        textArea.setPrefWidth(400);

        reportDialog.getDialogPane().setContent(textArea);
        reportDialog.showAndWait();
    }

    private void refreshTable() {
        employeeData.setAll(database.getAllEmployees());
    }

    private void updateStatus(String message) {
        statusLabel.setText(message + " (" + database.getEmployeeCount() + " total employees)");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void addSampleData() {
        database.addEmployee(new Employee<>(1, "Emmanuel Arhu", "IT", 75000, 4.5, 5, true));
        database.addEmployee(new Employee<>(2, "Sarah Johnson", "HR", 65000, 4.7, 7, true));
        database.addEmployee(new Employee<>(3, "Michael Brown", "Finance", 82000, 3.9, 4, true));
        database.addEmployee(new Employee<>(4, "Emily Davis", "IT", 78000, 4.2, 6, true));
        database.addEmployee(new Employee<>(5, "David Wilson", "Marketing", 61000, 4.0, 3, true));
        database.addEmployee(new Employee<>(6, "Jennifer Taylor", "HR", 59000, 3.8, 2, false));
        database.addEmployee(new Employee<>(7, "Robert Martinez", "Finance", 85000, 4.6, 8, true));
        database.addEmployee(new Employee<>(8, "Lisa Anderson", "IT", 72000, 4.1, 4, true));
        database.addEmployee(new Employee<>(9, "James Thomas", "Marketing", 63000, 3.5, 2, false));
        database.addEmployee(new Employee<>(10, "Patricia Robinson", "Finance", 79000, 4.3, 5, true));

        // Set the next available ID to be one more than the highest ID
        nextAvailableId = 11; // Since we added employees with IDs 1-10
    }

    public static void main(String[] args) {
        launch(args);
    }
}