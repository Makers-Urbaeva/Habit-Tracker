package com.example.service;

import com.example.dto.response.HabitResponse;

import java.util.List;

public interface CalendarService {
    List<HabitResponse> getHabits(Long id);

}