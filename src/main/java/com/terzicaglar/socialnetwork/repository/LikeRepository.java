package com.terzicaglar.socialnetwork.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LikeRepository {

    private final JdbcTemplate jdbcTemplate;

    public LikeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to save a like
    public void saveLike(Long sourceUserId, Long targetUserId) {
        String sql = "INSERT INTO user_likes (source_user_id, target_user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, sourceUserId, targetUserId);
    }

    public boolean likeExists(Long sourceUserId, Long targetUserId) {
        String sql = "SELECT COUNT(*) FROM user_likes WHERE source_user_id = ? AND target_user_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, sourceUserId, targetUserId);
        return count != null && count > 0;
    }

    public int countDistinctTargetsInFirst10Minutes(Long userId) {
        String sql = """
                    SELECT COUNT(DISTINCT target_user_id)
                    FROM user_likes
                    WHERE source_user_id = ?
                      AND created_at <= (
                          SELECT DATEADD('MINUTE', 10, MIN(created_at))
                          FROM user_likes
                          WHERE source_user_id = ?
                      )
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class, userId, userId);
    }
}
