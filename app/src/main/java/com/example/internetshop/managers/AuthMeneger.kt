package com.example.internetshop.managers

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthManager(context: Context) {

    private val auth = FirebaseAuth.getInstance()
    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    init {
        // Проверяем аутентификацию при первом открытии приложения
        _currentUser.value = auth.currentUser
    }

    fun registerWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _currentUser.value = auth.currentUser
                } else {
                    // Обработка ошибок регистрации
                }
            }
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _currentUser.value = auth.currentUser
                } else {
                    // Обработка ошибок входа
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _currentUser.value = null
    }
}
