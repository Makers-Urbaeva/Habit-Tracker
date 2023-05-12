package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "achievement_gen")
    @SequenceGenerator(name = "achievement_gen", sequenceName = "achievement_seq", allocationSize = 1, initialValue = 5)
    private Long id;

    private Integer mark;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private Habit habit;

}
