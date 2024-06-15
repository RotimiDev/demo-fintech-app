package com.akeemrotimi.vpdmoneyassessment.ui.feature.transaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.akeemrotimi.vpdmoneyassessment.data.local.AppDatabase
import com.akeemrotimi.vpdmoneyassessment.data.local.TransactionDao
import com.akeemrotimi.vpdmoneyassessment.data.model.Transaction
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionDao: TransactionDao =
        AppDatabase.getDatabase(application).transactionDao()
    val transactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()
    private val _filteredTransactions = MutableLiveData<List<Transaction>>()
    val filteredTransactions: LiveData<List<Transaction>> get() = _filteredTransactions

    fun filterTransactions(query: String) {
        _filteredTransactions.value = if (query.isEmpty()) {
            transactions.value
        } else {
            transactions.value?.filter {
                it.sourceAccount.contains(query, ignoreCase = true) ||
                        it.destinationAccount.contains(query, ignoreCase = true) ||
                        it.amount.toString().contains(query)
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionDao.insert(transaction)
        }
    }
}

class TransactionViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
