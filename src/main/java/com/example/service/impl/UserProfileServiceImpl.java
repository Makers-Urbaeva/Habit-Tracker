package com.example.service.impl;

import com.example.dto.request.UserRequest;
import com.example.dto.request.UserUpdateRequest;
import com.example.dto.response.SimpleResponse;
import com.example.dto.response.UserProfileResponse;
import com.example.entity.User;
import com.example.enums.Role;
import com.example.exceptions.AlreadyExistException;
import com.example.exceptions.NotFoundException;
import com.example.repository.UserRepository;
import com.example.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public SimpleResponse save(UserRequest request) {
        if (userRepository.existsByEmail(request.email())){
            throw new AlreadyExistException(
                    String.format("User with email: %s already exists!", request.email())
            );
        }
        User user = User.builder()
                .fullName(request.fullName())
                .icon(request.icon())
                .role(Role.USER)
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("User profile saved successfully!")
                .build();
    }

    @Override
    public UserProfileResponse getUserById(Long id) {
        return userRepository.getUserById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with id: %d doesn't exist!", id)));
    }

    @Override
    public SimpleResponse delete(Long id) {
        UserProfileResponse user = userRepository.getUserById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with id: %d doesn't exist!", id)));
        userRepository.deleteById(user.id());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("The user with id %d successfully deleted!", id))
                .build();
    }

    @Override
    public SimpleResponse update(UserUpdateRequest request) {
        User user = userRepository.findById(request.id())
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with id: %d doesn't exist!", request.id())));
        user.setFullName(request.fullName());
        user.setIcon(request.icon());
        userRepository.save(user);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("The user with id %d successfully updated!", request.id()))
                .build();
    }
}
