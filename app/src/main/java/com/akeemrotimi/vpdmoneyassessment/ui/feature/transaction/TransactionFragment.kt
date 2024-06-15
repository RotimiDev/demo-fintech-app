package com.akeemrotimi.vpdmoneyassessment.ui.feature.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

class TransactionFragment : Fragment() {
    private val transactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory(requireActivity().application)
    }

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            navController = findNavController()
            setContent {
                val transactionsState by transactionViewModel.transactions.observeAsState()
                val transactions = transactionsState ?: emptyList()
                var searchQuery by remember { mutableStateOf("") }

                val filteredTransactionsState by transactionViewModel.filteredTransactions.observeAsState()
                val filteredTransactions = filteredTransactionsState ?: transactions

                LaunchedEffect(searchQuery) {
                    transactionViewModel.filterTransactions(searchQuery)
                }

                TransactionScreen(
                    transactions = filteredTransactions,
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { newQuery ->
                        searchQuery = newQuery
                        transactionViewModel.filterTransactions(newQuery)
                    },
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}
