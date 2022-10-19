package fr.afpajulien.api.controller;

import fr.afpajulien.api.model.Employee;
import fr.afpajulien.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Create - Add a new employee
     *
     * @param employee An object employee
     * @return The employee object saved
     */
    @PostMapping("/employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {

        return new ResponseEntity<>(employeeService.saveEmployee(employee), CREATED);
    }

    /**
     * Read - Get one employee
     *
     * @param id The id of the employee
     * @return An Employee object full filled
     */
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") final Long id) {

        final Employee data = employeeService.getEmployee(id).orElseGet(() -> null);

        return new ResponseEntity<>(data, data != null ? OK : NOT_FOUND);
    }

    /**
     * Read - Get all employees
     *
     * @return - An Iterable object of Employee full filled
     */
    @GetMapping("/employees")
    public Iterable<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    /**
     * Update - Update an existing employee
     *
     * @param id       - The id of the employee to update
     * @param employee - The employee object updated
     * @return
     */
    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@PathVariable("id") final Long id, @RequestBody Employee employee) {
        Optional<Employee> e = employeeService.getEmployee(id);
        if (e.isPresent()) {
            Employee currentEmployee = e.get();

            String firstName = employee.getFirstName();
            if (firstName != null) {
                currentEmployee.setFirstName(firstName);
            }
            String lastName = employee.getLastName();
            if (lastName != null) {
                currentEmployee.setLastName(lastName);
            }
            String mail = employee.getMail();
            if (mail != null) {
                currentEmployee.setMail(mail);
            }
            String password = employee.getPassword();
            if (password != null) {
                currentEmployee.setPassword(password);
            }
            employeeService.saveEmployee(currentEmployee);
            return currentEmployee;
        } else {
            return null;
        }
    }

    /**
     * Delete - Delete an employee
     *
     * @param id - The id of the employee to delete
     */
    @DeleteMapping("/employee/{id}")
    public void deleteEmployee(@PathVariable("id") final Long id) {
        employeeService.deleteEmployee(id);
    }

}
