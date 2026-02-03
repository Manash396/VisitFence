package com.mk.visitfence.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mk.visitfence.data.local.dao.GeofenceDao
import com.mk.visitfence.data.local.dao.VisitFenceDao
import com.mk.visitfence.data.local.entity.GeofenceEntity
import com.mk.visitfence.data.local.entity.VisitEntity

@Database(
    entities = [GeofenceEntity::class, VisitEntity::class],
    version = 1,
    exportSchema = true
)
abstract class GoefenceDatabase : RoomDatabase() {

    // it will be implemented by room an abstraction layer for sql operation
    abstract fun geofenceDao() : GeofenceDao
    abstract fun visitfenceDao() : VisitFenceDao

    companion object{
        const val DATABASE_NAME = "geo_fence_db"
    }
}