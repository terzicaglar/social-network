package com.terzicaglar.socialnetwork.service;

import com.terzicaglar.socialnetwork.repository.LikeRepository;
import com.terzicaglar.socialnetwork.repository.UserProfileRepository;
import com.terzicaglar.socialnetwork.repository.VisitRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class FraudDetectionService {

    public static final int FRAUD_LIMIT = 3;

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(FraudDetectionService.class);
    private final VisitRepository visitRepository;
    private final LikeRepository likeRepository;
    private final UserProfileRepository userProfileRepository;

    public FraudDetectionService(VisitRepository visitRepository, LikeRepository likeRepository, UserProfileRepository userProfileRepository) {
        this.visitRepository = visitRepository;
        this.likeRepository = likeRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public void evaluateFraudStatus(Long userId) {
        int visitCount = visitRepository.countDistinctTargetsInFirst10Minutes(userId);
        int likeCount = likeRepository.countDistinctTargetsInFirst10Minutes(userId);

        // TODO check logic, it may be 1 less
        if ((visitCount + likeCount) >= FRAUD_LIMIT) {
            logger.warn("User {} is marked as fraud due to high activity: {} visits and {} likes in the first 10 minutes.", userId, visitCount, likeCount);
            userProfileRepository.markUserAsFraud(userId);
        }
    }
}
