package com.example.service;

import com.example.dto.request.AuthenticationRequest;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.AuthenticationResponse;
import com.google.firebase.auth.FirebaseAuthException;

public interface UserService {
    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException;
}
