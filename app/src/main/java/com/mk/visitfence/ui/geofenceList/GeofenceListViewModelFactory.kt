package com.mk.visitfence.ui.geofenceList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mk.visitfence.data.repository.GeofenceRepository
import com.mk.visitfence.ui.map.MapScreenViewModel

class GeofenceListViewModelFactory(
    private val repository: GeofenceRepository
) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GeofenceListViewModel::class.java)) {
            return GeofenceListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}