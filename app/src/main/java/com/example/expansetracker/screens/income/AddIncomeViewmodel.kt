package com.example.expansetracker.screens.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expansetracker.data.model.IncomeDao
import com.example.expansetracker.data.model.IncomeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddIncomeViewmodel @Inject constructor(private val dao: IncomeDao) : ViewModel() {
    private val _incomeItems = MutableStateFlow<IncomeItem?>(null)
    val incomeItem = _incomeItems.value

    private val _allIncome = MutableStateFlow<List<IncomeItem>>(emptyList())
    val allIncomes: StateFlow<List<IncomeItem>> = _allIncome



  private val _totalIncome =MutableStateFlow(0)
    val totalIncome:StateFlow<Int> = _totalIncome

    private val _totalAmount=MutableStateFlow(0)
    val totalAmount:StateFlow<Int> = _totalAmount


init {
    getAll()
    getTotalExpanse()
}

    fun getIncome(name: String, amount: Int, date: String) {
      val newTransactionItem = IncomeItem(text = name, amount = amount, date = date)
        _incomeItems.value=newTransactionItem
        insert()
    }

    private fun insert() {
        viewModelScope.launch {
            dao.insert(_incomeItems.value!!)
        }
    }

private fun getAll(){
    viewModelScope.launch {
        _allIncome.value=dao.getAll()
    }

}

    private fun getTotalExpanse() {
        viewModelScope.launch {
           _totalIncome.value= dao.getTotal()
        }
    }


    fun deleteAll() {
        viewModelScope.launch {
            dao.deleteAll()
            _totalIncome.value=dao.getTotal()
        }
    }

}