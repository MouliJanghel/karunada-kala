package com.example.karunada_kala.repository

import com.example.karunada_kala.data.local.ArtFormDao
import com.example.karunada_kala.model.ArtForm
import com.example.karunada_kala.model.Artisan
import com.example.karunada_kala.model.Event
import com.example.karunada_kala.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ArtRepository(
    private val artFormDao: ArtFormDao,
    private val firestore: FirebaseFirestore
) {
    val allArtForms: Flow<List<ArtForm>> = artFormDao.getAllArtForms()

    suspend fun refreshArtForms() {
        try {
            val snapshot = firestore.collection("artforms").get().await()
            val artForms = snapshot.toObjects(ArtForm::class.java)
            artFormDao.insertArtForms(artForms)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getArtisans(): List<Artisan> {
        return try {
            val snapshot = firestore.collection("artisans").get().await()
            snapshot.toObjects(Artisan::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Real-time listener for events
    fun getEventsRealtime(): Flow<List<Event>> = callbackFlow {
        val subscription = firestore.collection("events")
            .orderBy("date", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val events = snapshot.toObjects(Event::class.java)
                    trySend(events)
                }
            }
        awaitClose { subscription.remove() }
    }

    suspend fun getProducts(): List<Product> {
        return try {
            val snapshot = firestore.collection("products").get().await()
            snapshot.toObjects(Product::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun submitSignup(signupData: Map<String, Any>) {
        try {
            firestore.collection("signups").add(signupData).await()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}