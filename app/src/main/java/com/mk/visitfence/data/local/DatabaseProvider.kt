package com.mk.visitfence.data.local

import android.content.Context
import androidx.room.Room
import com.mk.visitfence.data.local.dao.GeofenceDao
import com.mk.visitfence.data.local.dao.VisitFenceDao

object DatabaseProvider {

    @Volatile
    private var INSTANCE : GoefenceDatabase? = null

    // two null check if the case two thread at the same time reach first an see INSTANCE ==  null
    fun provideDatabase(context : Context): GoefenceDatabase{
        return INSTANCE ?: synchronized(this){
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                GoefenceDatabase::class.java,
                GoefenceDatabase.DATABASE_NAME
            ).build().also {
                INSTANCE = it
            }
        }
    }

    fun provideGeofenceDao(context: Context) : GeofenceDao{
        return provideDatabase(context).geofenceDao()
    }

    fun provideVisitFenceDao(context: Context) : VisitFenceDao {
        return provideDatabase(context).visitfenceDao()
    }
}