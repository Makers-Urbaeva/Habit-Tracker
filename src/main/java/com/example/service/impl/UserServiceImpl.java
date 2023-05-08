package com.example.service.impl;

import com.example.config.jwt.JwtService;
import com.example.dto.request.*;
import com.example.dto.response.AuthenticationResponse;
import com.example.dto.response.SimpleResponse;
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
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender javaMailSender;

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.email())){
            throw new AlreadyExistException("This email already exists!");
        }
        User user = User.builder()
                .fullName(registerRequest.fullName())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .role(Role.USER)
                .build();
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

    public String getCurrentUser() {
        return jwtService.getUserInToken().getEmail();
    }

    @Override
    public SimpleResponse changePassword(ChangePasswordRequest request) {
        try {
            User user = userRepository.findByEmail(getCurrentUser()).
                    orElseThrow(() -> new NotFoundException(String.format("User with email : %s doesn't exists! ", getCurrentUser())));
            if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
                return SimpleResponse.builder().status(HttpStatus.NOT_FOUND).message("Wrong old password.").build();
            }
            user.setPassword(passwordEncoder.encode(request.newPassword()));
            userRepository.save(user);
            return SimpleResponse.builder().status(HttpStatus.OK).message("Password updated successfully.").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SimpleResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message("Something went wrong.").build();
    }

    @Override
    public SimpleResponse forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with email: %s doesn't exist!", request.email())));
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userRepository.save(user);
        try {
            String senderName = "Habit tracker user registration portal server";
            String subject ="Here is the link to reset your password";
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("habittracker.service@gmail.com", senderName);
            helper.setTo(request.email());
            helper.setSubject(subject);
            String htmlMessage = "<p>Hello,</p>" +
                    "<p>You have requested to reset your password.</p>" +
                    "<p>Click the link below to change your password:</p>" +
                    "<p><b><a href=\"http://localhost:2023/processResetPassword=?" + token + "\">Change my password</a><b></p>" +
                    "<p>Ignore this email if you do remember your password, or you have not made the request.</p>";
            helper.setText(htmlMessage, true);
            javaMailSender.send(message);
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Check your email for credentials.")
                    .build();
        } catch (Exception e){
            e.printStackTrace();
        }
        return SimpleResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Something went wrong.")
                .build();
    }

    @Override
    public SimpleResponse resetToken(NewPasswordRequest newPasswordRequest) {
        User user = userRepository.findByResetToken(newPasswordRequest.token())
                .orElseThrow(() -> new NotFoundException("Invalid token"));
        user.setPassword(passwordEncoder.encode(newPasswordRequest.newPassword()));
        user.setResetToken(null);
        userRepository.save(user);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully updated!")
                .build();
    }
}
