package com.jpa.manytomany.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.jpa.manytomany.entity.Employee;
import com.jpa.manytomany.entity.Project;
import com.jpa.manytomany.repository.EmployeeRepo;
import com.jpa.manytomany.repository.ProjectRepo;
import com.jpa.manytomany.response.ResponseError;
import com.jpa.manytomany.response.ResponseSuccess;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	ProjectRepo projectRepo;

	String regexName = "[\\s]*[A-Za-z]*[\\s]*[A-Za-z]*[\\s]*[A-Za-z]*[\\s]*";

//Post Api for insert data into employee and project.
	public ResponseEntity<Object> postData(List<Employee> employees) {

		if (employees == null || employees.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Json is empty"));
		}
     
		for (Employee employee : employees) {
			if (employee.getEmpName() == null && employee.getEmpId() == 0 && employee.getEmpSalary() == 0
					&& employee.getEmpGender() == null && employee.getEmpAge() == 0 && employee.getProjects() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseError("Please Eneter all the fields"));
			}
			Employee findEmp = employeeRepo.findByEmpId(employee.getEmpId());
			if (findEmp!=null) {
			int indexEmp = employees.indexOf(employee);
			employees.set(indexEmp, findEmp);
			
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ResponseError("Employee Id already exist"));
			} 
			
			if (employee.getEmpId() <= 1000) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseError("Employee Id must be started with 1001"));
			}
			
			if (employee.getEmpId() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseError("Please enter employee Id!!!"));
			}
			
			if (employee.getEmpName() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseError("Employee name is mandatory!!!"));
			}

			if (!employee.getEmpName().matches(regexName)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						new ResponseError("Invalid characters in name! Name must only contain letters and spaces."));
			}

			if (employee.getEmpName().isBlank()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Employee name is blank"));
			}

			if (employee.getEmpName().length() < 4 || employee.getEmpName().length() > 30) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseError("Invalid name!! Name must be in 4 and 30 characters."));
			}
			
			if (employee.getEmpAge() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseError("Employee Age is not mentioned"));
			}
			if (employee.getEmpAge() < 22 || employee.getEmpAge() > 60) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Invalid age!!!"));
			}
			
			if (employee.getEmpGender() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseError("Please enter employee gender!!!"));
			}
			
			if (!employee.getEmpGender().equalsIgnoreCase("Male") && !employee.getEmpGender().equalsIgnoreCase("Female")
					&& !employee.getEmpGender().equalsIgnoreCase("Other")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseError("Gender must be Male, Female or Other"));
			}
			
			if (employee.getEmpSalary() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseError("Employee salary is not mentioned"));
			}
			
			if (employee.getEmpSalary() < 5000) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseError("Employee salary must be greater than equal to 5000"));
			}

			employee.setEmpName(employee.getEmpName().trim().replaceAll("\\s{1,}", " "));
			employee.setEmpGender(employee.getEmpGender().trim());
			employee.setEmpJoiningDate(LocalDateTime.now());
			employee.setModificationDate(LocalDateTime.now());
			List<Project> projects = employee.getProjects();
			if (projects != null) {
				for (Project project : projects) {
					if (project.getProjectName().isBlank()) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new ResponseError("Project name is blank!!!"));
					}
					
					if (project.getProjectName() == null || project.getProjectName().isEmpty()) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new ResponseError("Project name is mandatory!!!"));
					}

					if (project.getProjectName().length() < 5 || project.getProjectName().length() > 50) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(
								"Invalid project name!! Project name must be in 4 and 50 characters."));
					}
					
					if (project.getDurationMonth() == 0) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new ResponseError("project duration not mentioned"));
					}
					if (project.getDurationMonth() < 1 || project.getDurationMonth() <= 0) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new ResponseError("project duration in month not less than 1"));
					}
					
					project.setProjectName(project.getProjectName().trim().replaceAll("\\s{1,}", " "));
					project.setProjectJoingDate(LocalDateTime.now());
					project.setModicationDate(LocalDateTime.now());
				}
								
			}
			
		}
		employeeRepo.saveAll(employees);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseSuccess("Data Inserted Successfully"));
	}

//Pagination 
	public ResponseEntity<Object> getEmployee(int pageNo, int pageSize) {
		try {
		Pageable pageable = PageRequest.of(pageNo -1, pageSize, Sort.by("empId").ascending());
		Page page = employeeRepo.findAll(pageable);
		if (page.hasContent()) {
			return ResponseEntity.status(HttpStatus.OK).body(page.getContent());
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseError("Record is not present at this page no."));
		}
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseError("Page no. and page size must not be zero or negative!"));
		}
	}

