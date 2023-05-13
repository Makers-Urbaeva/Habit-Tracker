package com.example.dto.request;

import com.example.enums.ExecutionFrequency;
import com.example.enums.MeasureType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record HabitRequest(
        String name,
        String description,
        Integer goal,
        ExecutionFrequency frequency,
        MeasureType measureType,
        LocalDate start_date,
        LocalDate end_date
) {
}
