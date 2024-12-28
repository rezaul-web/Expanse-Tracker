package com.example.expansetracker.budget

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val TOTAL_BUDGET_KEY = intPreferencesKey("total_budget")


    suspend fun saveTotalBudget(budget: Int) {
        dataStore.edit { preferences ->
            preferences[TOTAL_BUDGET_KEY] = budget
        }
    }

    fun getTotalBudget(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[TOTAL_BUDGET_KEY] ?: 0
        }
    }
}
