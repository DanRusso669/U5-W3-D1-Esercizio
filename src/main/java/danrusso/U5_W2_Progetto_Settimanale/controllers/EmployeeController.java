package danrusso.U5_W2_Progetto_Settimanale.controllers;

import danrusso.U5_W2_Progetto_Settimanale.entities.Employee;
import danrusso.U5_W2_Progetto_Settimanale.exceptions.ValidationException;
import danrusso.U5_W2_Progetto_Settimanale.payloads.NewEmployeeDTO;
import danrusso.U5_W2_Progetto_Settimanale.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Employee> findAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "employeeId") String sortBy) {
        return this.employeeService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee createEmployee(@RequestBody @Validated NewEmployeeDTO payload, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            throw new ValidationException(validationResults.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return this.employeeService.saveEmployee(payload);
    }

    @GetMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee findById(@PathVariable UUID employeeId) {
        return this.employeeService.findById(employeeId);
    }

    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee findByIdAndUpdate(@PathVariable UUID employeeId, @RequestBody @Validated NewEmployeeDTO payload, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            throw new ValidationException(validationResults.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.employeeService.findByIdAndUpdate(payload, employeeId);
    }

    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID employeeId) {
        this.employeeService.findByIdAndDelete(employeeId);
    }

    @PatchMapping("/{employeeId}/avatar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String uploadAvatar(@PathVariable UUID employeeId, @RequestParam("avatar") MultipartFile file) {
        return this.employeeService.uploadAvatar(employeeId, file);
    }

    // ***************** /me ENDPOINTS ***********************

    @GetMapping("/me")
    public Employee getPersonalProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee) {
        return currentAuthenticatedEmployee;
    }

    @PutMapping("/me")
    public Employee updatePersonalProfile(@AuthenticationPrincipal Employee currentAuthEmployee, @RequestBody @Validated NewEmployeeDTO payload) {
        return this.employeeService.findByIdAndUpdate(payload, currentAuthEmployee.getEmployeeId());
    }

    @DeleteMapping("/me")
    public void deletePersonalProfile(@AuthenticationPrincipal Employee currentAuthEmployee) {
        this.employeeService.findByIdAndDelete(currentAuthEmployee.getEmployeeId());
    }
}
