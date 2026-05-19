package com.example.karunada_kala.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "art_forms")
data class ArtForm(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val description: String = "",
    val region: String = "",
    val videoUrl: String = "",
    val imageUrl: String = ""
)