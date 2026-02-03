package com.mk.visitfence.geofence

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.mk.visitfence.data.local.entity.GeofenceEntity

class GeofenceManager(
    private val context: Context
) {
    private val geofencingClient: GeofencingClient =
        LocationServices.getGeofencingClient(context.applicationContext)

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun addGeofence(entity : GeofenceEntity) {
        val geofence = Geofence.Builder()
            .setRequestId(entity.id)
            .setCircularRegion(entity.latitude,entity.longitude,entity.radius)
            .setExpirationDuration(24*60*60*1000)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()

        val request  = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        val pendingIntent = GeofenceReceiver.getPendingIntent(context)

        geofencingClient.addGeofences(request,pendingIntent)
            .addOnSuccessListener { Log.d("KrishnaGeofence", "Added ${entity.name}") }
            .addOnFailureListener { Log.e("KrishnaGeofence", it.message ?: "Failed") }
    }
}