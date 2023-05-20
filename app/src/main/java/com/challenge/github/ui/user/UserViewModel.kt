package com.challenge.github.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.challenge.github.core.BaseViewModel
import com.challenge.github.core.NetworkResult
import com.challenge.github.core.repository.GitHubRepository
import com.challenge.github.core.util.Message.Companion.GENERIC_ERROR_MESSAGE
import com.challenge.github.model.UserDetail
import com.challenge.github.model.UserRepository
import com.challenge.github.model.response.toUserDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: GitHubRepository
) : BaseViewModel() {
    private val _user = MutableLiveData<UserDetail>()
    val user: LiveData<UserDetail> = _user

    private val _repositories = MutableLiveData<List<UserRepository>>()
    val repositories: LiveData<List<UserRepository>> = _repositories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getUserDetail(userLogin: String) {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.getUser(userLogin)) {
                is NetworkResult.Success -> {
                    _user.postValue(response.data?.toUserDetail())
                    _isLoading.postValue(false)
                    getUserRepositories(userLogin)
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

    fun getUserRepositories(userLogin: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.getUserRepositories(userLogin)) {
                is NetworkResult.Success -> {
                    _repositories.postValue(response.data?.map { it -> it.toUserRepository() })
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