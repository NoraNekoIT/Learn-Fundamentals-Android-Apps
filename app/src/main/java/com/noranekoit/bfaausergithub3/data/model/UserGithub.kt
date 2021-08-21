package com.noranekoit.bfaausergithub3.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserGithub(
    val login : String,
    val id: Int,
    val avatar_url : String,
    val type: String
) : Parcelable
