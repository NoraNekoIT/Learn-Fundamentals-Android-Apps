package com.noranekoit.bfaausergithub3.ui.favorit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.noranekoit.bfaausergithub3.data.lokal.FavoriteUser
import com.noranekoit.bfaausergithub3.data.lokal.FavoriteUserDao
import com.noranekoit.bfaausergithub3.data.lokal.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUser()
    }
}