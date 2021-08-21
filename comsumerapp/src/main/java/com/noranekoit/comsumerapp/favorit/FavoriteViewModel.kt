package com.noranekoit.comsumerapp.favorit

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.noranekoit.comsumerapp.DatabaseContract
import com.noranekoit.comsumerapp.MappingHelper
import com.noranekoit.comsumerapp.model.UserGithub

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var list = MutableLiveData<ArrayList<UserGithub>>()

    fun setFavoriteUser(context: Context){
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoriteUserColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val listConverted = MappingHelper.mapCursorToArrayList(cursor)
        list.postValue(listConverted)
    }

    fun getFavoriteUser(): MutableLiveData<ArrayList<UserGithub>>{
        return list
    }

}