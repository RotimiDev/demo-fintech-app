package com.akeemrotimi.vpdmoneyassessment.ui.feature.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akeemrotimi.vpdmoneyassessment.data.model.Account
import com.google.firebase.auth.FirebaseAuth

class AccountViewModel : ViewModel() {
    private val _sourceAccount = MutableLiveData<Account?>()
    val sourceAccount: LiveData<Account?> get() = _sourceAccount

    private val _accounts = MutableLiveData<List<Account>>()
    val accounts: LiveData<List<Account>> get() = _accounts

    init {
        val user = FirebaseAuth.getInstance().currentUser
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
                accountNumber = "2412403012",
                accountBalance = 30000.00
            )
        )
    }

    fun updateBalance(userId: Int, newBalance: Double) {
        _accounts.value?.let {
            val updatedAccounts = it.map { account ->
                if (account.id == userId) {
                    account.copy(accountBalance = newBalance)
                } else {
                    account
                }
            }
            _accounts.value = updatedAccounts
        }

        _sourceAccount.value?.let {
            if (it.id == userId) {
                _sourceAccount.value = it.copy(accountBalance = newBalance)
            }
        }
    }
}
