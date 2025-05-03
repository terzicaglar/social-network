package com.terzicaglar.socialnetwork.service;

import com.terzicaglar.socialnetwork.exception.UserProfileNotFoundException;
import com.terzicaglar.socialnetwork.model.UserProfileConverter;
import com.terzicaglar.socialnetwork.model.UserProfileDto;
import com.terzicaglar.socialnetwork.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfileDto getUserProfileById(Long id) {
        return userProfileRepository.findById(id)
                .map(UserProfileConverter::convertToDto)
                .orElseThrow(() -> new UserProfileNotFoundException("User profile not found with id: " + id));
    }

    public boolean userProfileExistsById(Long id) {
        return userProfileRepository.existsById(id);
    }

    public void markUserAsFraud(Long userId) {
        if (!userProfileExistsById(userId)) {
            throw new UserProfileNotFoundException("User profile not found with id: " + userId);
        }
        userProfileRepository.markUserAsFraud(userId);
    }

}
