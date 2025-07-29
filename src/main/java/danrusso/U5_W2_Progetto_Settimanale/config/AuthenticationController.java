package danrusso.U5_W2_Progetto_Settimanale.config;

import danrusso.U5_W2_Progetto_Settimanale.payloads.LoginDTO;
import danrusso.U5_W2_Progetto_Settimanale.payloads.LoginRespDTO;
import danrusso.U5_W2_Progetto_Settimanale.services.AuthenticatorService;
import danrusso.U5_W2_Progetto_Settimanale.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @PostMapping("/signup")
//    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public Employee createEmployee(@RequestBody @Validated NewEmployeeDTO payload, BindingResult validationResults) {
//        if (validationResults.hasErrors()) {
//            throw new ValidationException(validationResults.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
//        }
//        return this.employeeService.saveEmployee(payload);
//    }

}
