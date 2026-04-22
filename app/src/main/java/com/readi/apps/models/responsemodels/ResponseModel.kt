package com.readi.apps.models.responsemodels
data class ResponseModel(
    val status: Boolean,
    val action: String? = null,
    val title: String? = null,
    val errors: List<ErrorItem>? = null
)

data class ErrorItem(
    val field: String,
    val message: String
)