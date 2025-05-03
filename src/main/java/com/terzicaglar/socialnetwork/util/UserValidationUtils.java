package com.terzicaglar.socialnetwork.util;

import com.terzicaglar.socialnetwork.exception.UserProfileNotFoundException;
import com.terzicaglar.socialnetwork.service.UserProfileService;

public class UserValidationUtils {
    public static void validateUserExists(UserProfileService userProfileService, Long userId) {
        if (!userProfileService.userProfileExistsById(userId)) {
            throw new UserProfileNotFoundException("User not found with id: " + userId);
        }
    }
}