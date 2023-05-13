package com.example.service.impl;

import com.example.dto.response.HabitResponse;
import com.example.repository.CalendarRepository;
import com.example.repository.HabitRepository;
import com.example.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {
    private final CalendarRepository calendarRepository;
    private final HabitRepository habitRepository;

    @Override
    public List<HabitResponse> getHabits(Long id) {
        return habitRepository.getHabits(id);
    }
}
