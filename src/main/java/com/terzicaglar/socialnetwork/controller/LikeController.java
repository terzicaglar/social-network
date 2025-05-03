package com.terzicaglar.socialnetwork.controller;

import com.terzicaglar.socialnetwork.model.InteractionRequest;
import com.terzicaglar.socialnetwork.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/like")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    // Endpoint to record a like action
    @PostMapping("")
    public ResponseEntity<Void> recordLike(@RequestBody InteractionRequest request) {
        likeService.recordLike(request.getSourceUserId(), request.getTargetUserId());
        return ResponseEntity.ok().build();
    }
}
