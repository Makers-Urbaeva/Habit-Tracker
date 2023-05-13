package com.example.service.impl;

import com.example.dto.request.HabitRequest;
import com.example.dto.request.HabitUpdateRequest;
import com.example.dto.response.HabitResponse;
import com.example.dto.response.SimpleResponse;
import com.example.entity.Calendar;
import com.example.entity.Habit;
import com.example.entity.Measurement;
import com.example.entity.User;
import com.example.exceptions.NotFoundException;
import com.example.repository.HabitRepository;
import com.example.repository.UserRepository;
import com.example.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    @Override
    public SimpleResponse save(Long userId, HabitRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with id: %d doesn't exist!", userId)));
        Calendar calendar = user.getCalendar();
        calendar.setStartDate(request.start_date());
        calendar.setEndDate(request.end_date());
        Measurement measurement = Measurement.builder().
                measureType(request.measureType()).build();
        Habit habit = Habit.builder()
                .name(request.name())
                .description(request.description())
                .goal(request.goal())
                .frequency(request.frequency())
                .measurement(measurement)
                .isDone(false)
                .calendar(calendar)
                .measurement(measurement)
                .build();
        user.getCalendar().addHabit(habit);
        habitRepository.save(habit);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Habit saved successfully!")
                .build();
    }

    @Override
    public HabitResponse getHabit(String habitName) {
        return habitRepository.getAllHabits().stream().filter(
                habit -> habit.name().equalsIgnoreCase(habitName)).findFirst()
                .orElseThrow(()-> new NotFoundException(
                        String.format("Habit with name %s not found!", habitName)
                ));
    }

    @Override
    public List<HabitResponse> getAll() {
        return habitRepository.getAllHabits();
    }

    @Override
    public SimpleResponse delete(Long id) {
        habitRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("The habit with id %d successfully deleted!", id))
                .build();
    }

    @Override
    public SimpleResponse update(HabitUpdateRequest request) {
        Habit habit = habitRepository.findById(request.id())
                .orElseThrow(()-> new NotFoundException(
                String.format("Habit with id %d not found!", request.id())
        ));
        habit.setName(request.name());
        habit.setGoal(request.goal());
        habit.setDescription(request.description());
        habit.setFrequency(request.frequency());
        habit.setCalendar(Calendar.builder()
                        .startDate(request.start_date())
                        .endDate(request.end_date())
                .build());
        habitRepository.save(habit);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("The habit with id %d successfully updated!")
                .build();
    }

    @Override
    public SimpleResponse changeHabitStatus(Boolean status, Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(()-> new NotFoundException(
                        String.format("Habit with id %d not found!", habitId)
                ));
        habit.setIsDone(status);
        habitRepository.save(habit);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("The habit's status with id %d successfully changed!")
                .build();
    }
}
