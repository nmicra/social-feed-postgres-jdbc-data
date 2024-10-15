package com.example.social_net_feed.repository

import com.example.social_net_feed.entity.User
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long>, UserRepositoryCustom {

    // Find user by name
    fun findByName(name: String): User?

    // Add a follower to a user
    @Modifying
    @Query("UPDATE \"user\" SET followers = array_append(followers, :followerId) WHERE id = :userId")
    fun addFollower(userId: Long, followerId: Long): Int

    // Remove a follower from a user
    @Modifying
    @Query("UPDATE \"user\" SET followers = array_remove(followers, :followerId) WHERE id = :userId")
    fun removeFollower(userId: Long, followerId: Long): Int
}