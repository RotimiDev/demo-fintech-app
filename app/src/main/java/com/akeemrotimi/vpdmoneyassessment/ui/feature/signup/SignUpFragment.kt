package com.akeemrotimi.vpdmoneyassessment.ui.feature.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.akeemrotimi.vpdmoneyassessment.R

class SignUpFragment : Fragment() {
    private val signUpViewModel by viewModels<SignUpViewModel>()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        return ComposeView(requireContext()).apply {
            setContent {
                SignUpScreen { firstName, middleName, lastName, phoneNumber, email, password, confirmPassword ->
                    if (password == confirmPassword) {
                        if (middleName != null) {
                            if (firstName == lastName && middleName.isEmpty()) {
                                Toast.makeText(requireContext(), "Middle name is required when first and last name are the same.", Toast.LENGTH_LONG).show()
                            } else {
                                signUpViewModel.signUp(email, password, firstName, middleName, lastName, phoneNumber, requireContext()) { success ->
                                    if (success) {
                                        navController.navigate(R.id.action_signUpFragment_to_homeFragment)
                                    } else {
                                        Toast.makeText(requireContext(), "Sign up failed", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
