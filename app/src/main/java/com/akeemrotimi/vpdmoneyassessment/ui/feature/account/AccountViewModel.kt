package com.akeemrotimi.vpdmoneyassessment.ui.feature.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akeemrotimi.vpdmoneyassessment.data.model.Account
import com.google.firebase.auth.FirebaseAuth

class AccountViewModel(firebaseAuth: FirebaseAuth) : ViewModel() {
    private val _sourceAccount = MutableLiveData<Account?>()
    val sourceAccount: LiveData<Account?> get() = _sourceAccount

    private val _accounts = MutableLiveData<List<Account>>()
    val accounts: LiveData<List<Account>> get() = _accounts

    init {
        val user = firebaseAuth.currentUser
        val nameParts = user?.displayName?.split(" ")
        val firstName = nameParts?.firstOrNull() ?: "Unknown"
        val lastName = nameParts?.lastOrNull() ?: "User"

        _sourceAccount.value = Account(
            id = 0,
            name = "$firstName $lastName",
            bankName = "VPD Money",
            accountNumber = "2412403012",
            accountBalance = 6000.00
        )

        _accounts.value = listOf(
            Account(
                id = 1,
                name = "Akeem Terry",
                bankName = "Sterling Bank",
                accountNumber = "2412403012",
                accountBalance = 100000.00
            ),
            Account(
                id = 2,
                name = "Joseph Parker",
                bankName = "First Bank",
                accountNumber = "4651240301",
                accountBalance = 30000.00
            ),
            Account(
                id = 3,
                name = "Happiness Julius",
                bankName = "VPD Money",
                accountNumber = "0012403012",
                accountBalance = 30000.00
            )
        )
    }

    fun transfer(destinationAccountId: Int, amount: Double): Boolean {
        val source = _sourceAccount.value
        val destination = _accounts.value?.find { it.id == destinationAccountId }

        return if (source != null && destination != null && source.accountBalance >= amount) {
            val updatedSource = source.copy(accountBalance = source.accountBalance - amount)
            val updatedDestination =
                destination.copy(accountBalance = destination.accountBalance + amount)

            _sourceAccount.value = updatedSource
            updateAccounts(updatedSource, updatedDestination)
            true
        } else {
            false
        }
    }

    private fun updateAccounts(updatedSource: Account, updatedDestination: Account) {
        _accounts.value = _accounts.value?.map { account ->
            when (account.id) {
                updatedSource.id -> updatedSource
                updatedDestination.id -> updatedDestination
                else -> account
            }
        }
    }
}

class AccountViewModelFactory(private val firebaseAuth: FirebaseAuth) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountViewModel(firebaseAuth) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
