package com.terzicaglar.socialnetwork.service;

import com.terzicaglar.socialnetwork.repository.LikeRepository;
import com.terzicaglar.socialnetwork.util.UserValidationUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(LikeService.class);
    private final LikeRepository likeRepository;
    private final UserProfileService userProfileService;  // Optional, for user validation
    private final FraudDetectionService fraudDetectionService;  // Optional, for fraud detection

    public LikeService(LikeRepository likeRepository, UserProfileService userProfileService, FraudDetectionService fraudDetectionService) {
        this.likeRepository = likeRepository;
        this.userProfileService = userProfileService;
        this.fraudDetectionService = fraudDetectionService;
    }

    //@Transactional
    public void recordLike(Long sourceUserId, Long targetUserId) {
        UserValidationUtils.validateUserExists(userProfileService, sourceUserId);
        UserValidationUtils.validateUserExists(userProfileService, targetUserId);
        // Check if the like already exists, if not, save it
        if (!likeRepository.likeExists(sourceUserId, targetUserId)) {
            likeRepository.saveLike(sourceUserId, targetUserId);
            logger.info("Like recorded from user {} to user {}", sourceUserId, targetUserId);
            fraudDetectionService.evaluateFraudStatus(sourceUserId);
        } else {
            logger.info("Like already exists from user {} to user {}", sourceUserId, targetUserId);
        }
    }
}