// Get all data from employee
	public List<Employee> getAllData(Employee employee) {
		List<Employee> employees = null;
		employees = employeeRepo.findAll();
		return employees;
	}

// Get specific filed data on the bases of UUID
	public ResponseEntity<Object> getByUuid(String uuid) {
		Employee employee = employeeRepo.findByUuid(uuid);
		if (employee != null) {
			return ResponseEntity.status(HttpStatus.OK).body(employee);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError("UUID not found!!!"));
		}
	}

// Put Api for update the data
	public ResponseEntity<Object> putMethod(String uuid, Employee employee) {
		try {
			Employee employee2 = employeeRepo.findByUuid(uuid);
			if (employee2 != null) {
				if (employee.getEmpId() == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ResponseError("please mention employee Id!"));
				}

				if (employee.getEmpId() == 0) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ResponseError("Incorrect employee Id! Id should starts from 1001"));
				}

				if ((int) employee.getEmpId() != employee2.getEmpId()) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ResponseError("Employee Id does not match!"));
				}

				if (employee.getEmpName() == null && employee.getEmpAge() == 0 && employee.getEmpGender() == null
						&& employee.getEmpSalary() == 0 && employee.getProjects() == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Payload is empty"));
				}

				if (employee.getEmpName() == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ResponseError("Employee name is mandatroy!!!"));
				}

				if (employee.getEmpName() != null) {
					if (employee.getEmpName().isBlank()) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Name is empty!"));
					}
					if (employee.getEmpName().length() < 4 || employee.getEmpName().length() > 30) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new ResponseError("Invalid name length!! Name must be in 4 and 30 characters."));
					}
					if (!employee.getEmpName().matches(regexName)) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(
								"Invalid characters in name! Name must only contain letters and spaces."));
					}
					employee2.setEmpName(employee.getEmpName().trim().replaceAll("\\s{1,}", " "));
				}
				if (employee.getEmpAge() == 0) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ResponseError("Employee Age is not mentioned"));
				}
				if (employee.getEmpAge() != 0) {

					if (employee.getEmpAge() < 22 || employee.getEmpAge() > 60) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new ResponseError("Invalid age! Age must be geater than 22 and less than 60."));
					}
					employee2.setEmpAge(employee.getEmpAge());
				}

				if (employee.getEmpSalary() == 0) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ResponseError("Employee salary is not mentioned"));
				}

				if (employee.getEmpSalary() != 0) {
					if (employee.getEmpSalary() < 5000) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new ResponseError("Employee salary must be greater than 5000"));
					}
					employee2.setEmpSalary(employee.getEmpSalary());
				}
				if (employee.getEmpGender() == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ResponseError("Please enter employee gender!!!"));
				}
				if (!employee.getEmpGender().contains("Male") && !employee.getEmpGender().contains("Female")
						&& !employee.getEmpGender().contains("Other")) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new ResponseError("Gender must be Male, Female or Other"));
				}
				
				employee2.setEmpGender(employee.getEmpGender().trim());
				employee2.setModificationDate(LocalDateTime.now());

				if (employee.getProjects() != null) {
					for (Project project : employee.getProjects()) {
						Project existing_Project = projectRepo.findByUuid(project.getUuid());
						if (existing_Project != null) {
							if (project.getProjectName() == null || project.getProjectName().isEmpty()) {
								return ResponseEntity.status(HttpStatus.BAD_REQUEST)
										.body(new ResponseError("Project name must not be empty!!!"));
							}
							if (project.getProjectName().length() < 5 || project.getProjectName().length() > 50) {
								return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(
										"Invalid project name!! Project name must be in 4 and 50 characters."));
							}
							if (project.getDurationMonth() < 1 || project.getDurationMonth() <= 0) {
								return ResponseEntity.status(HttpStatus.BAD_REQUEST)
										.body(new ResponseError("Month duration not less than 1"));
							}
							existing_Project.setProjectName(project.getProjectName().trim().replaceAll("\\s{1,}", " "));
							existing_Project.setDurationMonth(project.getDurationMonth());
							int project_index = employee2.getProjects().indexOf(existing_Project);
							employee2.getProjects().set(project_index, existing_Project);
							existing_Project.setModicationDate(LocalDateTime.now());
						} else {
//							Project newProject = new Project();
//							newProject.setProjectName(project.getProjectName());
//							newProject.setDurationMonth(project.getDurationMonth());
//							employee2.getProjects().add(newProject);
							return new ResponseEntity<>(new ResponseError("UUID is not mentioned for project"),
									HttpStatus.BAD_REQUEST);
						}
						employeeRepo.save(employee2);
						return new ResponseEntity<>(new ResponseSuccess("Data Updated Successfully"), HttpStatus.OK);
					}
				} else
					return new ResponseEntity<>(new ResponseSuccess("Employee Data Updated Successfully"),
							HttpStatus.OK);
			} else
				return new ResponseEntity<>(new ResponseError("UUID not exist"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ResponseError("Data cannot be updated!!!"), HttpStatus.CONFLICT);
	}

