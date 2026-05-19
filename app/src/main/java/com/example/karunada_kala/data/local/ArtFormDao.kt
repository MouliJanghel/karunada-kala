package com.example.karunada_kala.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.karunada_kala.model.ArtForm
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtFormDao {
    @Query("SELECT * FROM art_forms")
    fun getAllArtForms(): Flow<List<ArtForm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtForms(artForms: List<ArtForm>)

    @Query("SELECT * FROM art_forms WHERE id = :id")
    suspend fun getArtFormById(id: String): ArtForm?
}