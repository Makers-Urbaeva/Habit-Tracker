package com.example.api;

import com.example.dto.request.HabitRequest;
import com.example.dto.request.HabitUpdateRequest;
import com.example.dto.response.HabitResponse;
import com.example.dto.response.SimpleResponse;
import com.example.service.HabitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/habits")
@Tag(name = "Habit tracker CRUD", description = "API endpoints for habits")
public class HabitApi {
    private final HabitService habitService;

    @GetMapping("/get")
    @Operation(summary = "The habit get with name method",
            description = "Habit with name"
    )
    public HabitResponse getHabit(@RequestParam  String habitName){
        return habitService.getHabit(habitName);
    }

    @PostMapping
    @Operation(summary = "Method for saving the habit request.",
            description = "You can save habit using this method.")
    public SimpleResponse save(@RequestBody HabitRequest request){
        return habitService.save(request);
    }

    @GetMapping
    @Operation(summary = "The habit get all method",
            description = "Habit get all"
    )
    public List<HabitResponse> habits(){
        return habitService.getAll();
    }

    @DeleteMapping
    @Operation(summary = "The habit delete method.",
            description = "This method should be used to delete the habit")
    public SimpleResponse delete(Long id){
        return habitService.delete(id);
    }

    @PutMapping
    @Operation(summary = "The habit update method.",
            description = "This method should be used to update the habit")
    public SimpleResponse update(HabitUpdateRequest request){
        return habitService.update(request);
    }
}
