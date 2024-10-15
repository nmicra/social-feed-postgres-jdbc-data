package com.example.social_net_feed.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("post")
data class Post(
    @Id
    val id: Long? = null,
    val creationDate: LocalDate = LocalDate.now(),
    val content: String,
    val post2user: Long
)