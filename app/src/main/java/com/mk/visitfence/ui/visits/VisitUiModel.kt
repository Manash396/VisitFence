package com.mk.visitfence.ui.visits

data class VisitUiModel(
    val id : Int ,
    val geofenceId: String,
    val locationName: String,
    val entryTime: String,
    val exitTime: String,
    val date : String,
    val duration :  Long? = null,
)