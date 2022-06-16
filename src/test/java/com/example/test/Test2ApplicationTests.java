package com.example.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.test.model.Employee;
import com.example.test.repository.EmployeeRepo;

@SpringBootTest
class Test2ApplicationTests {

	@Autowired
	EmployeeRepo emprepo;
	
	@Test
	public void testInsert() {
		Employee emp = new Employee();
		emp.setName("Raman");
		emp.setDesignation("Software engineer");
		emp.setEmail("raman19@gmail.com");
		Employee emps= emprepo.save(emp);
		Assertions.assertNotNull(emprepo.findById(emps.getId()));
	}
	
	@Test
	public void testGetAllData() {
		Iterable<Employee> emps = emprepo.findAll();
		assertThat(emps).size().isGreaterThan(0);
	}
	
	@Test
	public void testDelete() {
		Employee emp = new Employee();
		emp.setName("Raman1");
		emp.setDesignation("Software engineer1");
		emp.setEmail("raman194@gmail.com");
		Employee emps= emprepo.save(emp);
		emprepo.deleteById(emps.getId());
		assertThat(emprepo.existsById(emps.getId())).isFalse();
	}
}
