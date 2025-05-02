package com.terzicaglar.socialnetwork.repository;

import com.terzicaglar.socialnetwork.model.UserProfile;
import com.terzicaglar.socialnetwork.model.UserProfileDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserProfileRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserProfileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserProfile> findById(Long id) {
        String sql = "SELECT id, first_name, last_name, email, date_of_birth, user_defined_fields, is_fraud FROM users WHERE id = ?";
        return jdbcTemplate.query(sql, ps -> ps.setLong(1, id), rs -> {
            if (rs.next()) {
                return Optional.of(mapRowToUserProfile(rs));
            } else {
                return Optional.empty();
            }
        });
    }

    private UserProfile mapRowToUserProfile(ResultSet rs) throws SQLException {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(rs.getLong("id"));
        userProfile.setFirstName(rs.getString("first_name"));
        userProfile.setLastName(rs.getString("last_name"));
        userProfile.setEmail(rs.getString("email"));
        userProfile.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
        userProfile.setUserDefinedFields(rs.getString("user_defined_fields"));
        userProfile.setFraud(rs.getBoolean("is_fraud"));
        return userProfile;
    }
}
