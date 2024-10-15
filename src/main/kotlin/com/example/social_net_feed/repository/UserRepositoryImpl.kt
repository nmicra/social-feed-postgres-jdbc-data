package com.example.social_net_feed.repository

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : UserRepositoryCustom {

    override fun addPostToFeeds(userIds: List<Long>, postId: Long) {
        val sql = """
            UPDATE "user"
            SET feed = array_append(feed, :postId)
            WHERE id = ANY(:userIds::bigint[])
        """.trimIndent()

        val params = MapSqlParameterSource()
        params.addValue("postId", postId)
        params.addValue("userIds", userIds.toTypedArray())

        namedParameterJdbcTemplate.update(sql, params)
    }

    override fun removePostsFromFeed(userId: Long, postIds: List<Long>) {
        if (postIds.isEmpty()) {
            return
        }

        val sql = """
            UPDATE "user"
            SET feed = array_remove(feed, UNNEST(:postIds::bigint[]))
            WHERE id = :userId
        """.trimIndent()

        val params = MapSqlParameterSource()
        params.addValue("userId", userId)
        params.addValue("postIds", postIds.toTypedArray())

        namedParameterJdbcTemplate.update(sql, params)
    }
}