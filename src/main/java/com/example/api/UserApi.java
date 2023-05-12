package com.example.api;

import com.example.dto.request.AuthenticationRequest;
import com.example.dto.request.UserRequest;
import com.example.dto.response.AuthenticationResponse;
import com.example.service.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserApi {
    private final UserService userService;

    @PostMapping("/signUp")
    public AuthenticationResponse signUp(@RequestBody @Valid UserRequest request){
        return userService.register(request);
    }

    @PostMapping("/signIn")
    public AuthenticationResponse authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest){
        return userService.authenticate(authenticationRequest);
    }

    @PostMapping("/auth-google")
    public AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        return userService.authWithGoogle(tokenId);
    }
}
