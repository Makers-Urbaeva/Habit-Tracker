package com.example.enums;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
public enum ExecutionFrequency {
    DAILY,
    WEEKLY,
    MONTHLY;

    @Enumerated(EnumType.STRING)
    private Day day;
    private int week;
    @Enumerated(EnumType.STRING)
    private Month month;
    ExecutionFrequency(Day day) {
        this.day = day;
    }
    ExecutionFrequency(int week) {
        this.week = week;
    }
    ExecutionFrequency(Month month) {
        this.month = month;
    }
    ExecutionFrequency() {
    }
}
