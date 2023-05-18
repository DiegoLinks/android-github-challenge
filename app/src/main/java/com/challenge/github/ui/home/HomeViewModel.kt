package com.challenge.github.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.challenge.github.core.BaseViewModel
import com.challenge.github.core.NetworkResult
import com.challenge.github.core.repository.GitHubRepository
import com.challenge.github.core.util.Message.Companion.GENERIC_ERROR_MESSAGE
import com.challenge.github.model.User
import com.challenge.github.model.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: GitHubRepository
) : BaseViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getUserList() {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.getUserList()) {
                is NetworkResult.Success -> {
                    _users.postValue(response.data?.map { userResponse -> userResponse.toUser() })
                    _isLoading.postValue(false)
                }
                is NetworkResult.Error -> {
                    _error.postValue(response.message ?: GENERIC_ERROR_MESSAGE)
                    _isLoading.postValue(false)
                }
                is NetworkResult.Loading -> {
                    _isLoading.value = true
                }
            }
        }
    }
}