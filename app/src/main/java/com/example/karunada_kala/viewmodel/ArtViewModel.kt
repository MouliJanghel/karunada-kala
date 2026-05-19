package com.example.karunada_kala.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.karunada_kala.data.local.AppDatabase
import com.example.karunada_kala.model.ArtForm
import com.example.karunada_kala.model.Artisan
import com.example.karunada_kala.model.Event
import com.example.karunada_kala.model.Product
import com.example.karunada_kala.repository.ArtRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ArtViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ArtRepository
    
    private val _allArtForms = MutableStateFlow<List<ArtForm>>(emptyList())
    val allArtForms: StateFlow<List<ArtForm>> = _allArtForms.asStateFlow()

    private val _artisans = MutableStateFlow<List<Artisan>>(emptyList())
    val artisans: StateFlow<List<Artisan>> = _artisans.asStateFlow()

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        val db = AppDatabase.getDatabase(application)
        repository = ArtRepository(db.artFormDao(), FirebaseFirestore.getInstance())
        
        // Collect local art forms
        viewModelScope.launch {
            repository.allArtForms.collect {
                _allArtForms.value = it
            }
        }

        // Collect real-time events
        viewModelScope.launch {
            repository.getEventsRealtime()
                .catch { e -> e.printStackTrace() }
                .collect {
                    _events.value = it
                }
        }

        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.refreshArtForms()
                _artisans.value = repository.getArtisans()
                _products.value = repository.getProducts()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun submitWorkshopSignup(name: String, phone: String, date: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val signupData = mapOf(
                    "name" to name,
                    "phone" to phone,
                    "date" to date,
                    "timestamp" to System.currentTimeMillis()
                )
                repository.submitSignup(signupData)
                onComplete(true)
            } catch (e: Exception) {
                e.printStackTrace()
                onComplete(false)
            } finally {
                _isLoading.value = false
            }
        }
    }
}