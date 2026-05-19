package com.example.karunada_kala.model

data class Event(
    val id: String = "",
    val title: String = "",
    val artForm: String = "",
    val date: String = "",
    val location: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)