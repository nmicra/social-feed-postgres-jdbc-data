package com.example.social_net_feed.repository

import com.example.social_net_feed.entity.Post
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : CrudRepository<Post, Long> {

    // Find posts by user ID
    fun findByPost2user(post2user: Long): List<Post>
}