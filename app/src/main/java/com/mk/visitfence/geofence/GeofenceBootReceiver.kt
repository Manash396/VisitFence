package com.mk.visitfence.geofence

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mk.visitfence.data.local.DatabaseProvider
import com.mk.visitfence.data.repository.GeofenceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GeofenceBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action != Intent.ACTION_BOOT_COMPLETED) return
       if (context == null) return
        CoroutineScope(Dispatchers.IO).launch {
            val db = DatabaseProvider.provideDatabase(context.applicationContext)
            val manager = GeofenceManager(context.applicationContext)
            val repo = GeofenceRepository(db.geofenceDao(), db.visitfenceDao(), manager)

            repo.syncGeofenceFromDB()
        }
    }

}