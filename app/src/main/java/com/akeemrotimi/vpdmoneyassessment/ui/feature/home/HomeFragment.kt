package com.akeemrotimi.vpdmoneyassessment.ui.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.akeemrotimi.vpdmoneyassessment.R
import com.akeemrotimi.vpdmoneyassessment.data.local.AppDatabase
import com.akeemrotimi.vpdmoneyassessment.data.model.Account
import com.akeemrotimi.vpdmoneyassessment.data.model.Transaction
import com.akeemrotimi.vpdmoneyassessment.ui.feature.account.AccountViewModel
import com.akeemrotimi.vpdmoneyassessment.ui.feature.account.AccountViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel
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
        // Initialize the Room database
        database = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "app_db"
        ).build()

        // Initialize the ViewModel with a factory
        val firebaseAuth = FirebaseAuth.getInstance()
        val factory = AccountViewModelFactory(firebaseAuth)
        accountViewModel =
            ViewModelProvider(requireActivity(), factory)[AccountViewModel::class.java]

        // Observe LiveData and update the Composable UI accordingly
        return ComposeView(requireContext()).apply {
            setContent {
                val sourceAccountState by accountViewModel.sourceAccount.observeAsState()
                val accountsState by accountViewModel.accounts.observeAsState(emptyList())

                // Update mutable states with LiveData values
                sourceAccount = sourceAccountState
                destinationAccounts = accountsState.filter { it.id != sourceAccountState?.id }

                // Extract values for display
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
        accountViewModel.sourceAccount.observe(viewLifecycleOwner) { account ->
            sourceAccount = account
        }
        accountViewModel.accounts.observe(viewLifecycleOwner) { accounts ->
            destinationAccounts = accounts.filter { it.id != sourceAccount?.id }
        }
    }
}
