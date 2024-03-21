package com.javaguid.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaguid.springboot.exception.ResourceNotFoundException;
import com.javaguid.springboot.model.Employee;
import com.javaguid.springboot.repository.EmployeeRepository;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
//	get all Employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();		
	}
	
//	adding data of employee
	@PostMapping("/newemployee")
	public Employee newEmployee(@RequestBody Employee employee) {
		
		return employeeRepository.save(employee);
		}
	
//	Finding User Using ID
	@GetMapping("/employee/{id}")
	Employee getEmployeeById(@PathVariable long id) {
		return employeeRepository.findById( id).orElseThrow(()->new ResourceNotFoundException(id));
	}
//	Editing User by ID
	@PutMapping("/employee/{id}")
	Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable long id) {
		return employeeRepository.findById(id)
				.map(Employee->{
					Employee.setFirstName(newEmployee.getFirstName());
					Employee.setLastName(newEmployee.getLastName());
					Employee.setEmailId(newEmployee.getEmailId());
					return employeeRepository.save(Employee);
				}).orElseThrow(()-> new ResourceNotFoundException(id));
	}
	
//	Deleting User by Using ID
	@DeleteMapping("/employee/{id}")
	String deleteEmployeebyId(@ PathVariable long id) {
		if(!employeeRepository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		employeeRepository.deleteById(id);
		return "Employee by id "+id+" has been deleted";
	}
	
}
