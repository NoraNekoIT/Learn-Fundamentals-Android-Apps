package com.noranekoit.comsumerapp

import android.database.Cursor
import com.noranekoit.comsumerapp.model.UserGithub

object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<UserGithub>{
        val list = ArrayList<UserGithub>()
        if (cursor != null){
            while (cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.ID))
                val username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.USERNAME))
                val avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.AVATAR_URL))
                val type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.TYPE))
                list.add(
                    UserGithub(
                        username,id,avatarUrl,type
                    )
                )
            }
        }
        return list
    }
}