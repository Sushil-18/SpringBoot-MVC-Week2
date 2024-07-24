package com.codingShuttle.sushil.MVC.services;

import com.codingShuttle.sushil.MVC.dto.EmployeeDTO;
import com.codingShuttle.sushil.MVC.entities.EmployeeEntity;
import com.codingShuttle.sushil.MVC.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.Ref;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {



    private  final EmployeeRepository employeeRepository;
    private  final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper){
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
/*
       Optional<EmployeeEntity> employeeEntity =  employeeRepository.findById(id);
       return employeeEntity.map((employeeEntity1 -> modelMapper.map(employeeEntity1,EmployeeDTO.class)));
*/
        return employeeRepository.findById(id).map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployee() {
        List<EmployeeEntity> employeeEntities =  employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createEmployee(EmployeeDTO newEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(newEmployee,EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity =  employeeRepository.save(toSaveEntity);
        return modelMapper.map(savedEmployeeEntity,EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(EmployeeDTO employeeDTO, Long employeeId){
        EmployeeEntity toUpdateEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
        toUpdateEntity.setId(employeeId);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(toUpdateEntity);
        return modelMapper.map(savedEmployeeEntity,EmployeeDTO.class);
    }

    public boolean existsById(Long employeeId){
        return employeeRepository.existsById(employeeId);
    }
    public boolean deleteEmployeeById(Long employeeId) {
       boolean exists = existsById(employeeId);
        if(!exists) return false;
        employeeRepository.deleteById(employeeId);
        return  true;
    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
        boolean exists = existsById(employeeId);
        if(!exists) return null;
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((field,value)->{
           Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class,field);
           fieldToBeUpdated.setAccessible(true);
            /*
 Handle type conversion if necessary
            Object convertedValue = value;
            Class<?> fieldType = fieldToBeUpdated.getType();
            if (fieldType.equals(Long.class) && value instanceof Integer) {
                convertedValue = Long.valueOf((Integer) value);
            }
*/
            ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);
        }) ;
        return modelMapper.map(employeeRepository.save(employeeEntity),EmployeeDTO.class);

    }
}