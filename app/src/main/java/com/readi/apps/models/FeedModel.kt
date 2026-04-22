package com.readi.apps.models

data class FeedModel(
    val id: String,
    val videoUrl: String,
    val thumbnailUrl: String,
    val userName: String,
    val userAvatar: String,
    val caption: String,
    val workoutDate: String,
    val likeCount: Int,
    val commentCount: Int,
    val isVideo: Boolean = true
)