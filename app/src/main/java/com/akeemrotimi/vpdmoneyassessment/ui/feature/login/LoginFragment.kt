package com.akeemrotimi.vpdmoneyassessment.ui.feature.login

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

class LoginFragment : Fragment() {
    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        return ComposeView(requireContext()).apply {
            setContent {
                LoginScreen(
                    onSignInClick = { email, password ->
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            loginViewModel.login(email, password, requireContext()) { success ->
                                if (success) {
                                    navController.navigate(R.id.action_loginFragment_to_homeFragment)
                                }
                            }
                        } else {
                            Toast.makeText(requireContext(), R.string.msg_email_password_required, Toast.LENGTH_SHORT).show()
                        }
                    },
                    onCreateAccountClick = {
                        navController.navigate(R.id.action_loginFragment_to_signUpFragment)
                    }
                )
            }
        }
    }
}
