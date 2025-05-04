package com.terzicaglar.socialnetwork.controller;

import com.terzicaglar.socialnetwork.model.InteractionRequest;
import com.terzicaglar.socialnetwork.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/like")
@Tag(name = "Like", description = "APIs for managing like interactions")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    // Endpoint to record a like action
    @PostMapping("")
    @Operation(summary = "Record a like", description = "Records a like interaction between two users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Like recorded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> recordLike(
            @Parameter(description = "Like interaction request containing source and target user IDs", required = true)
            @RequestBody InteractionRequest request) {
        likeService.recordLike(request.getSourceUserId(), request.getTargetUserId());
        return ResponseEntity.ok().build();
    }
}
