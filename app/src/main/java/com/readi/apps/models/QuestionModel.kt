package com.readi.apps.models

data class QuestionModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val options: List<String>,
    val nextMap: Map<String, String>
)