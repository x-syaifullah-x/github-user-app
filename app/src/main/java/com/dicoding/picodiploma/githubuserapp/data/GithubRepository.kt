package com.dicoding.picodiploma.githubuserapp.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.dicoding.picodiploma.githubuserapp.data.remote.Retrofit
import com.dicoding.picodiploma.githubuserapp.data.remote.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepository private constructor() {

    companion object {

        @Volatile
        private var instance: GithubRepository? = null

        fun getInstance(): GithubRepository {
            synchronized(this) {
                if (instance == null) instance = GithubRepository()
            }
            return (instance as GithubRepository)
        }
    }

    private fun <T> set(context: Context, block: () -> Call<T>) =
        MutableLiveData<T>().apply {
            block().enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) postValue(response.body())
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    postValue(null)
                }
            })
    }

    fun setUserDetail(username: String, context: Context) = set(context) {
        Retrofit.service.getUserDetail(username)
    }

    fun setSearchUsers(query: String, context: Context): LiveData<ArrayList<User>> = set(context) {
        Retrofit.service.getSearchUsers(query)
    }.map { it.items }

    fun setListFollowers(username: String, context: Context) = set(context) {
        Retrofit.service.getFollowers(username)
    }

    fun setListFollowing(username: String, context: Context) = set(context) {
        Retrofit.service.getFollowing(username)
    }
}