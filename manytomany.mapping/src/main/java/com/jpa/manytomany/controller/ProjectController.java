package com.jpa.manytomany.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.manytomany.entity.Employee;
import com.jpa.manytomany.entity.Project;
import com.jpa.manytomany.repository.EmployeeRepo;
import com.jpa.manytomany.repository.ProjectRepo;
import com.jpa.manytomany.response.ResponseError;
import com.jpa.manytomany.service.ProjectService;

@RestController
@RequestMapping("/crud")
public class ProjectController {
	@Autowired
	ProjectService projectService;
	
	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	ProjectRepo projectRepo;

	@GetMapping("/project/{uuid}")
	public ResponseEntity<Object> getproject(@PathVariable String uuid) {
		return projectService.getproject(uuid);
}
	
	@GetMapping("/project")
	public List<Project> getAllData(Project project) {
		return projectService.getAllData(project);
   }

}