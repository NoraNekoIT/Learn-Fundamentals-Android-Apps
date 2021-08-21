package com.noranekoit.bfaausergithub3.data.lokal

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite_user")
data class FavoriteUser(
    val login : String,
    @PrimaryKey
    val id: Int,
    val avatar_url : String,
    val type : String
): Parcelable
