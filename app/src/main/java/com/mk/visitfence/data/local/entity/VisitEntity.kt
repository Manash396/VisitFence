package com.mk.visitfence.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mk.visitfence.ui.visits.VisitUiModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "visits")
data class VisitEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val geofenceId: String,
    val locationName: String,
    val entryTime: Long,
    val exitTime: Long? = null,
    val duration :  Long? = null,
    val createdAt : Long = System.currentTimeMillis()
)

fun VisitEntity.toVisitUIModel() : VisitUiModel{
    return VisitUiModel(
        id = this.id,
        geofenceId = this.geofenceId,
        locationName = this.locationName,
        entryTime = this.entryTime.toTimeString(),
        exitTime = this.exitTime?.toTimeString() ?: "--",
        date = this.createdAt.toDateString(),
        duration = this.duration
    )
}


private fun Long.toTimeString() : String{
    val formatter  = SimpleDateFormat("hh mm a", Locale.getDefault())
    return formatter.format(Date(this))
}

private fun Long.toDateString() : String{
    val formatter  = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(Date(this))
}