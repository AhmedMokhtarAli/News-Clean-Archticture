package com.example.domain.entity

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)