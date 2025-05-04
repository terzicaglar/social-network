package com.terzicaglar.socialnetwork.controller;

import com.terzicaglar.socialnetwork.model.UserProfile;
import com.terzicaglar.socialnetwork.model.UserProfileDto;
import com.terzicaglar.socialnetwork.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User Profile", description = "APIs for managing user profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user profile by ID", description = "Retrieves a user profile by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User profile not found")
    })
    public ResponseEntity<UserProfileDto> getUserProfileById(
            @Parameter(description = "ID of the user profile to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.getUserProfileById(id));
    }

    @PostMapping("/bulk")
    @Operation(summary = "Bulk insert users", description = "Creates multiple user profiles in a single operation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users inserted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<String> batchInsertUsers(
            @Parameter(description = "List of user profiles to create", required = true)
            @Valid @RequestBody List<UserProfile> users) {
        userProfileService.bulkInsertUsers(users);
        return ResponseEntity.ok("Users inserted successfully");
    }
}
