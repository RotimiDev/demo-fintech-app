package com.akeemrotimi.vpdmoneyassessment.ui.feature.signup

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.akeemrotimi.vpdmoneyassessment.data.local.AppDatabase
import com.akeemrotimi.vpdmoneyassessment.data.local.UserDao
import com.akeemrotimi.vpdmoneyassessment.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.launch

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao: UserDao = AppDatabase.getDatabase(application).userDao()

    fun signUp(
        email: String,
        password: String,
        firstName: String,
        middleName: String?,
        lastName: String,
        phoneNumber: String,
        context: Context,
        callback: (Boolean) -> Unit
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    val fullName = if (middleName.isNullOrEmpty()) {
                        "$firstName $lastName"
                    } else {
                        "$firstName $middleName $lastName"
                    }
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(fullName)
                        .build()
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener { profileTask ->
                        if (profileTask.isSuccessful) {
                            viewModelScope.launch {
                                saveUserDetailsToDatabase(user.uid, firstName, middleName, lastName, phoneNumber)
                                callback(true)
                            }
                        } else {
                            callback(false)
                            Toast.makeText(context, "Failed to set user name: ${profileTask.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    callback(false)
                    Toast.makeText(context, "Sign up failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private suspend fun saveUserDetailsToDatabase(
        userId: String,
        firstName: String,
        middleName: String?,
        lastName: String,
        phoneNumber: String,
    ) {
        val user = User(userId, firstName, middleName, lastName, phoneNumber)
        userDao.insertUser(user)
    }
}
