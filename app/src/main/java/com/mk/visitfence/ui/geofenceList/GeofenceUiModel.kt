package com.mk.visitfence.ui.geofenceList

data class GeofenceUiModel(
    val id : String ,
    val name : String,
    val latitude : Double,
    val longitude : Double,
    val radius : String,
    val createdAt: String
)
