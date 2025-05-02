package com.terzicaglar.socialnetwork.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.sql.Date;

public class UserProfileConverter {

    public static UserProfileDto convertToDto(UserProfile userProfile) {
        if (userProfile == null) {
            return null;
        }
        return new UserProfileDto(
                userProfile.getId(),
                userProfile.getFirstName(),
                userProfile.getLastName(),
                userProfile.getEmail(),
                userProfile.getDateOfBirth(),
                calculateAge(userProfile.getDateOfBirth()),
                userProfile.getUserDefinedFields(),
                userProfile.getFraud()
        );
    }

    private static Integer calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return null;
        }

        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
