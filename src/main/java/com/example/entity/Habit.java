package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "habit_gen")
    @SequenceGenerator(name = "habit_gen", sequenceName = "habit_seq", allocationSize = 1, initialValue = 5)
    private Long id;
    private String name;
    private String description;
    private Integer goal;
    private String frequency;
    private LocalDate start_date;
    private LocalDate end_date;
}
