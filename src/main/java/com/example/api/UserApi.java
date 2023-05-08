package com.example.api;

import com.example.dto.request.AuthenticationRequest;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.AuthenticationResponse;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserApi {
    private final UserService userService;

    @PostMapping("/signUp")
    public AuthenticationResponse signUp(@RequestBody RegisterRequest registerRequest){
        return userService.register(registerRequest);
    }

    @PostMapping("/signIn")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        return userService.authenticate(authenticationRequest);
    }
}
