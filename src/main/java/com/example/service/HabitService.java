package com.example.service;

import com.example.dto.request.HabitRequest;
import com.example.dto.request.HabitUpdateRequest;
import com.example.dto.response.HabitResponse;
import com.example.dto.response.SimpleResponse;

import java.util.List;

public interface HabitService {
    SimpleResponse save(HabitRequest request);

    HabitResponse getHabit(String habitName);

    List<HabitResponse> getAll();

    SimpleResponse delete(Long id);

    SimpleResponse update(HabitUpdateRequest request);

    SimpleResponse changeHabitStatus(Boolean isDone, Long habitId);
}
