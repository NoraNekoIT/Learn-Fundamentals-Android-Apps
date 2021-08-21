package com.noranekoit.bfaausergithub3.ui.detail.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noranekoit.bfaausergithub3.data.api.RetrofitClient
import com.noranekoit.bfaausergithub3.data.model.UserGithub
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<UserGithub>>()

    fun setListFollowing(username: String){
        RetrofitClient.apiInstances
            .getFollowing(username)
            .enqueue(object :Callback<ArrayList<UserGithub>>{
                override fun onResponse(
                    call: Call<ArrayList<UserGithub>>,
                    response: Response<ArrayList<UserGithub>>
                ) {
                    if (response.isSuccessful){

                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserGithub>>, t: Throwable) {
                    Log.d("Failure","${t.message}")
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<UserGithub>> {
        return listFollowing
    }
}