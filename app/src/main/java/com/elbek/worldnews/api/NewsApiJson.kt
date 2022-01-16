package com.elbek.worldnews.api

data class NewsApiJson(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)