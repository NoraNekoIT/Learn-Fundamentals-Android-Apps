package com.noranekoit.bfaausergithub3.ui.detail.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noranekoit.bfaausergithub3.data.api.RetrofitClient
import com.noranekoit.bfaausergithub3.data.model.RepositoryGithubResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel :ViewModel(){

    val listRepository = MutableLiveData<ArrayList<RepositoryGithubResponse>>()

    fun setListRepository(username: String){
        RetrofitClient.apiInstances
            .getRepositories(username)
            .enqueue(object :Callback<ArrayList<RepositoryGithubResponse>>{
                override fun onResponse(
                    call: Call<ArrayList<RepositoryGithubResponse>>,
                    response: Response<ArrayList<RepositoryGithubResponse>>
                ) {
                    if (response.isSuccessful){

                        listRepository.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<RepositoryGithubResponse>>, t: Throwable) {
                    Log.d("Failure","${t.message}")
                }

            })
    }

    fun getListRepository(): LiveData<ArrayList<RepositoryGithubResponse>> {
        return listRepository
    }
}