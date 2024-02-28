package com.jpa.manytomany.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jpa.manytomany.entity.Employee;
import com.jpa.manytomany.repository.EmployeeRepo;
import com.jpa.manytomany.repository.ProjectRepo;
import com.jpa.manytomany.service.EmployeeService;

@RestController
@RequestMapping("/crud")
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	ProjectRepo projectRepo;

	@GetMapping("/employee")
	public List<Employee> getAllData(Employee employee) {
		return employeeService.getAllData(employee);
	}

	@PostMapping("/employee")
	public ResponseEntity<Object> postData(@Validated @RequestBody List<Employee> employees) {
		return employeeService.postData(employees);
	}

	@GetMapping("/employee/{uuid}")
	public ResponseEntity<Object> getByUuid(@PathVariable String uuid) {
		return employeeService.getByUuid(uuid);
	}

	@GetMapping("employeepage")
	public ResponseEntity<Object> getEmployee(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "5") int pageSize) {
		return employeeService.getEmployee(pageNo, pageSize);
	}

	@PutMapping("employee/{uuid}")
	public ResponseEntity<Object> putMethod(@PathVariable String uuid, @RequestBody Employee employee) {
		return employeeService.putMethod(uuid, employee);
	}

	@PatchMapping("employee/{uuid}")
	public ResponseEntity<Object> patchUpdate(@PathVariable String uuid, @RequestBody Employee employee) {
		return employeeService.patchUpdate(uuid, employee);
	}

	@DeleteMapping("/employee/{uuid}")
	public ResponseEntity<Object> deleteByUuid(@PathVariable String uuid) {
		return employeeService.deleteByUuid(uuid);
	}

	@DeleteMapping("/project/{uuid}")
	public ResponseEntity<Object> deleteproject(@PathVariable String uuid) {
		return employeeService.deleteByUuid(uuid);
	}

}