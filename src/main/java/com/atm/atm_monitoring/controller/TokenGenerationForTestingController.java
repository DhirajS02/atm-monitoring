package com.atm.atm_monitoring.controller;

import com.atm.atm_monitoring.util.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for generating JWT tokens for testing purposes.
 */
@RestController
@RequestMapping("/v1")
public class TokenGenerationForTestingController {
    private final JwtUtil jwtUtil;

    public TokenGenerationForTestingController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Endpoint for generating an access token based on the provided employee code.
     *
     * @param empCode the employee code used to generate the token.
     * @return a JWT access token for the specified employee code.
     */
    @PostMapping("/generate-token")
    public String generateAccessToken(@RequestParam("empCode") String empCode) {
        return jwtUtil.generateAccessToken(empCode);
    }
}
