package com.terzicaglar.socialnetwork.repository;

import com.terzicaglar.socialnetwork.config.FraudProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

@Repository
public class LikeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final FraudProperties fraudProperties;

    public LikeRepository(JdbcTemplate jdbcTemplate, FraudProperties fraudProperties) {
        this.jdbcTemplate = jdbcTemplate;
        this.fraudProperties = fraudProperties;
    }

    // Method to save a like
    public void saveLike(Long sourceUserId, Long targetUserId) {
        String sql = "INSERT INTO user_likes (source_user_id, target_user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, sourceUserId, targetUserId);
    }

    public boolean likeExists(Long sourceUserId, Long targetUserId) {
        String sql = """
                    SELECT 1 FROM user_likes 
                    WHERE source_user_id = ? AND target_user_id = ? 
                    LIMIT 1
                """;
        return Boolean.TRUE.equals(jdbcTemplate.query(sql, ps -> {
            ps.setLong(1, sourceUserId);
            ps.setLong(2, targetUserId);
        }, ResultSet::next));
    }

    public int countDistinctTargetsInFirstNMinutes(Long userId) {
        // Using CTE to avoid subquery execution for each row
        String sql = """
                SELECT COUNT(DISTINCT uv.target_user_id)
                FROM user_likes uv
                WHERE uv.source_user_id = ?
                  AND uv.created_at >= DATEADD('MINUTE', -?, CURRENT_TIMESTAMP)
                  AND uv.created_at <= CURRENT_TIMESTAMP;
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class, userId, fraudProperties.getPeriodMinutes());
    }
}
