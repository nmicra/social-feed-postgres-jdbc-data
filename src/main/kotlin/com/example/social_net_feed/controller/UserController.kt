package com.example.social_net_feed.controller

import com.example.social_net_feed.entity.Post
import com.example.social_net_feed.entity.User
import com.example.social_net_feed.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestParam name: String): ResponseEntity<User> {
        val user = userService.createUser(name)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/{userId}/follow/{targetUserId}")
    fun followUser(@PathVariable userId: Long, @PathVariable targetUserId: Long): ResponseEntity<Void> {
        userService.followUser(userId, targetUserId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{userId}/unfollow/{targetUserId}")
    fun unfollowUser(@PathVariable userId: Long, @PathVariable targetUserId: Long): ResponseEntity<Void> {
        userService.unfollowUser(userId, targetUserId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{userId}/feed")
    fun getUserFeed(@PathVariable userId: Long): ResponseEntity<List<Post>> {
        val feed = userService.getUserFeed(userId)
        return ResponseEntity.ok(feed)
    }
}