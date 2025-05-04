package com.terzicaglar.socialnetwork.controller;

import com.terzicaglar.socialnetwork.model.UserProfile;
import com.terzicaglar.socialnetwork.model.UserProfileDto;
import com.terzicaglar.socialnetwork.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUserProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.getUserProfileById(id));
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> batchInsertUsers(@RequestBody List<UserProfile> users) {
        userProfileService.bulkInsertUsers(users);
        return ResponseEntity.ok("Users inserted successfully");
    }

}
