package com.example.service.impl;

import com.example.dto.request.HabitRequest;
import com.example.dto.request.HabitUpdateRequest;
import com.example.dto.response.HabitResponse;
import com.example.dto.response.SimpleResponse;
import com.example.entity.Habit;
import com.example.exceptions.NotFoundException;
import com.example.repository.HabitRepository;
import com.example.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {
    private final HabitRepository habitRepository;

    @Override
    public SimpleResponse save(HabitRequest request) {
        Habit habit = Habit.builder()
                .name(request.name())
                .description(request.description())
                .goal(request.goal())
                .frequency(request.frequency())
                .start_date(request.start_date())
                .end_date(request.end_date())
                .build();
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
        habit.setStart_date(request.start_date());
        habit.setEnd_date(request.end_date());

        habitRepository.save(habit);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("The habit with id %d successfully updated!")
                .build();
    }
}
