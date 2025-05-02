package com.terzicaglar.socialnetwork.service;

import com.terzicaglar.socialnetwork.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitService {

    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    //@Transactional
    public void recordVisit(Long sourceUserId, Long targetUserId) {
        if (sourceUserId.equals(targetUserId)) {
            throw new IllegalArgumentException("Users cannot visit their own profile"); // TODO create custom exception
        }

        visitRepository.saveVisit(sourceUserId, targetUserId);
    }
}
