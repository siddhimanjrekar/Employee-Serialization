package com.aurionpro.model;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable {
	private int id;
	private String name;
	private String role;
	private int managerId;
	private Date dateOfJoining;
	private double salary;
	private double commission;
	private int departmentNo;

	public Employee(int id, String name, String role, int managerId, Date dateOfJoining, double salary,
			double commission, int departmentNo) {
		super();
		this.id = id;
		this.name = name;
		this.role = role;
		this.managerId = managerId;
		this.dateOfJoining = dateOfJoining;
		this.salary = salary;
		this.commission = commission;
		this.departmentNo = departmentNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public int getDepartmentNo() {
		return departmentNo;
	}

	public void setDepartmentNo(int departmentNo) {
		this.departmentNo = departmentNo;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", role=" + role + ", managerId=" + managerId
				+ ", dateOfJoining=" + dateOfJoining + ", salary=" + salary + ", commission=" + commission
				+ ", departmentNo=" + departmentNo + "]";
	}

}
