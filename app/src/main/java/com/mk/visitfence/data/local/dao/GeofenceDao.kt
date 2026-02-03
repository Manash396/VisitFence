package com.mk.visitfence.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mk.visitfence.data.local.entity.GeofenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GeofenceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGeofence(geofenceEntity: GeofenceEntity): Long

    @Query("select * from geofences order by createdAt desc")
    fun getAllGeofences() : Flow<List<GeofenceEntity>>

    @Query("select * from geofences where id = :id limit 1")
    suspend fun getGeofenceById(id : String) : GeofenceEntity

    @Delete
    suspend fun removeGeofence(geofenceEntity: GeofenceEntity)
}