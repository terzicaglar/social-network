package com.terzicaglar.socialnetwork.controller;

import com.terzicaglar.socialnetwork.model.InteractionRequest;
import com.terzicaglar.socialnetwork.model.PaginationResponse;
import com.terzicaglar.socialnetwork.model.UserProfileDto;
import com.terzicaglar.socialnetwork.model.VisitorDto;
import com.terzicaglar.socialnetwork.service.VisitService;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/visit")
public class VisitController {

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping("")
    public ResponseEntity<Void> recordVisit(@RequestBody InteractionRequest request) {
        visitService.recordVisit(request.getSourceUserId(), request.getTargetUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PaginationResponse<VisitorDto>> getVisitors(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Validate page and size
        // TODO Move this validation to another class
        if (page <= 0) page = 1;
        if (size <= 0 || size > 100) size = 10;

        PaginationResponse<VisitorDto> visitors = visitService.getVisitors(userId, page, size);
        return ResponseEntity.ok(visitors);
    }
}
