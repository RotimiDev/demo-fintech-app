package com.akeemrotimi.vpdmoneyassessment.ui.feature.transfer.success

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.akeemrotimi.vpdmoneyassessment.R

class TransferSuccessfulFragment : Fragment() {
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        return ComposeView(requireContext()).apply {
            setContent {
                TransactionSuccessScreen(
                    onGoHomeClick = {
                        navController.navigate(R.id.action_transferSuccessfulFragment_to_homeFragment)
                    }
                )
            }
        }
    }
}
