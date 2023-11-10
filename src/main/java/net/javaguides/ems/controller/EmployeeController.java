package net.javaguides.ems.controller;

//Bu Controller, "/api/employees" yolunda HTTP POST isteklerini dinler ve gelen istekleri işleyerek bir çalışan eklemeyi sağlar.

import lombok.AllArgsConstructor;
import net.javaguides.ems.dto.EmployeeDto;
import net.javaguides.ems.sevice.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*") //bu endpoint'in tüm kaynaklardan gelen isteklere açık olduğunu belirtir.
@AllArgsConstructor
@RestController
@RequestMapping("/api/employees") //HTTP POST isteklerini işlemek için kullanılan bir metodu belirtir.
public class EmployeeController {
    private EmployeeService employeeService;


    // Build ADD employee REST API
    @PostMapping //HTTP POST isteklerine cevap verir. Gelen istek içindeki verilere dayanarak yeni bir çalışan oluşturur
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }



    // Build GET employee REST API
    //@getmapping
    //HTTP GET isteklerine cevap vereceğini belirtir. Bu metot, istemciden gelen belirli bir çalışan kimliği (ID) ile veritabanından ilgili çalışanı arar.
    //Eğer çalışan bulunursa, bu çalışanın bilgileri EmployeeDto olarak HTTP yanıtında geri döndürülür.
    //Eğer belirtilen kimlik numarasına sahip bir çalışan bulunamazsa, ResourceNotFoundException fırlatılır ve istemciye uygun bir hata mesajı döndürülür.
    @GetMapping("{id}")
    public  ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long employeeId){
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDto);
    }

    //  Build get all employees REST API
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    //Build update Employee REST API
    @PutMapping("{id}")
    public  ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long employeeId,
                                                       @RequestBody EmployeeDto updateEmployee){
       EmployeeDto employeeDto = employeeService.updateEmployee(employeeId,updateEmployee);
       return ResponseEntity.ok(employeeDto);
    }

    //Build Delete Employee REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee deleted successfully!.");
    }

}
