package com.aurionpro.test;

import com.aurionpro.model.Employee;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeeSerializationTest {

	private static List<Employee> employeeList = new ArrayList<>();

	public static List<Employee> findEmployeesInDepartment(List<Employee> employees, int targetDepartmentNo) {
		List<Employee> employeesInDepartment = new ArrayList<>();
		for (Employee employee : employees) {
			if (employee.getDepartmentNo() == targetDepartmentNo) {
				employeesInDepartment.add(employee);
			}
		}
		return employeesInDepartment;
	}

	private static void serializeToFile(List<Employee> employees) {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("employees.ser"))) {
			outputStream.writeObject(employees);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<Employee> deserializeFromFile() {
		List<Employee> employees = new ArrayList<>();
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("employees.ser"))) {
			employees = (List<Employee>) inputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return employees;
	}

	public static Employee findImmediateManager(List<Employee> employees, int employeeId) {
		for (Employee employee : employees) {
			if (employee.getId() == employeeId) {
				int managerId = employee.getManagerId();
				for (Employee potentialManager : employees) {
					if (potentialManager.getId() == managerId) {
						return potentialManager;
					}
				}
			}
		}
		return null;
	}

	private static Date parseDate(String dateStr) {
		try {
			return new SimpleDateFormat("dd-MMM-yy").parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void serializeToFile1(List<Employee> employees) {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("employees.ser"))) {
			outputStream.writeObject(employees);
			System.out.println("Serialized employee list to file.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<Employee> deserializeFromFile1() {
		List<Employee> employees = new ArrayList<>();
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("employees.ser"))) {
			employees = (List<Employee>) inputStream.readObject();
			System.out.println("Deserialized employee list from file.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return employees;
	}

	public static void main(String[] args) {
		String fileName = "D:\\Aurionpro Java\\Core Java\\48-Test2-Serialization\\src\\com\\aurionpro\\test\\Employeedetails";
		List<Employee> employeeList = new ArrayList<>();

		try {
			Path filePath = Paths.get(fileName);
			Scanner scanner = new Scanner(filePath);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(",");

				if (parts.length == 8) {
					int id = Integer.parseInt(parts[0]);
					String name = parts[1].replace("'", "");
					String role = parts[2].replace("'", "");

					int managerId = parts[3].equals("NULL") ? 0 : Integer.parseInt(parts[3]);

					Date dateOfJoining = new SimpleDateFormat("dd-MMM-yy").parse(parts[4].replace("'", ""));

					double salary = Double.parseDouble(parts[5]);
					double commission = parts[6].equals("NULL") ? 0.0 : Double.parseDouble(parts[6]);

					int departmentNo = parts[7].equals("NULL") ? 0 : Integer.parseInt(parts[7]);

					// Create an Employee object and add it to the list
					Employee employee = new Employee(id, name, role, managerId, dateOfJoining, salary, commission,
							departmentNo);
					employeeList.add(employee);
				}
			}

			scanner.close();
		} catch (IOException | ParseException e) {
			System.out.println("An error occurred: " + e.getMessage());
		}

		// List of Employee objects
		System.out.println("List of employee details: ");
		for (Employee employee : employeeList) {
			System.out.println(employee);
		}

		System.out.println("---------------------------------------------------------------------------------------");

		// Find all employees in a department
		int targetDepartmentNo = 30;
		List<Employee> employeesInDepartment = findEmployeesInDepartment(employeeList, targetDepartmentNo);
		System.out.println("Printing employee details by department: ");
		System.out.println("Employees in Department " + targetDepartmentNo + ":");
		for (Employee employee : employeesInDepartment) {
			System.out.println(employee);
		}

		System.out.println("---------------------------------------------------------------------------------------");

		// Find count of employees in each department
		Map<Integer, Integer> departmentEmployeeCount = new HashMap<>();
		// Count employees in each department
		for (Employee employee : employeeList) {
			int departmentNo = employee.getDepartmentNo();
			departmentEmployeeCount.put(departmentNo, departmentEmployeeCount.getOrDefault(departmentNo, 0) + 1);
		}
		// Print the count of employees in each department
		System.out.println("Employee count in each department:");
		for (Map.Entry<Integer, Integer> entry : departmentEmployeeCount.entrySet()) {
			System.out.println("Department " + entry.getKey() + ": " + entry.getValue() + " employees");
		}

		System.out.println("---------------------------------------------------------------------------------------");

		// Add/Delete employee details
		try {
			// Add a new employee
			Employee newEmployee = new Employee(8001, "Siddhi", "NEW EMPLOYEE", 7839, parseDate("01-JAN-22"), 1000, 0.0,
					10);
			employeeList.add(newEmployee);
			System.out.println("New employee details added");

			// Remove an employee by ID
			int employeeIdToRemove = 7698;
			Employee employeeToRemove = null;
			for (Employee employee : employeeList) {
				if (employee.getId() == employeeIdToRemove) {
					employeeToRemove = employee;
					break;
				}
			}
			if (employeeToRemove != null) {
				employeeList.remove(employeeToRemove);
				System.out.println("Employee with ID " + employeeIdToRemove + " removed.");
			} else {
				System.out.println("Employee with ID " + employeeIdToRemove + " not found.");
			}

			// Serialize and write the updated employee list to a file
			serializeToFile(employeeList);

			// De-serialize employees from the file and update the list
			List<Employee> deserializedEmployees = deserializeFromFile();
			if (deserializedEmployees != null) {
				employeeList = deserializedEmployees;
				System.out.println("Deserialized and updated employee list.");
			} else {
				System.out.println("Deserialization failed or returned null.");
			}

			// Print the updated list of employees
			System.out.println("Updated list of employees:");
			for (Employee employee : employeeList) {
				System.out.println(employee);
			}

			// Write the updated employee list back to the text file
			try (PrintWriter writer = new PrintWriter(fileName)) {
				for (Employee employee : employeeList) {
					String formattedDate = new SimpleDateFormat("dd-MMM-yy").format(employee.getDateOfJoining());
					String line = employee.getId() + ",'" + employee.getName() + "','" + employee.getRole() + "',"
							+ employee.getManagerId() + ",'" + formattedDate + "'," + employee.getSalary() + ","
							+ employee.getCommission() + "," + employee.getDepartmentNo();
					writer.println(line);
				}
				System.out.println("Updated employee data written back to the text file.");
			} catch (IOException e) {
				System.out.println("Error writing to the text file: " + e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("-------------------------------------------------------------------------------------");

		// Find the immediate manager of the employee
		int employeeIdToFind = 7698;
		Employee immediateManager = findImmediateManager(employeeList, employeeIdToFind);
		if (immediateManager != null) {
			System.out.println("Immediate manager of employee with ID " + employeeIdToFind + ":");
			System.out.println(immediateManager);
		} else {
			System.out.println("No immediate manager found for employee with ID " + employeeIdToFind);
		}

		System.out.println("-------------------------------------------------------------------------------------");

		// Find highest paid employee in each department
		Map<Integer, Employee> highestPaidEmployeesInDepartments = new HashMap<>();
		for (Employee employee : employeeList) {
			int departmentNo = employee.getDepartmentNo();
			if (!highestPaidEmployeesInDepartments.containsKey(departmentNo)
					|| employee.getSalary() > highestPaidEmployeesInDepartments.get(departmentNo).getSalary()) {
				highestPaidEmployeesInDepartments.put(departmentNo, employee);
			}
		}
		System.out.println("Highest paid employees in each department:");
		for (Map.Entry<Integer, Employee> entry : highestPaidEmployeesInDepartments.entrySet()) {
			System.out.println("Department " + entry.getKey() + ": " + entry.getValue());
		}

		System.out.println("-------------------------------------------------------------------------------------");

		// Find highest paid employee on each role
		Map<String, Employee> highestPaidEmployeesInRoles = new HashMap<>();
		for (Employee employee : employeeList) {
			String role = employee.getRole();
			if (!highestPaidEmployeesInRoles.containsKey(role)
					|| employee.getSalary() > highestPaidEmployeesInRoles.get(role).getSalary()) {
				highestPaidEmployeesInRoles.put(role, employee);
			}
		}
		System.out.println("Highest paid employees in each role:");
		for (Map.Entry<String, Employee> entry : highestPaidEmployeesInRoles.entrySet()) {
			System.out.println("Role " + entry.getKey() + ": " + entry.getValue());
		}

		System.out.println("-------------------------------------------------------------------------------------");

		// Find employees joined between two dates
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
		Date startDate = null;
		Date endDate = null;

		try {
			startDate = dateFormat.parse("01-MAY-81");
			endDate = dateFormat.parse("9-JUN-81");
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		System.out.println("Employees who joined between " + dateFormat.format(startDate) + " and "
				+ dateFormat.format(endDate) + ":");
		for (Employee employee : employeeList) {
			try {
				Date joinDate = employee.getDateOfJoining();
				if (joinDate.compareTo(startDate) >= 0 && joinDate.compareTo(endDate) <= 0) {
					System.out.println(employee);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("-------------------------------------------------------------------------------------");

		// Find salesman with highest commission
		Employee highestCommissionSalesman = null;
		double highestCommission = 0.0;
		for (Employee employee : employeeList) {
			if ("SALESMAN".equals(employee.getRole())) {
				Double commission = employee.getCommission(); // Use Double instead of double
				if (commission != null && commission > highestCommission) { // Check for null and perform the comparison
					highestCommission = commission;
					highestCommissionSalesman = employee;
				}
			}
		}
		if (highestCommissionSalesman != null) {
			System.out.println("Salesman with the highest commission:");
			System.out.println(highestCommissionSalesman);
		} else {
			System.out.println("No salesman with commission found.");
		}

		System.out.println("-------------------------------------------------------------------------------------");

		// Find sum of salary of all employees
		double totalSalary = 0.0;
		for (Employee employee : employeeList) {
			totalSalary += employee.getSalary();
		}
		System.out.println("Total sum of salaries of all employees: " + totalSalary);

		System.out.println("-------------------------------------------------------------------------------------");
	}
}
