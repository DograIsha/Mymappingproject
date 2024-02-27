package com.jpa.manytomany.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jpa.manytomany.entity.Employee;
import com.jpa.manytomany.entity.Project;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface EmployeeRepo extends JpaRepository<Employee, Integer>
{
//	 List<Employee> findByEmpname(String name);
	 List<Employee> findByEmpSalaryGreaterThan(long empsal);
	 Employee findByUuid(String uuid);
	 int deleteByUuid(String uuid);
	Employee findByEmpId(Integer empId);
}