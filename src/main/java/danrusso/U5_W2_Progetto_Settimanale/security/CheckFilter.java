package danrusso.U5_W2_Progetto_Settimanale.security;

import danrusso.U5_W2_Progetto_Settimanale.entities.Employee;
import danrusso.U5_W2_Progetto_Settimanale.exceptions.UnauthorizedException;
import danrusso.U5_W2_Progetto_Settimanale.services.EmployeeService;
import danrusso.U5_W2_Progetto_Settimanale.tools.JWTTools;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class CheckFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private EmployeeService employeeService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer "))
            throw new UnauthorizedException("No valid token in Authorization.");

        String token = header.replace("Bearer ", "");

        jwtTools.checkTokenValidity(token);

        // -------------------- AUTORIZZAZIONE -----------------------
        String employeeId = jwtTools.obtainIdFromToken(token);
        Employee currentEmployee = this.employeeService.findById(UUID.fromString(employeeId));

        Authentication authentication = new UsernamePasswordAuthenticationToken(currentEmployee, null, currentEmployee.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Questo è lo step finale del filtro, passa a un altro filtro o al controllore se i controlli sono finiti.
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return new AntPathMatcher().match("/authentication/**", request.getServletPath());
    }
}
