package com.dicoding.picodiploma.githubuserapp.ui.home.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.picodiploma.githubuserapp.data.GithubRepository
import com.dicoding.picodiploma.githubuserapp.data.remote.response.User

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = GithubRepository.getInstance()

    private val mediator = MediatorLiveData<ArrayList<User>>()

    val user: LiveData<ArrayList<User>> = mediator

    fun setSearchUsers(query: String) = mediator
        .addSource(repo.setSearchUsers(query, getApplication())) {
            mediator.postValue(it)
        }

    val stat = mutableMapOf<String, String>()
}