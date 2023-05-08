package com.example.service;

import com.example.dto.request.AuthenticationRequest;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.AuthenticationResponse;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;

public interface UserService {
    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
