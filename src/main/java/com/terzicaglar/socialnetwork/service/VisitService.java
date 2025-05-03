package com.terzicaglar.socialnetwork.service;

import com.terzicaglar.socialnetwork.repository.VisitRepository;
import com.terzicaglar.socialnetwork.util.UserValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
