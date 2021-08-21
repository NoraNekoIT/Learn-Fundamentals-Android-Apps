package com.noranekoit.bfaausergithub3.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noranekoit.bfaausergithub3.data.api.RetrofitClient
import com.noranekoit.bfaausergithub3.data.lokal.FavoriteUser
import com.noranekoit.bfaausergithub3.data.lokal.FavoriteUserDao
import com.noranekoit.bfaausergithub3.data.lokal.UserDatabase
import com.noranekoit.bfaausergithub3.data.model.DetailUserGithubResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUserGithubViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserGithubResponse>()

    private var userDao :FavoriteUserDao?
    private var userDb : UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstances
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserGithubResponse> {
                override fun onResponse(
                    call: Call<DetailUserGithubResponse>,
                    githubResponse: Response<DetailUserGithubResponse>
                ) {
                    if (githubResponse.isSuccessful) {
                        user.postValue(githubResponse.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserGithubResponse>, t: Throwable) {
                    Log.d("Failure", "${t.message}")
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserGithubResponse> {
        return user
    }

    fun addToFavorite(username: String, id : Int, avatar_url: String, type:String ){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username,id,avatar_url ,type
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}