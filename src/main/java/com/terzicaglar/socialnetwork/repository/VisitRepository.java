package com.terzicaglar.socialnetwork.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VisitRepository {

    private final JdbcTemplate jdbcTemplate;

    public VisitRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveVisit(Long sourceUserId, Long targetUserId) {
        String sql = "INSERT INTO user_visits (source_user_id, target_user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, sourceUserId, targetUserId);
    }
}
