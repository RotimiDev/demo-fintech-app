package com.akeemrotimi.vpdmoneyassessment.ui.feature.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    fun login(email: String, password: String, context: Context, callback: (Boolean) -> Unit) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                    Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show()
                } else {
                    callback(false)
                    Toast.makeText(context, "Login authentication failed", Toast.LENGTH_LONG).show()
                }
            }
    }
}
