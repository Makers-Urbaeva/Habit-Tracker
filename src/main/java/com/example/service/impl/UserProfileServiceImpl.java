package com.example.service.impl;

import com.example.dto.request.UserRequest;
import com.example.dto.request.UserUpdateRequest;
import com.example.dto.response.SimpleResponse;
import com.example.dto.response.UserProfileResponse;
import com.example.entity.Calendar;
import com.example.entity.Habit;
import com.example.entity.Measurement;
import com.example.entity.User;
import com.example.enums.Role;
import com.example.exceptions.AlreadyExistException;
import com.example.exceptions.NotFoundException;
import com.example.repository.CalendarRepository;
import com.example.repository.UserRepository;
import com.example.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = Logger.getLogger(User.class.getName());

    @Override
    public SimpleResponse save(UserRequest request) {
        if (userRepository.existsByEmail(request.email())){
            throw new AlreadyExistException(
                    String.format("User with email: %s already exists!", request.email())
            );
        }
        Calendar calendar = Calendar.builder().build();
        User user = User.builder()
                .fullName(request.fullName())
                .icon(request.icon())
                .role(Role.USER)
                .calendar(calendar)
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        calendar.setUser(user);
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
        logger.info(String.format("Attempting to delete doctor with id: %d", id));
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                String.format("User with id: %d doesn't exist!", id)));
        Calendar calendar = user.getCalendar();
        List<Habit> habits = calendar.getHabits();
        userRepository.delete(user);
        calendarRepository.delete(calendar);
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
