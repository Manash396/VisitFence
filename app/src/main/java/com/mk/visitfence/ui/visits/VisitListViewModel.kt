package com.mk.visitfence.ui.visits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.visitfence.data.repository.GeofenceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class VisitListViewModel(
    private val repo : GeofenceRepository
) : ViewModel(){

    val visitFenceStateFlow = repo.observeVisitFence
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

}