// Patch Api for partial data updation.
	public ResponseEntity<Object> patchUpdate(String uuid, Employee employee) {
		try {
			Employee employee2 = employeeRepo.findByUuid(uuid);
			if (employee2 == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError("Id not Exist"));
			} else {
				if (employee.getEmpId() != null) {
					if ((int) employee.getEmpId() != employee2.getEmpId()) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new ResponseError("Employee Id does not match!"));
					}
				}
				if (employee.getEmpName() == null && employee.getEmpAge() == 0 && employee.getEmpGender() == null
						&& employee.getEmpSalary() == 0 && employee.getProjects() == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Payload is empty"));
				}

				if (employee.getEmpName() != null) {
					if (employee.getEmpName().isBlank()) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new ResponseError("Employee name is blank!"));
					}
					if (employee.getEmpName().length() < 4 || employee.getEmpName().length() > 30) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new ResponseError("Invalid name length!! Name must be in 4 and 30 characters."));
					}
					if (!employee.getEmpName().matches(regexName)) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(
								"Invalid characters in name! Name must only contain letters and spaces."));
					}

					employee2.setEmpName(employee.getEmpName().trim().replaceAll("\\s{1,}", " "));
				}
				
				if (employee.getEmpAge() != 0) {
					if (employee.getEmpAge() < 22 || employee.getEmpAge() > 60) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Invalid age!!!"));
					}
					employee2.setEmpAge(employee.getEmpAge());
				}
				
				if (employee.getEmpSalary() != 0) {
					if (employee.getEmpSalary() < 5000) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new ResponseError("Employee salary must be greater than 5000"));
					}
					employee2.setEmpSalary(employee.getEmpSalary());
				}

				if (employee.getEmpGender() != null) {
					if (!employee.getEmpGender().contains("Male") && !employee.getEmpGender().contains("Female")
							&& !employee.getEmpGender().contains("Other")) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body(new ResponseError("Gender must be Male, Female or Other"));
					}
					employee2.setEmpGender(employee.getEmpGender().trim());
				}
				employee2.setModificationDate(LocalDateTime.now());

				List<Project> projects = employee.getProjects();
				if (projects != null) {

					for (Project project : projects) {

						Project existing_Project = projectRepo.findByUuid(project.getUuid());
						if (existing_Project != null) {
							if (project.getProjectName() != null) {

								if (project.getProjectName().isBlank()) {
									return ResponseEntity.status(HttpStatus.BAD_REQUEST)
											.body(new ResponseError("Project name must not be blank!"));
								}
								if (project.getProjectName().length() < 5 || project.getProjectName().length() > 50) {
									return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(
											"Invalid project name!! Project name must be in 4 and 50 characters."));
								}
								existing_Project
										.setProjectName(project.getProjectName().trim().replaceAll("\\s{1,}", " "));
							}

							if (project.getDurationMonth() == 0) {
								return ResponseEntity.status(HttpStatus.BAD_REQUEST)
										.body(new ResponseError("project duration not mentioned"));
							}
							if (project.getDurationMonth() != 0) {
								if (project.getDurationMonth() < 1 || project.getDurationMonth() <= 0) {
									return ResponseEntity.status(HttpStatus.BAD_REQUEST)
											.body(new ResponseError("Month duration not less than 1"));
								}
								existing_Project.setDurationMonth(project.getDurationMonth());
							}
							existing_Project.setModicationDate(LocalDateTime.now());
						} else {
							return ResponseEntity.status(HttpStatus.BAD_REQUEST)
									.body(new ResponseError("UUID not mentioned for project"));
						}

					}
				}
				employeeRepo.save(employee2);
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseSuccess("Data partially Updated"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Data is not Updated"));
	}

// Delete Api on the bases of employee UUID
	public ResponseEntity<Object> deleteByUuid(String uuid) {
		Employee employee = employeeRepo.findByUuid(uuid);
		if (employee != null) {
			employeeRepo.deleteByUuid(uuid);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseSuccess("Data delete successfully"));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError("UUID not exist"));
		}
	}

// Delete project on the bases of project UUID.
	public ResponseEntity<Object> deleteproject(String uuid) {
		Project optionalProject = projectRepo.findByUuid(uuid);
		if (optionalProject != null) {
			projectRepo.deleteByUuid(uuid);
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body(new ResponseSuccess("Project deleted successfully"));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError("Id not exist!!!"));
		}
	}

}
