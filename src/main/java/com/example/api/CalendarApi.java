package com.example.api;

import com.example.dto.response.HabitResponse;
import com.example.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendars")
@Tag(name = "Calendar", description = "API endpoints for calendar\n" +
        "You can view user's habits")
public class CalendarApi {
    private final CalendarService calendarService;

    @GetMapping("/{userId}/getHabits")
    @Operation(summary = "Get the user's habits  method",
            description = "User with id"
    )
    public List<HabitResponse> getHabits(@PathVariable Long userId){
        return calendarService.getHabits(userId);
    }
}
