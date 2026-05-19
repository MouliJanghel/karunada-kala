package com.example.karunada_kala.model

data class Artisan(
    val id: String = "",
    val name: String = "",
    val artForm: String = "",
    val phone: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val location: String = "",
    val type: String = "", // Workshop, Performance, Seller
    val description: String = "",
    val photoUrl: String = ""
)