package com.noranekoit.bfaausergithub3.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noranekoit.bfaausergithub3.data.api.RetrofitClient
import com.noranekoit.bfaausergithub3.data.model.UserGithub
import com.noranekoit.bfaausergithub3.data.model.UserGithubResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserGithubViewModel : ViewModel(){
    val listUsers = MutableLiveData<ArrayList<UserGithub>>()

    val checkSucces = MutableLiveData<Boolean>()

    fun setSearchUsers(query: String){
        RetrofitClient.apiInstances
            .getSearchUsers(query)
            .enqueue(object  : Callback<UserGithubResponse>{
                override fun onResponse(
                    call: Call<UserGithubResponse>,
                    githubResponse: Response<UserGithubResponse>
                ) {
                    if (githubResponse.isSuccessful){
                        checkSucces.postValue(true)
                        listUsers.postValue(githubResponse.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserGithubResponse>, t: Throwable) {
                    Log.d("Failure", "${t.message}")
                    checkSucces.postValue(false)
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<UserGithub>>{
        return listUsers
    }

    fun checkServer():MutableLiveData<Boolean>{
        return checkSucces
    }
}