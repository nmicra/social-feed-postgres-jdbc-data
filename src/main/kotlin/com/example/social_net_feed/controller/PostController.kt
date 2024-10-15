package com.example.social_net_feed.controller

import com.example.social_net_feed.entity.Post
import com.example.social_net_feed.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController(private val postService: PostService) {

    @PostMapping
    fun createPost(@RequestParam userId: Long, @RequestParam content: String): ResponseEntity<Post> {
        val post = postService.createPost(userId, content)
        return ResponseEntity.ok(post)
    }
}