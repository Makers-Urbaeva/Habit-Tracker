package com.example.entity;

import com.example.enums.ExecutionFrequency;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.REFRESH;

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
    @Enumerated(EnumType.STRING)
    private ExecutionFrequency frequency;
    private Boolean isDone;
    @OneToOne(cascade = {PERSIST, DETACH, MERGE, REFRESH})
    private Calendar calendar;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Measurement> measurements = new ArrayList<>();
}
