package com.mk.visitfence.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mk.visitfence.data.local.entity.VisitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VisitFenceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVisitFence(visitEntity: VisitEntity): Long

    @Query("update visits set exitTime = :exitTime ,  duration = :exitTime - entryTime where geofenceId = :idFence and exitTime is null")
    suspend fun updateExitPointVisitFence(idFence : String, exitTime: Long)


    @Query("Select * from visits")
    fun getAllVisitFenceList() : Flow<List<VisitEntity>>

}