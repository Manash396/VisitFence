package com.mk.visitfence.geofence

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.mk.visitfence.data.local.DatabaseProvider
import com.mk.visitfence.data.local.entity.VisitEntity
import com.mk.visitfence.util.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GeofenceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent) ?: return

        if (geofencingEvent.hasError()) {
            Log.e("KrishnaGeofence", "Error code: ${geofencingEvent.errorCode}")
            return
        }

        val geofenceTransition = geofencingEvent.geofenceTransition
        val geofencesTrigerred = geofencingEvent.triggeringGeofences

        val entryIds = geofencesTrigerred?.map { it.requestId } ?: emptyList()

        Log.e("KrishnaGeofence", "$geofenceTransition for $entryIds")

        when (geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                saveVisit(context,entryIds,true)
                NotificationHelper.sendNotificationForFence(context,"Entering Geofence")
            }
            Geofence.GEOFENCE_TRANSITION_EXIT-> {
                saveVisit(context,entryIds,false)
                NotificationHelper.sendNotificationForFence(context,"Exiting Geofence")
            }
        }

    }



    private fun saveVisit(context: Context, ids: List<String>, isEnter: Boolean) {
        // Save entry/exit times in Room (Visits table)
        // Use CoroutineScope for background
        CoroutineScope(Dispatchers.IO).launch {
            val daoVisit = DatabaseProvider.provideVisitFenceDao(context)
            val geofenceDao = DatabaseProvider.provideGeofenceDao(context)
            val now = System.currentTimeMillis()
            ids.forEach { id ->
                if (isEnter) {
                    val locName  = geofenceDao.getGeofenceById(id = id).name // for getting location name
                   daoVisit.addVisitFence(
                       VisitEntity(
                           geofenceId = id,
                           locationName = locName,
                           entryTime = now
                       )
                   )
                } else {
                    daoVisit.updateExitPointVisitFence(idFence = id, exitTime = now)
                }
            }

        }
    }

    companion object{
        fun getPendingIntent(context: Context) :  PendingIntent{
            val intent =  Intent(context, GeofenceReceiver::class.java)
            return PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        }
    }

}