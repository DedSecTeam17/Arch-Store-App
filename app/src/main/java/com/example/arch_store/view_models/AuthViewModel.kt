package com.example.arch_store.view_models

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.arch_store.models.User
import com.example.arch_store.repository.AuthRepository
import com.example.arch_store.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class AuthViewModel
@ViewModelInject
constructor(
    private val authRepository: AuthRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _user: MutableLiveData<DataState<User>> = MutableLiveData()

    val user: LiveData<DataState<User>>
        get() = _user

    fun setStateEvent(
        authStateEvent: AuthStateEvent,
        identifier: String,
        password: String,
        email: String,
        userName: String
    ) {
        viewModelScope.launch {
            when (authStateEvent) {
                is AuthStateEvent.SignIning -> {
                    authRepository.signIn(
                        id = identifier,
                        pwd = password
                    )
                        .onEach { dataState ->
                            _user.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                AuthStateEvent.SignUp -> {
                    // who cares
                    authRepository.signUp(
                        email = email,
                        userName = userName,
                        password = password
                    )
                        .onEach { dataState ->
                            _user.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                AuthStateEvent.None -> {
                    // who cares
                }
            }
        }
    }

}


sealed class AuthStateEvent() {

    object SignIning : AuthStateEvent()
    object SignUp : AuthStateEvent()


    object None : AuthStateEvent()
}
