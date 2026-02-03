package com.mk.visitfence.ui.visits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mk.visitfence.data.repository.GeofenceRepository
import com.mk.visitfence.ui.map.MapScreenViewModel

class VisitListViewModelFactory (
    private val repository: GeofenceRepository
): ViewModelProvider.Factory {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VisitListViewModel::class.java)) {
            return VisitListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}