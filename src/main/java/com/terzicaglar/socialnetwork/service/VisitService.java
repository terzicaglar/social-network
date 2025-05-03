package com.terzicaglar.socialnetwork.service;

import com.terzicaglar.socialnetwork.model.PaginationResponse;
import com.terzicaglar.socialnetwork.model.UserProfileDto;
import com.terzicaglar.socialnetwork.model.VisitorDto;
import com.terzicaglar.socialnetwork.repository.VisitRepository;
import com.terzicaglar.socialnetwork.util.UserValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final UserProfileService userProfileService;

    public VisitService(VisitRepository visitRepository, UserProfileService userProfileService) {
        this.visitRepository = visitRepository;
        this.userProfileService = userProfileService;
    }

    //@Transactional
    public void recordVisit(Long sourceUserId, Long targetUserId) {
        UserValidationUtils.validateUserExists(userProfileService, sourceUserId);
        UserValidationUtils.validateUserExists(userProfileService, targetUserId);

        visitRepository.saveVisit(sourceUserId, targetUserId);
    }

    public PaginationResponse<VisitorDto> getVisitors(Long userId, int page, int size) {
        UserValidationUtils.validateUserExists(userProfileService, userId);

        Long totalItems = visitRepository.getVisitorCount(userId);

        // Calculate offset for pagination
        int offset = (page - 1) * size;

        // Fetch visitors from the repository
        List<VisitorDto> visitors = visitRepository.getVisitors(userId, offset, size);

        // Return the list of visitors
        return new PaginationResponse<>(totalItems, page, size, visitors);
    }
}
