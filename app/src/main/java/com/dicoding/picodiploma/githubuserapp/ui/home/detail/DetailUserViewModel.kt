package com.dicoding.picodiploma.githubuserapp.ui.home.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.picodiploma.githubuserapp.data.GithubRepository
import com.dicoding.picodiploma.githubuserapp.data.remote.response.DetailUserResponse

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = GithubRepository.getInstance()

    private val mediator = MediatorLiveData<DetailUserResponse>()

    val userDetail: LiveData<DetailUserResponse> = mediator

    fun setUserDetail(username: String) = mediator
        .addSource(repo.setUserDetail(username, getApplication())) {
            mediator.postValue(it)
        }
}