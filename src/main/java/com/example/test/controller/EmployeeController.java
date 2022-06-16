package com.example.test.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.model.Employee;
import com.example.test.repository.EmployeeRepo;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepo employeerepo;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@PostMapping("/saveEmployee")
	public Employee saveEmployee(@RequestBody Employee emp) {
		
		return employeerepo.save(emp);
	}
	@PostMapping("/sendEmail")
	public ResponseEntity<Object> sendEmail(@RequestBody Employee emp) {
		Optional<Employee> emps = employeerepo.findById(emp.getId());
		if(emps.isPresent()) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("javatechw2022@gmail.com");
		msg.setTo(emp.getEmail());

		msg.setSubject("Welcome to Gmail Example");
		msg.setText("Hello"+emp.getEmail()+ ",You are now part of Java SpringBoot Project Series");

		javaMailSender.send(msg);
		return generateRespose("mail Sent to User"+ emp.getEmail(), HttpStatus.OK, emp.getEmail());

		}else {
			return generateRespose("User not found"+ emp.getEmail(), HttpStatus.BAD_REQUEST, emp.getEmail());

		}

	}
	@GetMapping("/getEmployeeById")
	public Optional<Employee> getEmployeeById(@RequestBody Employee emp){
		return employeerepo.findById(emp.getId());
	}
	
	@GetMapping("/getAllEmployees")
	public Iterable<Employee> getAllEmployee() {
		return employeerepo.findAll();
	}
	
	@PostMapping("/updateEmployee")
	public Employee updateEmployee(@RequestBody Employee emp) {
		Optional<Employee> emps = employeerepo.findById(emp.getId());
		emps.get().setDesignation(emp.getDesignation());
		return employeerepo.save(emps.get());
		
	}
	
	
	@PostMapping("/delete")
	public ResponseEntity<Object> deleteDataById(@RequestBody Employee emp){
		employeerepo.deleteById(emp.getId());
		Optional<Employee> emps = employeerepo.findById(emp.getId());
		if(emps.isPresent())
			return generateRespose("The EMployee is not deleted ", HttpStatus.BAD_REQUEST, emp);
		else
			return generateRespose("The Employee Deleted with Id : "+emp.getId() , HttpStatus.OK, emp);
	}
	
	public ResponseEntity<Object> generateRespose(String message, HttpStatus st , Object responseobj){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("meaasge", message);
		map.put("Status", st.value());
		map.put("data", responseobj);
		
		return new ResponseEntity<Object> (map,st);
	}
}
