package medilabo.component;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class GatewayTokenFilter extends OncePerRequestFilter {

    // Filtre : Verifie si le Token correspond
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("X-Gateway-Token");
        if (!"MY_SECRET_TOKEN".equals(token)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: Invalid Gateway Token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}