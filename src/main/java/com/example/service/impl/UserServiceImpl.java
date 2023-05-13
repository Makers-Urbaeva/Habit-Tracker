package com.example.service.impl;

import com.example.config.jwt.JwtService;
import com.example.dto.request.AuthenticationRequest;
import com.example.dto.request.UserRequest;
import com.example.dto.response.AuthenticationResponse;
import com.example.entity.Calendar;
import com.example.entity.User;
import com.example.enums.Role;
import com.example.exceptions.AlreadyExistException;
import com.example.exceptions.BadCredentialException;
import com.example.exceptions.NotFoundException;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(UserRequest request) {
        if (userRepository.existsByEmail(request.email())){
            throw new AlreadyExistException("This email already exists!");
        }
        Calendar calendar = Calendar.builder().build();
        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        calendar.setUser(user);
        userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .email(user.getEmail())
                .token(jwt)
                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with email: %s doesn't exist!", request.email())));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialException("Invalid password!");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .email(user.getEmail())
                .token(token)
                .role(user.getRole().name())
                .build();
    }

    @PostConstruct
    public void init(){
        try {
            GoogleCredentials googleCredentials = GoogleCredentials.
                    fromStream(new ClassPathResource("habit-tracker-firebase.json").getInputStream());
            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(googleCredentials)
                    .build();
            log.info("Successfully worked the init method");
            FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
        } catch (IOException e) {
            log.error("IOException");
        }
    }

    @Override
    public AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);
        if (!userRepository.existsByEmail(firebaseToken.getEmail())){
            User user = new User();
            user.setFullName(firebaseToken.getName());
            user.setEmail(firebaseToken.getEmail());
            user.setPassword(firebaseToken.getEmail());
            user.setRole(Role.USER);
            userRepository.save(user);
        }
        User user = userRepository.findByEmail(firebaseToken.getEmail()).orElseThrow(()->{
            log.error(String.format("User with this email address %s was not found!", firebaseToken.getEmail()));
            return new NotFoundException(String.format("User with this email address %s was not found!", firebaseToken.getEmail()));
        });
        String token = jwtService.generateToken(user);
        log.info("Successfully worked the authorization with Google");

        return  AuthenticationResponse.builder()
                .email(firebaseToken.getEmail())
                .token(token)
                .role(user.getRole().name())
                .build();
    }
}
