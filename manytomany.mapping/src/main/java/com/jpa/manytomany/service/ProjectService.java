package com.jpa.manytomany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.jpa.manytomany.entity.Employee;
import com.jpa.manytomany.entity.Project;
import com.jpa.manytomany.repository.EmployeeRepo;
import com.jpa.manytomany.repository.ProjectRepo;
import com.jpa.manytomany.response.ResponseError;
import com.jpa.manytomany.response.ResponseSuccess;

@Service
public class ProjectService {
	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired
	ProjectRepo projectRepo;

	public ResponseEntity<Object> getproject(String uuid) {
		Project project = projectRepo.findByUuid(uuid);
		if(project!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(project);
		} else {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError("UUID not found!!!"));
	}
	}
		public List<Project> getAllData(Project project){
			List<Project> projects = null;
			projects = projectRepo.findAll();
			return projects;
		
		
}
//		Delete project on the bases of project UUID.
		public ResponseEntity<Object> deleteProject(String uuid) {
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