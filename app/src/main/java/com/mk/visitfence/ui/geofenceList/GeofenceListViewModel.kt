package com.mk.visitfence.ui.geofenceList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.visitfence.data.repository.GeofenceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class GeofenceListViewModel(
    private val repo : GeofenceRepository
) : ViewModel(){

    val geofencesStateFlow = repo.observeGeofences
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

}