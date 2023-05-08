package com.example.api;

import com.example.dto.request.AuthenticationRequest;
import com.example.dto.request.ChangePasswordRequest;
import com.example.dto.request.ForgotPasswordRequest;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.AuthenticationResponse;
import com.example.dto.response.SimpleResponse;
import com.example.service.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserApi {
    private final UserService userService;

    @PostMapping("/signUp")
    public AuthenticationResponse signUp(@RequestBody @Valid RegisterRequest registerRequest){
        return userService.register(registerRequest);
    }

    @PostMapping("/signIn")
    public AuthenticationResponse authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest){
        return userService.authenticate(authenticationRequest);
    }

    @PostMapping("/auth-google")
    public AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        return userService.authWithGoogle(tokenId);
    }

    @PostMapping("/change-password")
    SimpleResponse changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        return userService.changePassword(request);
    }

    @PostMapping("/forgot-password")
    SimpleResponse forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        return userService.forgotPassword(request);
    }
}
