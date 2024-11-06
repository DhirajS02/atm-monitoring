package com.atm.atm_monitoring.filter;

import com.atm.atm_monitoring.exceptions.InvalidJwtToken;
import com.atm.atm_monitoring.model.ErrorResponse;
import com.atm.atm_monitoring.service.EmployeeService;
import com.atm.atm_monitoring.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * JwtFilter is a custom security filter that intercepts HTTP requests to validate JWT (JSON Web Tokens)
 * for user authentication. It extends OncePerRequestFilter to ensure the filter is executed once per request.
 *
 * This filter performs the following steps:
 *   Extracts the JWT token from the Authorization header.
 *   Validates the JWT token using JwtUtil to ensure it's not expired or invalid.
 *   If the token is valid, it retrieves the user's details using the EmployeeService and sets the user's
 *       authentication in the Spring Security context.
 *   If the token is invalid or the user is not found, an appropriate error response is returned.
 *
 *
 * If any error occurs during validation or authentication, the filter will return an error response with
 * appropriate HTTP status codes and error details.
 *
 * It is annotated with @Component, making it a Spring bean that will be automatically discovered and applied
 * to all incoming requests as part of the Spring Security filter chain.
 *
 * @author Dhiraj
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final String jwtSecret;
    private final JwtUtil jwtUtil;
    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;

    public JwtFilter(@Value("${jwt.secretKey}") String jwtSecret,
                     JwtUtil jwtUtil,
                     EmployeeService employeeService,
                     @Qualifier("objectMapper") ObjectMapper objectMapper) {
        this.jwtSecret = jwtSecret;
        this.jwtUtil = jwtUtil;
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
    }

    /**
     * Filters each HTTP request to validate the JWT token from the Authorization header.
     * If valid, the user is authenticated and the authentication is stored in the SecurityContext.
     * If invalid or expired, an error response is sent back to the client with appropriate status codes.
     *
     * @param request  the incoming HTTP request
     * @param response the outgoing HTTP response
     * @param filterChain the filter chain to proceed with the next filter
     * @throws ServletException in case of servlet-related exceptions
     * @throws IOException in case of I/O exceptions
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            final var jwtToken = authorizationHeader.substring(7);

            try {
                if (jwtUtil.validateAccessToken(jwtToken)) {
                    final var username = jwtUtil.getUsername(jwtToken);
                    final var userDetails=employeeService.loadUserByUsername(username);
                    final var authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //Set the authentication token to Spring Security Context
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                else
                {
                    throw new InvalidJwtToken("Invalid Access Token. Token is not valid or expired");
                }
            }
            catch (UsernameNotFoundException e) {
                handleException(response, e.getMessage(), request.getRequestURI(), HttpStatus.UNAUTHORIZED);
                return;

            }
            catch (Exception e) {
                handleException(response, e.getMessage(), request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Helper method to handle exceptions by sending an error response as JSON.
     *
     * @param response the outgoing HTTP response
     * @param message the error message to include in the response
     * @param requestUri the URI where the error occurred
     * @param status the HTTP status to set in the response
     * @throws IOException if an input or output exception occurred
     */
    private void handleException(HttpServletResponse response, String message, String requestUri, HttpStatus status) throws IOException {
        final var errorResponse = new ErrorResponse(message, "uri=" + requestUri);
        response.setContentType("application/json");
        response.setStatus(status.value());
        try (PrintWriter writer = response.getWriter()) {
            writer.write(objectMapper.writeValueAsString(errorResponse));
            writer.flush();
        }
    }
}

