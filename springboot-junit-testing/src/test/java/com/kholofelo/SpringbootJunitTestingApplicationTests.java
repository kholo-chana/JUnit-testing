package com.kholofelo;

import com.kholofelo.entity.Employee;
import com.kholofelo.exception.ResourceNotFoundException;
import com.kholofelo.repository.EmployeeRepository;
import com.kholofelo.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SpringbootJunitTestingApplicationTests {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	private Employee employee;

	@BeforeEach
	public void setup(){

		employee = Employee.builder()
				.id(1L)
				.firstName("Kholofelo")
				.lastName("Ramile")
				.email("kholofelo@gmail.com")
				.build();
	}

	// JUnit test for saveEmployee method
	@DisplayName("JUnit test for saveEmployee method")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() throws ResourceNotFoundException {
		// given - precondition or setup
		given(employeeRepository.findByEmail(employee.getEmail()))
				.willReturn(Optional.empty());
		given(employeeRepository.save(employee)).willReturn(employee);
		System.out.println(employeeRepository);
		System.out.println(employeeService);
		// when -  action or the behaviour that we are going test
		Employee savedEmployee = employeeService.saveEmployee(employee);
		System.out.println(savedEmployee);
		// then - verify the output
		assertThat(savedEmployee).isNotNull();
	}

	// JUnit test for saveEmployee method
	@DisplayName("JUnit test for saveEmployee method which throws exception")
	@Test
	public void givenExistingEmail_whenSaveEmployee_thenThrowsException(){
		// given - precondition or setup
		given(employeeRepository.findByEmail(employee.getEmail()))
				.willReturn(Optional.of(employee));

		System.out.println(employeeRepository);
		System.out.println(employeeService);

		// when -  action or the behaviour that we are going test
		org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			employeeService.saveEmployee(employee);
		});

		// then
		verify(employeeRepository, never()).save(any(Employee.class));
	}

	// JUnit test for getAllEmployees method
	@DisplayName("JUnit test for getAllEmployees method")
	@Test
	public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList(){
		// given - precondition or setup

		Employee employee1 = Employee.builder()
				.id(2L)
				.firstName("Tony")
				.lastName("Stark")
				.email("tony@gmail.com")
				.build();

		given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));

		// when -  action or the behaviour that we are going test
		List<Employee> employeeList = employeeService.getAllEmployees();

		// then - verify the output
		assertThat(employeeList).isNotNull();
		assertThat(employeeList.size()).isEqualTo(2);
	}

	// JUnit test for getAllEmployees method
	@DisplayName("JUnit test for getAllEmployees method (negative scenario)")
	@Test
	public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList(){
		// given - precondition or setup

		Employee employee1 = Employee.builder()
				.id(2L)
				.firstName("Tony")
				.lastName("Stark")
				.email("tony@gmail.com")
				.build();

		given(employeeRepository.findAll()).willReturn(Collections.emptyList());

		// when -  action or the behaviour that we are going test
		List<Employee> employeeList = employeeService.getAllEmployees();

		// then - verify the output
		assertThat(employeeList).isEmpty();
		assertThat(employeeList.size()).isEqualTo(0);
	}

	// JUnit test for getEmployeeById method
	@DisplayName("JUnit test for getEmployeeById method")
	@Test
	public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){
		// given
		given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

		// when
		Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

		// then
		assertThat(savedEmployee).isNotNull();

	}

	// JUnit test for updateEmployee method
	@DisplayName("JUnit test for updateEmployee method")
	@Test
	public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
		// given - precondition or setup
		given(employeeRepository.save(employee)).willReturn(employee);
		employee.setEmail("Ramile@gmail.com");
		employee.setFirstName("Kholoo");
		// when -  action or the behaviour that we are going test
		Employee updatedEmployee = employeeService.updateEmployee(employee);

		// then - verify the output
		assertThat(updatedEmployee.getEmail()).isEqualTo("Ramile@gmail.com");
		assertThat(updatedEmployee.getFirstName()).isEqualTo("Kholo");
	}

	// JUnit test for deleteEmployee method
	@DisplayName("JUnit test for deleteEmployee method")
	@Test
	public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
		// given - precondition or setup
		long employeeId = 1L;

		willDoNothing().given(employeeRepository).deleteById(employeeId);

		// when -  action or the behaviour that we are going test
		employeeService.deleteEmployee(employeeId);

		// then - verify the output
		verify(employeeRepository, times(1)).deleteById(employeeId);
	}
}
