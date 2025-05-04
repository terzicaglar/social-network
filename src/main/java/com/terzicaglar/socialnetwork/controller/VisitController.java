package com.terzicaglar.socialnetwork.controller;

import com.terzicaglar.socialnetwork.model.InteractionRequest;
import com.terzicaglar.socialnetwork.model.PaginationResponse;
import com.terzicaglar.socialnetwork.model.VisitorDto;
import com.terzicaglar.socialnetwork.service.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/visit")
@Tag(name = "Visit", description = "APIs for managing user profile visits")
public class VisitController {

    public static final int MAXIMUM_PAGE_SIZE = 100;
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VisitController.class);
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping("")
    @Operation(summary = "Record a visit", description = "Records a visit interaction between two users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visit recorded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> recordVisit(
            @Parameter(description = "Visit interaction request containing source and target user IDs", required = true)
            @RequestBody InteractionRequest request) {
        visitService.recordVisit(request.getSourceUserId(), request.getTargetUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user's visitors", description = "Retrieves a paginated list of users who visited a specific profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visitors retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<PaginationResponse<VisitorDto>> getVisitors(
            @Parameter(description = "ID of the user whose visitors to retrieve", required = true)
            @PathVariable Long userId,
            @Parameter(description = "Page number (1-based)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Number of items per page (max 100)", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        // Validate page and size
        if (page <= 0) {
            logger.warn("Invalid page number: {}. Defaulting to page 1.", page);
            page = 1;
        }
        if (size <= 0 || size > MAXIMUM_PAGE_SIZE) {
            logger.warn("Invalid size: {}. Defaulting to size 10.", size);
            size = 10;
        }

        PaginationResponse<VisitorDto> visitors = visitService.getVisitors(userId, page, size);
        return ResponseEntity.ok(visitors);
    }
}
