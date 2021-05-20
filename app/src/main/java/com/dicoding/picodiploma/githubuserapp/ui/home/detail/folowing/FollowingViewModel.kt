package com.dicoding.picodiploma.githubuserapp.ui.home.detail.folowing

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.picodiploma.githubuserapp.data.GithubRepository
import com.dicoding.picodiploma.githubuserapp.data.remote.response.User

class FollowingViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = GithubRepository.getInstance()

    private val mediator = MediatorLiveData<ArrayList<User>>()

    val listFollowing: LiveData<ArrayList<User>> = mediator

    fun setListFollowing(username: String) = mediator
        .addSource(repo.setListFollowing(username, getApplication())) {
            mediator.postValue(it)
        }
}