package com.terzicaglar.socialnetwork.service;

import com.terzicaglar.socialnetwork.model.PaginationResponse;
import com.terzicaglar.socialnetwork.model.VisitorDto;
import com.terzicaglar.socialnetwork.repository.VisitRepository;
import com.terzicaglar.socialnetwork.util.UserValidationUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitService {

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VisitService.class);
    private final VisitRepository visitRepository;
    private final UserProfileService userProfileService;
    private final FraudDetectionService fraudDetectionService;  // Optional, for fraud detection

    public VisitService(VisitRepository visitRepository, UserProfileService userProfileService, FraudDetectionService fraudDetectionService) {
        this.visitRepository = visitRepository;
        this.userProfileService = userProfileService;
        this.fraudDetectionService = fraudDetectionService;
    }

    //@Transactional
    public void recordVisit(Long sourceUserId, Long targetUserId) {
        UserValidationUtils.validateUserExists(userProfileService, sourceUserId);
        UserValidationUtils.validateUserExists(userProfileService, targetUserId);

        visitRepository.saveVisit(sourceUserId, targetUserId);
        logger.info("Visit recorded from user {} to user {}", sourceUserId, targetUserId);
        fraudDetectionService.evaluateFraudStatus(sourceUserId);
    }

    public PaginationResponse<VisitorDto> getVisitors(Long userId, int page, int size) {
        UserValidationUtils.validateUserExists(userProfileService, userId);

        Long totalItems = visitRepository.getVisitorCount(userId);

        // Calculate offset for pagination
        int offset = (page - 1) * size;
        logger.debug("Fetching visitors for user {}: page {}, size {}, offset {}", userId, page, size, offset);

        // Fetch visitors from the repository
        List<VisitorDto> visitors = visitRepository.getVisitors(userId, offset, size);

        // Return the list of visitors
        return new PaginationResponse<>(totalItems, page, size, visitors);
    }
}
