package com.example.repository;

import com.example.dto.response.HabitResponse;
import com.example.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {

    @Query("select new com.example.dto.response.HabitResponse(h.id,h.name,h.description,h.goal,h.frequency,h.measurement.measureType,h.isDone,h.calendar.startDate, h.calendar.endDate) from Habit h")
    List<HabitResponse> getAllHabits();

    @Query("select new com.example.dto.response.HabitResponse(h.id,h.name,h.description,h.goal,h.frequency,h.measurement.measureType,h.isDone,h.calendar.startDate,h.calendar.endDate) " +
            "from Habit h where h.calendar.user.id=?1")
    List<HabitResponse> getHabits(Long userId);
}
