package com.example.entity;

import com.example.enums.MeasureType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.REFRESH;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "measurement_gen")
    @SequenceGenerator(name = "measurement_gen", sequenceName = "measurement_seq", allocationSize = 1, initialValue = 5)
    private Long id;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private MeasureType measureType;
}
