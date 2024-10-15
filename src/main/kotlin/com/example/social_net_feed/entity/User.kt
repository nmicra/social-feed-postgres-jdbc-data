package com.example.social_net_feed.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("user")
data class User(
    @Id
    val id: Long? = null,
    val creationDate: LocalDate = LocalDate.now(),
    val name: String,
    val followers: List<Long> = emptyList(),
    val feed: List<Long> = emptyList()
)