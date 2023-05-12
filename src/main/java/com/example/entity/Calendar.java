package com.example.entity;

import com.example.enums.Day;
import com.example.enums.ExecutionFrequency;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calendar_gen")
    @SequenceGenerator(name = "calendar_gen", sequenceName = "calendar_seq", allocationSize = 1, initialValue = 5)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    @OneToMany(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private List<Habit> habits;
    @OneToOne()
    private User user;


    public void addHabit(Habit habit){
        if (habits == null){
            habits = new ArrayList<>();
        }
        habits.add(habit);
    }
}