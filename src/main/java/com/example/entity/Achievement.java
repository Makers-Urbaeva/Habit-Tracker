package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.DETACH;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "achievement_gen")
    @SequenceGenerator(name = "achievement_gen", sequenceName = "achievement_seq", allocationSize = 1, initialValue = 5)
    private Long id;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private User user;

    private Integer mark;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private Habit habit;
}
