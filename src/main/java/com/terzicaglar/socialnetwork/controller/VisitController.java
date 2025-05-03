package com.terzicaglar.socialnetwork.controller;

import com.terzicaglar.socialnetwork.model.InteractionRequest;
import com.terzicaglar.socialnetwork.service.VisitService;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
