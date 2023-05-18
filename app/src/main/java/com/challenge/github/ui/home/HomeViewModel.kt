package com.challenge.github.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.challenge.github.BaseViewModel
import com.challenge.github.core.repository.GitHubRepository
import com.challenge.github.model.User
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

    fun getUserList() {
        viewModelScope.launch(Dispatchers.IO) {
            _users.postValue(repository.getUserList())
        }
    }
}