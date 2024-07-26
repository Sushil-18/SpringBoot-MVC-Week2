package com.codingShuttle.sushil.MVC.controllers;

import com.codingShuttle.sushil.MVC.dto.EmployeeDTO;
import com.codingShuttle.sushil.MVC.entities.EmployeeEntity;
import com.codingShuttle.sushil.MVC.repositories.EmployeeRepository;
import com.codingShuttle.sushil.MVC.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {
/*
    @GetMapping(path = "/getMessage")
    public String getMyMessage(){
        return "Secret Message : I am learning springboot!";
    }
*/

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable @Valid Long employeeId){
        Optional<EmployeeDTO> employeeDTO =  employeeService.getEmployeeById(employeeId);
/*
        if(employeeDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
*/
        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1)).
                //orElse(ResponseEntity.notFound().build());
                orElseThrow(() -> new RuntimeException("Employee Not found with the id:"+employeeId));

    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO newEmployee){
        EmployeeDTO savedEmployee =  employeeService.createEmployee(newEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO,@PathVariable @Valid Long employeeId){
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeDTO,employeeId));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable @Valid Long employeeId){
        boolean isDeleted =  employeeService.deleteEmployeeById(employeeId);
        if(!isDeleted) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(true);
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@PathVariable @Valid Long employeeId , @RequestBody @Valid Map<String, Object> updates){
        EmployeeDTO updatedEmployee = employeeService.updatePartialEmployeeById(employeeId,updates);
        if(updatedEmployee == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedEmployee);
    }

    //Controller level exception handler
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<String> handleEmployeeNotFound(NoSuchElementException exception){
//        return new ResponseEntity<>("Employee does not exists",HttpStatus.NOT_FOUND);
//    }
}
