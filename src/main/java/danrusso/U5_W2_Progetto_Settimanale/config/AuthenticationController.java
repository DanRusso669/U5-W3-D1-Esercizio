package danrusso.U5_W2_Progetto_Settimanale.config;

import danrusso.U5_W2_Progetto_Settimanale.entities.Employee;
import danrusso.U5_W2_Progetto_Settimanale.exceptions.ValidationException;
import danrusso.U5_W2_Progetto_Settimanale.payloads.LoginDTO;
import danrusso.U5_W2_Progetto_Settimanale.payloads.LoginRespDTO;
import danrusso.U5_W2_Progetto_Settimanale.payloads.NewEmployeeDTO;
import danrusso.U5_W2_Progetto_Settimanale.payloads.NewEmployeeRespDTO;
import danrusso.U5_W2_Progetto_Settimanale.services.AuthenticatorService;
import danrusso.U5_W2_Progetto_Settimanale.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    private AuthenticatorService authenticatorService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        String token = this.authenticatorService.checkCredentialsAndCreateToken(body);
        return new LoginRespDTO(token);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public NewEmployeeRespDTO save(@RequestBody @Validated NewEmployeeDTO payload, BindingResult validationResults) {
        if (validationResults.hasErrors())
            throw new ValidationException(validationResults.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        else {
            Employee newEmployee = this.employeeService.saveEmployee(payload);
            return new NewEmployeeRespDTO(newEmployee.getEmployeeId());
        }
    }


}
