package com.example.social_net_feed.service

import com.example.social_net_feed.entity.Post
import com.example.social_net_feed.entity.User
import com.example.social_net_feed.repository.PostRepository
import com.example.social_net_feed.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository, private val postRepository: PostRepository) {

    fun createUser(name: String): User {
        val user = User(name = name)
        return userRepository.save(user)
    }

    fun followUser(userId: Long, targetUserId: Long) {
        userRepository.addFollower(targetUserId, userId)
    }

    fun getUserFeed(userId: Long): List<Post> {
        val user = userRepository.findById(userId).orElseThrow { Exception("User not found") }
        val feedPostIds = user.feed

        if (feedPostIds.isEmpty()) {
            return emptyList()
        }

        // Fetch posts by IDs
        return postRepository.findAllById(feedPostIds).toList()
    }


    @Transactional
    fun unfollowUser(userId: Long, targetUserId: Long) {
        // Check if both users exist
        val user = userRepository.findById(userId).orElseThrow { error("User not found: $userId") }
        val targetUser = userRepository.findById(targetUserId).orElseThrow { error("User not found: $targetUserId") }

        // Remove follower
        val rowsAffected = userRepository.removeFollower(targetUserId, userId)

        if (rowsAffected == 0) {
            throw IllegalStateException("Failed to unfollow user: $targetUserId")
        }

        // Optionally, remove the target user's posts from the unfollowing user's feed
        // removePostsFromFeed(unfollowingUserId = userId, posterUserId = targetUserId)
    }

    // Optional method to remove posts from feed
    private fun removePostsFromFeed(unfollowingUserId: Long, posterUserId: Long) {
        // Fetch posts by the target user
        val postsByTargetUser = postRepository.findByPost2user(posterUserId)
        val postIds = postsByTargetUser.mapNotNull { it.id }

        if (postIds.isNotEmpty()) {
            userRepository.removePostsFromFeed(unfollowingUserId, postIds)
        }
    }
}