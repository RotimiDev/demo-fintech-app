package com.akeemrotimi.vpdmoneyassessment.ui.feature.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.akeemrotimi.vpdmoneyassessment.R
import com.akeemrotimi.vpdmoneyassessment.data.model.Account
import com.akeemrotimi.vpdmoneyassessment.data.model.Transaction
import com.akeemrotimi.vpdmoneyassessment.ui.feature.account.AccountViewModel
import com.akeemrotimi.vpdmoneyassessment.ui.feature.account.AccountViewModelFactory
import com.akeemrotimi.vpdmoneyassessment.ui.feature.transaction.TransactionViewModel
import com.akeemrotimi.vpdmoneyassessment.ui.feature.transaction.TransactionViewModelFactory
import com.akeemrotimi.vpdmoneyassessment.ui.feature.transfer.summary.SummaryBottomSheetView
import com.google.firebase.auth.FirebaseAuth

class TransferFragment : Fragment() {
    private lateinit var accountViewModel: AccountViewModel
    private val transactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory(requireActivity().application)
    }
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val firebaseAuth = FirebaseAuth.getInstance()
        val factory = AccountViewModelFactory(firebaseAuth)
        accountViewModel = ViewModelProvider(this, factory)[AccountViewModel::class.java]

        navController = findNavController()
        return ComposeView(requireContext()).apply {
            setContent {
                val sourceAccount by accountViewModel.sourceAccount.observeAsState(null)
                val destinationAccounts by accountViewModel.accounts.observeAsState(emptyList())

                var selectedDestinationAccount by remember { mutableStateOf<Account?>(null) }
                var amount by remember { mutableStateOf("") }
                var destinationAccountError by remember { mutableStateOf<String?>(null) }
                var amountError by remember { mutableStateOf<String?>(null) }
                var showModal by remember { mutableStateOf(false) }

                if (showModal) {
                    SummaryBottomSheetView(
                        sourceAccount = sourceAccount,
                        destinationAccount = selectedDestinationAccount?.name ?: "",
                        transferAmount = amount,
                        onDismiss = { showModal = false },
                        onConfirmTransfer = {
                            val transferAmount = amount.toDoubleOrNull() ?: 0.0
                            val newTransaction = Transaction(
                                id = 0,
                                sourceAccount = sourceAccount?.name ?: "",
                                destinationAccount = selectedDestinationAccount?.name ?: "",
                                amount = transferAmount,
                                timestamp = System.currentTimeMillis()
                            )
                            transactionViewModel.addTransaction(newTransaction)
                            accountViewModel.transfer(selectedDestinationAccount?.id ?: -1, transferAmount)
                            showModal = false
                            navController.navigate(R.id.action_transferFragment_to_transferSuccessfulFragment)
                        }
                    )
                }

                val incorrectAccountNumberError = getString(R.string.msg_incorrect_account_number)
                val insufficientFundsError = getString(R.string.msg_insufficient_fund)
                TransferScreen(
                    sourceAccount = sourceAccount,
                    destinationAccounts = destinationAccounts,
                    amount = amount,
                    destinationAccountError = destinationAccountError,
                    amountError = amountError,
                    onSourceAccountChange = { /* no op */ },
                    onDestinationAccountChange = { selectedAccount ->
                        selectedDestinationAccount = selectedAccount
                        destinationAccountError = null
                    },
                    onAmountChange = { newAmount ->
                        amount = newAmount
                        amountError = null
                    }
                ) {
                    var valid = true

                    if (selectedDestinationAccount == null) {
                        destinationAccountError = incorrectAccountNumberError
                        valid = false
                    }

                    val transferAmount = amount.toDoubleOrNull() ?: 0.0
                    val sourceAccountBalance = sourceAccount?.accountBalance ?: 0.0

                    if (transferAmount <= 0 || transferAmount > sourceAccountBalance) {
                        amountError = insufficientFundsError
                        valid = false
                    }

                    if (valid) {
                        showModal = true
                    }
                }
            }
        }
    }
}
