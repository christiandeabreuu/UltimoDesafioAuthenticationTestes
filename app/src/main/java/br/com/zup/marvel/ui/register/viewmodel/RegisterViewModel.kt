package br.com.zup.marvel.ui.register.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.marvel.CREATE_USER_ERROR_MESSAGE
import br.com.zup.marvel.EMAIL_ERROR_MESSAGE
import br.com.zup.marvel.NAME_ERROR_MESSAGE
import br.com.zup.marvel.PASSWORD_ERROR_MESSAGE
import br.com.zup.marvel.domain.model.User
import br.com.zup.marvel.domain.repository.AuthenticationRepositoryFactory

class RegisterViewModel : ViewModel() {
    private val authenticationRepository = AuthenticationRepositoryFactory.create()

    private var _registerState = MutableLiveData<User>()
    val registerState: LiveData<User> = _registerState

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun validateDataUser(user: User) {
        when {
            user.name.length < 3 -> {
                _errorState.value = NAME_ERROR_MESSAGE
            }
            Patterns.EMAIL_ADDRESS.matcher(user.email).matches().not() -> {
                _errorState.value = EMAIL_ERROR_MESSAGE
            }
            user.password.length < 8 -> {
                _errorState.value = PASSWORD_ERROR_MESSAGE
            }
            else -> registerUser(user)
        }
    }

    private fun registerUser(user: User) {
        try {
            authenticationRepository.registerUser(
                user.email,
                user.password
            ).addOnSuccessListener {
                authenticationRepository.updateUserProfile(user.name)?.addOnSuccessListener {
                    _registerState.value = user
                }
            }.addOnFailureListener {
                _errorState.value = CREATE_USER_ERROR_MESSAGE +
                        it.message
            }
        } catch (ex: Exception) {
            _errorState.value = ex.message
        }
    }
}
