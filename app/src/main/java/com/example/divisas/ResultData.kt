package com.example.divisas

import kotlinx.serialization.Serializable

@Serializable
data class ResultData (
    val data: Map<String, String>
)