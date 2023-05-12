package com.example.service;

import com.example.dto.request.AuthenticationRequest;
import com.example.dto.request.UserRequest;
import com.example.dto.response.AuthenticationResponse;
import com.google.firebase.auth.FirebaseAuthException;

public interface UserService {
    AuthenticationResponse register(UserRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException;
}
