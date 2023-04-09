package kg.iaau.amanalert.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.iaau.amanalert.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    private static final String BEARER = "bearer ";
    UserDetailsService userDetailsService;
    JwtUtils jwtUtils;

    public TokenAuthenticationFilter(final AuthenticationManager requiresAuth, UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        super(requiresAuth);
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = ofNullable(request.getHeader(AUTHORIZATION))
                .orElse(request.getParameter("t"));
        if (header == null || !header.toLowerCase().startsWith(BEARER)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = header.split(" ")[1];
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtils.getUserNameFromJwtToken(token));

            Authentication authResult = getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authResult);
        } catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
            return;
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            SecurityContextHolder.clearContext();
            response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
            return;
        }
        chain.doFilter(request, response);
    }
}
