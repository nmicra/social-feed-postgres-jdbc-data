package com.example.social_net_feed.repository

interface UserRepositoryCustom {
    fun addPostToFeeds(userIds: List<Long>, postId: Long)

    fun removePostsFromFeed(userId: Long, postIds: List<Long>)
}