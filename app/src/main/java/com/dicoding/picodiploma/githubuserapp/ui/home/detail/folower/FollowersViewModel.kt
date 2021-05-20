package com.dicoding.picodiploma.githubuserapp.ui.home.detail.folower

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.picodiploma.githubuserapp.data.GithubRepository
import com.dicoding.picodiploma.githubuserapp.data.remote.response.User

class FollowersViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = GithubRepository.getInstance()

    private val mediator = MediatorLiveData<ArrayList<User>>()

    val listFollowers: LiveData<ArrayList<User>> = mediator

    fun setListFollowers(username: String) = mediator
        .addSource(repo.setListFollowers(username, getApplication())) {
            mediator.postValue(it)
        }
}