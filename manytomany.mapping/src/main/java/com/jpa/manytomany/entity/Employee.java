package com.jpa.manytomany.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@GeneratedValue(strategy = GenerationType.UUID)
//	@JsonProperty("employee_uuid")
	private String uuid = UUID.randomUUID().toString();
	private Integer empId;
//	@JsonProperty("employee_name")
	private String empName;
//	@JsonProperty("employee_age")
	private int empAge;
//	@JsonProperty("employee_salary")
	private long empSalary;
//	@JsonProperty("employee_gender")
	private String empGender;
	private LocalDateTime empJoiningDate;
	private LocalDateTime modificationDate;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "emp_proj", joinColumns = @JoinColumn(name = "eid"), inverseJoinColumns = @JoinColumn(name = "pid"))
	private List<Project> projects;

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getEmpAge() {
		return empAge;
	}

	public void setEmpAge(int empAge) {
		this.empAge = empAge;
	}

	public long getEmpSalary() {
		return empSalary;
	}
	
	public void setEmpSalary(long empSalary) {
		this.empSalary = empSalary;
	}

	public String getEmpGender() {
		return empGender;
	}

	public void setEmpGender(String empGender) {
		this.empGender = empGender;
	}
	
	public void setEmpJoiningDate(LocalDateTime empJoiningDate) {
		this.empJoiningDate = empJoiningDate;
	}

	public void setModificationDate(LocalDateTime modificationDate) {
		this.modificationDate = modificationDate;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", uuid=" + uuid + ", empId=" + empId + ", empName=" + empName + ", empAge="
				+ empAge + ", empSalary=" + empSalary + ", empGender=" + empGender + ", empJoiningDate="
				+ empJoiningDate + ", modificationDate=" + modificationDate + ", projects=" + projects + "]";
	}
		
}
