package net.javaguides.ems.sevice.impl;
//Bu sınıf, iş mantığını gerçekleştiren ve Controller sınıfı ile veritabanı arasındaki iletişimi sağlayan kısımdır.

import lombok.AllArgsConstructor;
import net.javaguides.ems.dto.EmployeeDto;
import net.javaguides.ems.entity.Employee;
import net.javaguides.ems.exception.ResourceNotFoundException;
import net.javaguides.ems.mapper.EmployeeMapper;
import net.javaguides.ems.repository.EmployeeRepository;
import net.javaguides.ems.sevice.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    // Yeni bir çalışan oluşturmak için gerekli iş mantığını uygulayan metod.
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.maptoEmployeeDto(savedEmployee);

    }

    // Belirli bir kimlik numarasına sahip çalışanı getirmek için gerekli iş mantığını uygulayan metod.
    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee is not exists with given id: " + employeeId));
        return EmployeeMapper.maptoEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        // Tüm çalışanları veritabanından almak için EmployeeRepository kullanılır.
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()                   // Çalışan listesini bir akışa (stream) dönüştürür.
                .map((employee) -> EmployeeMapper.maptoEmployeeDto(employee))       // Her çalışanı EmployeeDto'ya dönüştürür.
                .collect(Collectors.toList());      // Dönüştürülen verileri bir liste olarak toplar ve döndürür.


    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updateEmployee) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee is not exist with given id: "+ employeeId)
        );
        employee.setFirstName(updateEmployee.getFirstName());
        employee.setLastName(updateEmployee.getLastName());
        employee.setEmail(updateEmployee.getEmail());

        Employee updatedEmployeeObj = employeeRepository.save(employee);

        return EmployeeMapper.maptoEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee is not exist with given id: "+ employeeId)
        );
        employeeRepository.deleteById(employeeId);


    }
}
