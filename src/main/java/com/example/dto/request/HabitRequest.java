package com.example.dto.request;

import com.example.enums.ExecutionFrequency;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record HabitRequest(
        Long userId,
        String name,
        String description,
        Integer goal,
        ExecutionFrequency frequency,
        LocalDate start_date,
        LocalDate end_date
) {
}
