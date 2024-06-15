package com.akeemrotimi.vpdmoneyassessment.ui.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.akeemrotimi.vpdmoneyassessment.R
import com.akeemrotimi.vpdmoneyassessment.data.local.AppDatabase
import com.akeemrotimi.vpdmoneyassessment.data.model.Account
import com.akeemrotimi.vpdmoneyassessment.data.model.Transaction
import com.akeemrotimi.vpdmoneyassessment.ui.feature.account.AccountViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val accountViewModel: AccountViewModel by viewModels()
    private lateinit var database: AppDatabase
    private val navController by lazy { findNavController() }
    private var transactions by mutableStateOf(emptyList<Transaction>())
    private var selectedItem by mutableIntStateOf(0)
    private var searchQuery by mutableStateOf("")
    private var destinationAccounts by mutableStateOf(emptyList<Account>())
    private var sourceAccount by mutableStateOf<Account?>(null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "app_db"
        ).build()

        return ComposeView(requireContext()).apply {
            setContent {
                val sourceAccountState by accountViewModel.sourceAccount.observeAsState()
                val userBalance = sourceAccountState?.accountBalance ?: 0.0
                val userName = sourceAccountState?.name ?: ""

                HomeScreen(
                    userName = userName,
                    balance = userBalance,
                    transactions = transactions,
                    selectedItem = selectedItem,
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { query -> searchQuery = query },
                    onItemSelected = { index -> selectedItem = index },
                    onTransferClick = { navController.navigate(R.id.action_homeFragment_to_transferFragment) },
                    onTransactionClick = { navController.navigate(R.id.action_homeFragment_to_transactionHistoryFragment) },
                    sourceAccount = sourceAccount,
                    destinationAccounts = destinationAccounts
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        observeTransactions()
        observeAccounts()
    }

    private fun observeTransactions() {
        database.transactionDao().getAllTransactions()
            .observe(viewLifecycleOwner) { transactionList ->
                transactions = transactionList
            }
    }

    private fun observeAccounts() {
        accountViewModel.accounts.observe(viewLifecycleOwner) { accounts ->
            sourceAccount = accounts.firstOrNull()
            destinationAccounts = accounts.filter { it != sourceAccount }
        }
    }

    fun transfer(amount: Double) {
        val currentAccounts = accountViewModel.accounts.value ?: return
        val userAccount = currentAccounts.firstOrNull() ?: return

        if (userAccount.accountBalance >= amount) {
            lifecycleScope.launch {
                val newTransaction = Transaction(
                    id = 0,
                    sourceAccount = "source_account",
                    destinationAccount = "destination_account",
                    amount = amount,
                    timestamp = System.currentTimeMillis()
                )
                database.transactionDao().insert(newTransaction)

                val newBalance = userAccount.accountBalance - amount
                database.userDao().updateUserBalance(userAccount.id, newBalance)
                accountViewModel.updateBalance(userAccount.id, newBalance)

                Toast.makeText(requireContext(), "Transfer successful", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(requireContext(), "Insufficient balance", Toast.LENGTH_LONG).show()
        }
    }
}
