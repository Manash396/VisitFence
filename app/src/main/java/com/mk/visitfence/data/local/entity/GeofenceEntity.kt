package com.mk.visitfence.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mk.visitfence.ui.geofenceList.GeofenceUiModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Entity(tableName = "geofences")
data class GeofenceEntity(
    @PrimaryKey
    val id : String ,
    val name : String,
    val latitude : Double,
    val longitude : Double,
    val radius : Float,
    val createdAt: Long = System.currentTimeMillis()
)

fun GeofenceEntity.toUiModel() : GeofenceUiModel{
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyy , hh:mm a", Locale.US)
    val createdDateTime  = Instant.ofEpochMilli(this.createdAt)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

    return GeofenceUiModel(
        id = id,
        name = name,
        latitude = latitude,
        longitude = longitude,
        radius = "$radius m",
        createdAt = createdDateTime.format(formatter)
    )
}

