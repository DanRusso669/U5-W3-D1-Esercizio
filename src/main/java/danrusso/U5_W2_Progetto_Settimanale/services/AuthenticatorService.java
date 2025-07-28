package danrusso.U5_W2_Progetto_Settimanale.services;

import danrusso.U5_W2_Progetto_Settimanale.entities.Employee;
import danrusso.U5_W2_Progetto_Settimanale.exceptions.UnauthorizedException;
import danrusso.U5_W2_Progetto_Settimanale.payloads.LoginDTO;
import danrusso.U5_W2_Progetto_Settimanale.tools.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatorService {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JWTTools jwtTools;

    public String checkCredentialsAndCreateToken(LoginDTO payload) {

        Employee found = this.employeeService.findyByEmail(payload.email());
        if (found.getPassword().equals(payload.password())) {
            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Wrong email or password.");
        }

    }
}
