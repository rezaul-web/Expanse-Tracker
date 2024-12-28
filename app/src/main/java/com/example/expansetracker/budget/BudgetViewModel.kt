package com.example.expansetracker.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    val totalBudget: StateFlow<Int> = repository.getTotalBudget()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    fun updateTotalBudget(newBudget: Int) {
        viewModelScope.launch {
            repository.saveTotalBudget(newBudget)
        }
    }
}
