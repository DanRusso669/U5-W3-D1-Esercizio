package danrusso.U5_W2_Progetto_Settimanale.security;

import danrusso.U5_W2_Progetto_Settimanale.exceptions.UnauthorizedException;
import danrusso.U5_W2_Progetto_Settimanale.tools.JWTTools;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CheckFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer "))
            throw new UnauthorizedException("No valid token in Authorization.");

        String token = header.replace("Bearer ", "");

        jwtTools.checkTokenValidity(token);

        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return new AntPathMatcher().match("/authentication/**", request.getServletPath());
    }
}
