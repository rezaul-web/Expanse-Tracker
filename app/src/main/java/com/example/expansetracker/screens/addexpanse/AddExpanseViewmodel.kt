package com.example.expansetracker.screens.addexpanse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expansetracker.data.model.ExpanseDao
import com.example.expansetracker.data.model.TransactionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpanseViewmodel @Inject constructor(private val dao: ExpanseDao) : ViewModel() {
    private val _transactionItem = MutableStateFlow<TransactionItem?>(null)
    val transactionItem = _transactionItem.value

    private val _allTransactions = MutableStateFlow<List<TransactionItem>>(emptyList())
    val allTransactions: StateFlow<List<TransactionItem>> = _allTransactions

    private val _recentTransactions = MutableStateFlow<List<TransactionItem>>(emptyList())
    val recentTransactions = _recentTransactions

  private val _totalExpanse =MutableStateFlow(0)
    val totalExpanse:StateFlow<Int> = _totalExpanse

    private val _totalAmount=MutableStateFlow(0)
    val totalAmount:StateFlow<Int> = _totalAmount


init {
    getRecentTransaction()
    getAll()
    getTotalExpanse()
}

    fun getTransactionItem(name: String, amount: Int, date: String) {
      val newTransactionItem = TransactionItem(text = name, amount = amount, date = date)
        _transactionItem.value=newTransactionItem
        insert()
    }

    private fun insert() {
        viewModelScope.launch {
            dao.insert(_transactionItem.value!!)
        }
    }

private fun getAll(){
    viewModelScope.launch {
        _allTransactions.value=dao.getAll()
    }

}

    private fun getTotalExpanse() {
        viewModelScope.launch {
           _totalExpanse.value= dao.getTotal()
        }
    }

    private fun getRecentTransaction() {
        viewModelScope.launch {
            _recentTransactions.value=dao.recentTransaction()
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            dao.deleteAll()
            _totalExpanse.value=dao.getTotal()
            _recentTransactions.value=dao.recentTransaction()
        }
    }

}