package com.terzicaglar.socialnetwork.repository;

import com.terzicaglar.socialnetwork.model.VisitorDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<VisitorDto> getVisitors(Long userId, int offset, int size) {
        String sql = "SELECT u.id, u.first_name, u.last_name, uv.created_at " +
                "FROM user_visits uv " +
                "JOIN users u ON uv.source_user_id = u.id " +
                "WHERE uv.target_user_id = ? " +
                "ORDER BY uv.created_at DESC " +
                "LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            VisitorDto visitor = new VisitorDto();
            visitor.setId(rs.getLong("id"));
            visitor.setFirstName(rs.getString("first_name"));
            visitor.setLastName(rs.getString("last_name"));
            visitor.setVisitedAt(rs.getTimestamp("created_at"));
            return visitor;
        }, userId, size, offset);
    }

    public Long getVisitorCount(Long userId) {
        String sql = "SELECT COUNT(*) FROM user_visits WHERE target_user_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, userId);
    }
}
