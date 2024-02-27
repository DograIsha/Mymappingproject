package com.jpa.manytomany.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.manytomany.entity.Project;

import jakarta.transaction.Transactional;

@Transactional
@Repository
public interface ProjectRepo extends JpaRepository<Project, Integer>{

	void save(List<Project> update_project);

	Project findByUuid(String uuid);
	
	int deleteByUuid(String uuid);
}