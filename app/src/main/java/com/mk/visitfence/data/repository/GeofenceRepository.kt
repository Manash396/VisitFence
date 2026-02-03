package com.mk.visitfence.data.repository

import com.mk.visitfence.data.local.dao.GeofenceDao
import com.mk.visitfence.data.local.dao.VisitFenceDao
import com.mk.visitfence.data.local.entity.GeofenceEntity
import com.mk.visitfence.data.local.entity.toUiModel
import com.mk.visitfence.data.local.entity.toVisitUIModel
import com.mk.visitfence.geofence.GeofenceManager
import com.mk.visitfence.ui.geofenceList.GeofenceUiModel
import com.mk.visitfence.ui.visits.VisitUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class  GeofenceRepository(
    private val geofenceDao: GeofenceDao,
    private val visitFenceDao: VisitFenceDao,
    private val fenceManager: GeofenceManager
) {

    val observeGeofences :  Flow<List<GeofenceUiModel>> = geofenceDao.getAllGeofences()
        .map { entities -> entities.map { entity -> entity.toUiModel() } }

    val observeVisitFence : Flow<List<VisitUiModel>>  = visitFenceDao.getAllVisitFenceList()
        .map{ entities -> entities.map { entity -> entity.toVisitUIModel() }}

    suspend fun syncGeofenceFromDB(){
        // first() -> give the first list and then cancels the flow
        geofenceDao.getAllGeofences()
            .first()
            .forEach { entity -> fenceManager.addGeofence(entity) }
    }

    suspend fun addGeofence(geofenceEntity: GeofenceEntity){
        val exist  = geofenceDao.addGeofence(geofenceEntity)
        if (exist.toInt() != -1){
            fenceManager.addGeofence(geofenceEntity)
        }
    }

    suspend fun removeGeofence(geofenceEntity: GeofenceEntity){
        geofenceDao.removeGeofence(geofenceEntity)
    }

}