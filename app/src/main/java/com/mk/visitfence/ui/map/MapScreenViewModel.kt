package com.mk.visitfence.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.mk.visitfence.data.local.entity.GeofenceEntity
import com.mk.visitfence.data.repository.GeofenceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID


class MapScreenViewModel(
    private val repository: GeofenceRepository
) : ViewModel() {

    val geofencesStateFlow = repository.observeGeofences
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    fun addGeofence(locationName  : String, latLang : LatLng , radius : Float = 100f ){
        viewModelScope.launch {
            val geofenceEntity = GeofenceEntity(
                id = UUID.randomUUID().toString() ,
                name  = locationName,
                latitude = latLang.latitude,
                longitude = latLang.longitude,
                radius = radius,
            )
            repository.addGeofence(geofenceEntity)
        }
    }

    // call sync
    init {
        viewModelScope.launch {
            repository.syncGeofenceFromDB()
        }
    }
}