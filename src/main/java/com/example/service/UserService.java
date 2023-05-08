package com.example.service;

import com.example.dto.request.*;
import com.example.dto.response.AuthenticationResponse;
import com.example.dto.response.SimpleResponse;
import com.google.firebase.auth.FirebaseAuthException;

public interface UserService {
    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException;
    SimpleResponse changePassword(ChangePasswordRequest request);
    SimpleResponse forgotPassword(ForgotPasswordRequest request);
    SimpleResponse resetToken(NewPasswordRequest newPasswordRequest);

}
