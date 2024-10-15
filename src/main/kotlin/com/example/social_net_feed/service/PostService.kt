package com.example.social_net_feed.service

import com.example.social_net_feed.entity.Post
import com.example.social_net_feed.repository.PostRepository
import com.example.social_net_feed.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun createPost(userId: Long, content: String): Post {
        val post = Post(content = content, post2user = userId)
        val savedPost = postRepository.save(post)

        // Get followers of the user
        val user = userRepository.findById(userId).orElseThrow { Exception("User not found") }
        val followersIds = user.followers

        // Update followers' feeds
        if (followersIds.isNotEmpty()) {
            userRepository.addPostToFeeds(followersIds, savedPost.id!!)
        }

        return savedPost
    }
}