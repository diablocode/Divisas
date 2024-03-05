package com.example.divisas

import kotlinx.serialization.Serializable

@Serializable
data class DivisaData (
    val data: Map<String, Datos>
)

@Serializable
data class Datos (
    val name: String,
    val code: String
)