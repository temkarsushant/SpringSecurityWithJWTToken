package com.spring.restsecurity.repo;

import java.util.Optional;

import com.spring.restsecurity.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Employee, Integer> {

//	@Query("select e FROM Employee e where name=:username")@Param("username") 
	Optional<Employee> findByUsername(String username);
}
