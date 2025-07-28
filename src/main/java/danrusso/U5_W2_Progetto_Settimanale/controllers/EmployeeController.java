package danrusso.U5_W2_Progetto_Settimanale.controllers;

import danrusso.U5_W2_Progetto_Settimanale.entities.Employee;
import danrusso.U5_W2_Progetto_Settimanale.exceptions.ValidationException;
import danrusso.U5_W2_Progetto_Settimanale.payloads.NewEmployeeDTO;
import danrusso.U5_W2_Progetto_Settimanale.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Page<Employee> findAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "employeeId") String sortBy) {
        return this.employeeService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody @Validated NewEmployeeDTO payload, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            throw new ValidationException(validationResults.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return this.employeeService.saveEmployee(payload);
    }

    @GetMapping("/{employeeId}")
    public Employee findById(@PathVariable UUID employeeId) {
        return this.employeeService.findById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public Employee findByIdAndUpdate(@PathVariable UUID employeeId, @RequestBody @Validated NewEmployeeDTO payload, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            throw new ValidationException(validationResults.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.employeeService.findByIdAndUpdate(payload, employeeId);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID employeeId) {
        this.employeeService.findByIdAndDelete(employeeId);
    }

    @PatchMapping("/{employeeId}/avatar")
    public String uploadAvatar(@PathVariable UUID employeeId, @RequestParam("avatar") MultipartFile file) {
        return this.employeeService.uploadAvatar(employeeId, file);
    }

}
