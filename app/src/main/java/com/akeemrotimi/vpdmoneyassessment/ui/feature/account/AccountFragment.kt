package com.akeemrotimi.vpdmoneyassessment.ui.feature.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class AccountFragment : Fragment() {
    private val accountViewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val sourceAccount by accountViewModel.sourceAccount.observeAsState(null)
                val destinationAccounts by accountViewModel.accounts.observeAsState(emptyList())
                AccountScreen(sourceAccount, destinationAccounts)
            }
        }
    }
}
