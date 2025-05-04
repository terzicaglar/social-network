package com.terzicaglar.socialnetwork.repository;

import com.terzicaglar.socialnetwork.model.UserProfile;
import com.terzicaglar.socialnetwork.model.UserProfileDto;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserProfileRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserProfileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsById(Long id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM users WHERE id = ?)";
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, id);
        return Boolean.TRUE.equals(exists);
    }

    public Optional<UserProfile> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
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

    public void markUserAsFraud(Long userId) {
        String sql = "UPDATE users SET is_fraud = TRUE WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Transactional
    public void bulkInsert(List<UserProfile> users) {
        String sql = """
            INSERT INTO users (first_name, last_name, email, date_of_birth, user_defined_fields)
            VALUES (?, ?, ?, ?, ?)
        """;

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UserProfile user = users.get(i);
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getEmail());
                ps.setDate(4, java.sql.Date.valueOf(user.getDateOfBirth()));
                ps.setString(5, user.getUserDefinedFields());
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
    }
}
