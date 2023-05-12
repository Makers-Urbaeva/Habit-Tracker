package com.example.api;

import com.example.dto.request.UserRequest;
import com.example.dto.request.UserUpdateRequest;
import com.example.dto.response.SimpleResponse;
import com.example.dto.response.UserProfileResponse;
import com.example.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User profile CRUD", description = "API endpoints for user profile")
public class UserProfileApi {
    private final UserProfileService userProfileService;

    @GetMapping("/get")
    @Operation(summary = "The user get with id method",
            description = "User with id"
    )
    public UserProfileResponse getUserById(@RequestParam Long id){
        return userProfileService.getUserById(id);
    }

    @PostMapping
    @Operation(summary = "Method for saving the user profile request.",
            description = "You can save user profile using this method.")
    public SimpleResponse save(@RequestBody UserRequest request){
        return userProfileService.save(request);
    }

    @DeleteMapping
    @Operation(summary = "The user profile it delete method.",
            description = "This method should be used to delete the user profile")
    public SimpleResponse delete(Long id){
        return userProfileService.delete(id);
    }

    @PutMapping
    @Operation(summary = "The User update method.",
            description = "This method should be used to update the user profile")
    public SimpleResponse update(UserUpdateRequest request){
        return userProfileService.update(request);
    }
}